package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.*;
import ServiCasa.enums.StatutPaiement;
import ServiCasa.enums.StatutReservation;
import ServiCasa.mapper.ReservationMapper;
import ServiCasa.repository.*;
import ServiCasa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import ServiCasa.notification.dto.NotificationRequestDTO;
import ServiCasa.notification.enums.NotificationType;
import ServiCasa.notification.service.NotificationService;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {

    private final ReservationMapper mapper;
    private final ReservationRepository repository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;
    private final DemandeServiceRepository demandeServiceRepository;
    private final PaiementRepository paiementRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;
    private final DisponibiliteRepository disponibiliteRepository;

    @Override
    public ReservationResponseDTO addReservation(ReservationRequestDTO dto) {
        return addReservation(dto, null);
    }

    @Override
    public ReservationResponseDTO addReservation(ReservationRequestDTO dto, String clientEmail) {
        Artisan artisan = artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Artisan !"));

        Client client = null;

        if (clientEmail != null) {
            User user = userRepository.findByEmail(clientEmail)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

            client = clientRepository.findById(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable"));

        } else if (dto.getClientId() != null) {

            client = clientRepository.findById(dto.getClientId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Client !"));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Client non identifié");
        }

        DemandeService demandeService = null;

        if (dto.getDemandeServiceId() != null) {
            demandeService = demandeServiceRepository
                    .findById(dto.getDemandeServiceId())
                    .orElse(null);
        }

        Reservation reservation = mapper.toEntity(dto);

        reservation.setArtisan(artisan);
        reservation.setClient(client);
        reservation.setDemandeService(demandeService);

        if (reservation.getPrixTotal() == null) {
            if (dto.getPrixTotal() != null) {
                reservation.setPrixTotal(dto.getPrixTotal());
            } else if (artisan.getTarifHoraire() != null) {
                reservation.setPrixTotal(artisan.getTarifHoraire());
            } else {
                reservation.setPrixTotal(java.math.BigDecimal.valueOf(250));
            }
        }

        if (reservation.getStatutReservation() == null) {
            reservation.setStatutReservation(StatutReservation.EN_ATTENTE);
        }

        if (reservation.getDateReservation() == null) {
            reservation.setDateReservation(LocalDateTime.now());
        }

        Reservation savedReservation = repository.save(reservation);

        if (artisan != null) {
            userRepository.findById(artisan.getId()).ifPresent(u -> {

                String clientName = reservation.getClient() != null
                        ? reservation.getClient().getPrenom() + " " + reservation.getClient().getNom()
                        : "Un client";

                NotificationRequestDTO notification = new NotificationRequestDTO();
                notification.setType(NotificationType.NOUVELLE_DEMANDE);
                notification.setMessage("Nouvelle demande de réservation de " + clientName + ".");
                notification.setDate(LocalDateTime.now());
                notification.setReservationId(savedReservation.getId());

                notificationService.createAndSend(notification, u);
            });
        }

        return mapper.toDto(savedReservation);
    }

    @Override
    public ReservationResponseDTO updateReservationStatus(Long id, StatutReservation statut, String artisanEmail) {

        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (artisanEmail != null) {

            User user = userRepository.findByEmail(artisanEmail)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

            Artisan artisan = artisanRepository.findById(user.getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

            if (!reservation.getArtisan().getId().equals(artisan.getId())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette réservation n'appartient pas à cet artisan");
            }
        }

        reservation.setStatutReservation(statut);

        Reservation savedReservation = repository.save(reservation);

        if (reservation.getClient() != null) {
            userRepository.findById(reservation.getClient().getId()).ifPresent(u -> {

                if (statut == StatutReservation.ACCEPTEE) {

                    NotificationRequestDTO notification = new NotificationRequestDTO();
                    notification.setType(NotificationType.DEMANDE_ACCEPTEE);
                    notification.setMessage(
                            "Votre demande de réservation a été acceptée par l'artisan."
                    );
                    notification.setDate(LocalDateTime.now());
                    notification.setReservationId(savedReservation.getId());

                    notificationService.createAndSend(notification, u);

                } else if (statut == StatutReservation.REFUSEE) {

                    NotificationRequestDTO notification = new NotificationRequestDTO();
                    notification.setType(NotificationType.DEMANDE_REFUSEE);
                    notification.setMessage(
                            "Votre demande de réservation a été refusée par l'artisan."
                    );
                    notification.setDate(LocalDateTime.now());
                    notification.setReservationId(savedReservation.getId());

                    notificationService.createAndSend(notification, u);

                } else if (statut == StatutReservation.TERMINEE) {

                    NotificationRequestDTO notification = new NotificationRequestDTO();
                    notification.setType(NotificationType.INTERVENTION_TERMINEE);
                    notification.setMessage(
                            "L'intervention a été terminée par l'artisan. Vous pouvez maintenant donner un avis."
                    );
                    notification.setDate(LocalDateTime.now());
                    notification.setReservationId(savedReservation.getId());

                    notificationService.createAndSend(notification, u);
                }
            });
        }

        return mapper.toDto(savedReservation);
    }

    @Override
    @Transactional
    public ReservationResponseDTO accepterReservation(Long reservationId, String artisanEmail) {

        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (reservation.getStatutReservation() != StatutReservation.EN_ATTENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seules les réservations EN_ATTENTE peuvent être acceptées");
        }

        if (reservation.getDateIntervention() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La date d'intervention est obligatoire");
        }

        if (artisanEmail == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Artisan non authentifié");
        }

        User user = userRepository.findByEmail(artisanEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        if (!reservation.getArtisan().getId().equals(artisan.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette réservation n'appartient pas à cet artisan");
        }

        LocalDateTime newStart = reservation.getDateIntervention();
        LocalDateTime newEnd = newStart.plusHours(1);

        List<Reservation> existantes = repository.findByArtisanIdAndStatutReservationIn(
                artisan.getId(), List.of(StatutReservation.ACCEPTEE, StatutReservation.EN_COURS));

        boolean chevauchement = existantes.stream()
                .filter(r -> !r.getId().equals(reservationId))
                .filter(r -> r.getDateIntervention() != null)
                .anyMatch(r -> {
                    LocalDateTime existStart = r.getDateIntervention();
                    LocalDateTime existEnd = existStart.plusHours(1);
                    return newStart.isBefore(existEnd) && newEnd.isAfter(existStart);
                });

        if (chevauchement) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "L'artisan est déjà indisponible pour cette heure.");
        }

        reservation.setStatutReservation(StatutReservation.ACCEPTEE);
        Reservation savedReservation = repository.save(reservation);

        LocalDate reservationDate = newStart.toLocalDate();
        LocalTime reservationStart = newStart.toLocalTime();
        LocalTime reservationEnd = newEnd.toLocalTime();

        List<Disponibilite> existingSlots = disponibiliteRepository.findByArtisanIdAndDateAndDisponibleIsTrue(artisan.getId(), reservationDate);

        if (!existingSlots.isEmpty()) {
            for (Disponibilite slot : existingSlots) {
                LocalTime slotStart = slot.getHeureDebut();
                LocalTime slotEnd = slot.getHeureFin();
                if (!reservationStart.isBefore(slotStart) && !reservationEnd.isAfter(slotEnd)) {
                    if (reservationStart.isAfter(slotStart)) {
                        slot.setHeureFin(reservationStart);
                        disponibiliteRepository.save(slot);
                    }
                    Disponibilite blocked = new Disponibilite();
                    blocked.setArtisan(artisan);
                    blocked.setDate(reservationDate);
                    blocked.setHeureDebut(reservationStart);
                    blocked.setHeureFin(reservationEnd);
                    blocked.setDisponible(false);
                    disponibiliteRepository.save(blocked);
                    if (reservationEnd.isBefore(slotEnd)) {
                        Disponibilite remaining = new Disponibilite();
                        remaining.setArtisan(artisan);
                        remaining.setDate(reservationDate);
                        remaining.setHeureDebut(reservationEnd);
                        remaining.setHeureFin(slotEnd);
                        remaining.setDisponible(true);
                        disponibiliteRepository.save(remaining);
                    }
                } else if (!reservationStart.isBefore(slotStart) && reservationEnd.isAfter(slotEnd)) {
                    slot.setHeureFin(reservationStart);
                    disponibiliteRepository.save(slot);
                    Disponibilite blocked = new Disponibilite();
                    blocked.setArtisan(artisan);
                    blocked.setDate(reservationDate);
                    blocked.setHeureDebut(reservationStart);
                    blocked.setHeureFin(reservationEnd);
                    blocked.setDisponible(false);
                    disponibiliteRepository.save(blocked);
                }
            }
        } else {
            try {
                Disponibilite bloc = new Disponibilite();
                bloc.setArtisan(artisan);
                bloc.setDate(reservationDate);
                bloc.setHeureDebut(reservationStart);
                bloc.setHeureFin(reservationEnd);
                bloc.setDisponible(false);
                disponibiliteRepository.save(bloc);
            } catch (Exception ignored) {
            }
        }

        if (savedReservation.getClient() != null) {
            userRepository.findById(savedReservation.getClient().getId()).ifPresent(u -> {
                NotificationRequestDTO notification = new NotificationRequestDTO();
                notification.setType(NotificationType.DEMANDE_ACCEPTEE);
                notification.setMessage("Votre demande de réservation a été acceptée par l'artisan.");
                notification.setDate(LocalDateTime.now());
                notification.setReservationId(savedReservation.getId());
                notificationService.createAndSend(notification, u);
            });
        }

        return mapper.toDto(savedReservation);
    }

    @Override
    public ReservationResponseDTO refuserReservation(Long reservationId, String artisanEmail) {

        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (reservation.getStatutReservation() != StatutReservation.EN_ATTENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seules les réservations EN_ATTENTE peuvent être refusées");
        }

        if (artisanEmail == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Artisan non authentifié");
        }

        User user = userRepository.findByEmail(artisanEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        if (!reservation.getArtisan().getId().equals(artisan.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette réservation n'appartient pas à cet artisan");
        }

        return updateReservationStatus(reservationId, StatutReservation.REFUSEE, artisanEmail);
    }

    @Override
    public ReservationResponseDTO terminerReservation(Long reservationId, String artisanEmail) {

        Reservation reservation = repository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (reservation.getStatutReservation() != StatutReservation.ACCEPTEE && reservation.getStatutReservation() != StatutReservation.EN_COURS) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seules les réservations ACCEPTEE ou EN_COURS peuvent être terminées");
        }

        if (artisanEmail == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Artisan non authentifié");
        }

        User user = userRepository.findByEmail(artisanEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        if (!reservation.getArtisan().getId().equals(artisan.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette réservation n'appartient pas à cet artisan");
        }

        return updateReservationStatus(reservationId, StatutReservation.TERMINEE, artisanEmail);
    }

    @Override
    public ReservationResponseDTO findReservationById(Long id) {

        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune réservation !"));

        return mapper.toDto(reservation);
    }

    @Override
    public Page<ReservationResponseDTO> findAllReservations(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto) {

        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune réservation !"));

        mapper.updateReservationDto(dto, reservation);

        Artisan artisan = artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Artisan !"));

        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Client !"));

        DemandeService demandeService = (dto.getDemandeServiceId() != null)
                        ? demandeServiceRepository
                        .findById(dto.getDemandeServiceId())
                        .orElse(null)
                        : null;

        reservation.setArtisan(artisan);
        reservation.setClient(client);
        reservation.setDemandeService(demandeService);

        return mapper.toDto(repository.save(reservation));
    }

    @Override
    public Page<ReservationResponseDTO> findReservationsByClient(Long clientId, Pageable pageable) {
        Page<Reservation> reservations = repository.findByClientId(clientId, pageable);
        return reservations.map(mapper::toDto);
    }

    @Override
    public Page<ReservationResponseDTO> getPendingReservationsByArtisan(String email,  Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        Page<Reservation> reservations = repository.findByArtisanIdAndStatutReservation(artisan.getId(), StatutReservation.EN_ATTENTE, pageable);

        return reservations.map(mapper::toDto);
    }

    @Override
    public Page<ReservationResponseDTO> getInterventionsByArtisan(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));
        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));
        List<StatutReservation> statuses = List.of(StatutReservation.EN_COURS, StatutReservation.ACCEPTEE, StatutReservation.TERMINEE);
        Page<Reservation> reservations = repository.findByArtisanIdAndStatutReservationIn(artisan.getId(), statuses, pageable);
        return reservations.map(mapper::toDto);
    }

    @Override
    public Page<ReservationResponseDTO> getLatestReservations(Pageable pageable) {
        return repository.findAllByOrderByDateReservationDesc(pageable).map(mapper::toDto);
    }

    @Override
    public Page<ReservationResponseDTO> getMyReservations(String email, Pageable pageable) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable"));

        Page<Reservation> reservations = repository.findByClientIdOrderByDateReservationDesc(client.getId(), pageable);

        return reservations.map(mapper::toDto);
    }


    @Override
    @Transactional
    public void cancelReservation(Long id) {

        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (reservation.getStatutReservation() != StatutReservation.EN_ATTENTE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Seules les réservations EN_ATTENTE peuvent être annulées.");
        }

        Paiement paiement = paiementRepository.findByReservation(reservation)
                .orElse(null);

        if (paiement != null && paiement.getStatutPaiement() == StatutPaiement.PAYE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette réservation a déjà été payée et ne peut pas être annulée.");
        }

        reservation.setStatutReservation(StatutReservation.ANNULEE);

        Reservation saved = repository.save(reservation);

        if (reservation.getArtisan() != null) {

            userRepository.findById(reservation.getArtisan().getId()).ifPresent(user -> {

                String clientName = reservation.getClient() != null
                        ? reservation.getClient().getPrenom() + " " + reservation.getClient().getNom()
                        : "Un client";

                NotificationRequestDTO notification = new NotificationRequestDTO();
                notification.setType(NotificationType.RESERVATION_ANNULEE);
                notification.setMessage(clientName + " a annulé la réservation.");
                notification.setDate(LocalDateTime.now());
                notification.setReservationId(saved.getId());

                notificationService.createAndSend(notification, user);
            });
        }
    }
}
