package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.PaiementRequestDTO;
import ServiCasa.dto.response.PaiementResponseDTO;
import ServiCasa.entity.Paiement;
import ServiCasa.entity.Reservation;
import ServiCasa.enums.StatutPaiement;
import ServiCasa.mapper.PaiementMapper;
import ServiCasa.notification.dto.NotificationRequestDTO;
import ServiCasa.notification.enums.NotificationType;
import ServiCasa.notification.service.NotificationService;
import ServiCasa.repository.PaiementRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {

    private final PaiementRepository paiementRepository;
    private final ReservationRepository reservationRepository;
    private final PaiementMapper paiementMapper;
    private final NotificationService notificationService;
    private final UserRepository userRepository;

    @Override
    public List<PaiementResponseDTO> getAllPaiements() {
        return paiementRepository.findAll().stream()
                .map(paiementMapper::toDto)
                .toList();
    }

    @Override
    public PaiementResponseDTO getPaiementById(Long id) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paiement introuvable"));
        return paiementMapper.toDto(paiement);
    }

    @Override
    public PaiementResponseDTO getPaiementByReservationId(Long reservationId) {
        return paiementRepository.findByReservationId(reservationId)
                .map(paiementMapper::toDto)
                .orElse(null);
    }

    @Override
    public PaiementResponseDTO createPaiement(PaiementRequestDTO dto, String email) {
        if (dto.getReservationId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "reservationId est obligatoire");
        }
        if (dto.getModePaiement() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Mode de paiement invalide");
        }

        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (paiementRepository.findByReservation(reservation).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette réservation est déjà payée");
        }

        if (reservation.getPrixTotal() == null) {
            if (reservation.getArtisan() != null && reservation.getArtisan().getTarifHoraire() != null) {
                reservation.setPrixTotal(reservation.getArtisan().getTarifHoraire());
                reservationRepository.save(reservation);
            } else {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prix total non défini pour cette réservation");
            }
        }

        Paiement paiement = new Paiement();
        paiement.setReservation(reservation);
        paiement.setMontant(reservation.getPrixTotal());
        paiement.setModePaiement(dto.getModePaiement());
        paiement.setDatePaiement(LocalDateTime.now());
        paiement.setStatutPaiement(StatutPaiement.PAYE);

        Paiement saved = paiementRepository.save(paiement);

        try {
            if (saved.getStatutPaiement() == StatutPaiement.PAYE && reservation.getClient() != null) {
                userRepository.findById(reservation.getClient().getId()).ifPresent(user -> {
                    NotificationRequestDTO notification = new NotificationRequestDTO();
                    notification.setType(NotificationType.PAIEMENT_CONFIRME);
                    notification.setMessage("Votre paiement de " + saved.getMontant() + " DH a été confirmé.");
                    notification.setDate(LocalDateTime.now());
                    notification.setReservationId(reservation.getId());
                    notificationService.createAndSend(notification, user);
                });
            }
        } catch (Exception ignored) {
        }

        return paiementMapper.toDto(saved);
    }

    @Override
    public PaiementResponseDTO updatePaiement(Long id, PaiementRequestDTO dto, String email) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paiement introuvable"));
        if (paiement.getStatutPaiement() == StatutPaiement.PAYE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ce paiement est déjà validé");
        }
        if (dto.getModePaiement() != null) {
            paiement.setModePaiement(dto.getModePaiement());
        }
        return paiementMapper.toDto(paiementRepository.save(paiement));
    }

    @Override
    public void deletePaiement(Long id, String email) {
        Paiement paiement = paiementRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Paiement introuvable"));
        paiementRepository.delete(paiement);
    }

    @Override
    public long countPaiements() {
        return paiementRepository.countByStatutPaiement(StatutPaiement.PAYE);
    }
}
