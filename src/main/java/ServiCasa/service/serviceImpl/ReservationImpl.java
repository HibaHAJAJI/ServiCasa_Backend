package ServiCasa.service.serviceImpl;


import ServiCasa.dto.request.ReservationRequestDTO;
import ServiCasa.dto.response.ReservationResponseDTO;
import ServiCasa.entity.*;
import ServiCasa.enums.StatutReservation;
import ServiCasa.mapper.ReservationMapper;
import ServiCasa.repository.*;
import ServiCasa.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;



@Service
@RequiredArgsConstructor
public class ReservationImpl implements ReservationService {

    private final ReservationMapper mapper;
    private final ReservationRepository repository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;
    private final DemandeServiceRepository demandeServiceRepository;
    private final UserRepository userRepository;

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
            demandeService = demandeServiceRepository.findById(dto.getDemandeServiceId()).orElse(null);
        }

        Reservation reservation = mapper.toEntity(dto);
        reservation.setArtisan(artisan);
        reservation.setClient(client);
        reservation.setDemandeService(demandeService);

        if (reservation.getStatutReservation() == null) {
            reservation.setStatutReservation(StatutReservation.EN_ATTENTE);
        }

        if (reservation.getDateReservation() == null) {
            reservation.setDateReservation(java.time.LocalDateTime.now());
        }

        return mapper.toDto(repository.save(reservation));
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
        return mapper.toDto(repository.save(reservation));
    }

    @Override
    public ReservationResponseDTO findReservationById(Long id){
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune réservation !"));
        return mapper.toDto(reservation);
    }

    @Override
    public Page<ReservationResponseDTO> findAllReservations(Pageable pageable){
        return repository.findAll(pageable).map(mapper::toDto);
    }

    @Override
    public ReservationResponseDTO updateReservation(Long id, ReservationRequestDTO dto){
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucune réservation !"));

        mapper.updateReservationDto(dto, reservation);

        Artisan artisan = artisanRepository.findById(dto.getArtisanId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Artisan !"));
        Client client = clientRepository.findById(dto.getClientId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Aucun Client !"));
        DemandeService demandeService = (dto.getDemandeServiceId() != null)
                ? demandeServiceRepository.findById(dto.getDemandeServiceId()).orElse(null) : null;

        reservation.setArtisan(artisan);
        reservation.setClient(client);
        reservation.setDemandeService(demandeService);

        return mapper.toDto(repository.save(reservation));
    }

    @Override
    public Page<ReservationResponseDTO> findReservationsByClient(Long clientId, Pageable pageable){
        Page<Reservation> reservations = repository.findByClientId(clientId, pageable);
        return reservations.map(mapper::toDto);
    }

    @Override
    public Page<ReservationResponseDTO> getPendingReservationsByArtisan(String email, Pageable pageable) {
        User user = userRepository.findByEmail(email).orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Artisan artisan = artisanRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        Page<Reservation> reservations = repository.findByArtisanIdAndStatutReservation(artisan.getId(), StatutReservation.EN_ATTENTE, pageable);

        return reservations.map(mapper::toDto);
    }

    @Override
    public Page<ReservationResponseDTO> getLatestReservations(Pageable pageable){
        return repository.findAllByOrderByDateReservationDesc(pageable).map(mapper::toDto);
    }

    @Override
    public java.util.List<ReservationResponseDTO> getMyReservations(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable"));

        java.util.List<Reservation> reservations = repository.findByClientIdOrderByDateReservationDesc(client.getId());

        return reservations.stream().map(mapper::toDto).collect(java.util.stream.Collectors.toList());
    }

    @Override
    public void cancelReservation(Long id) {
        Reservation reservation = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        StatutReservation statut = reservation.getStatutReservation();
        if (statut != StatutReservation.EN_ATTENTE && statut != StatutReservation.ACCEPTEE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cette réservation ne peut pas être annulée.");
        }

        reservation.setStatutReservation(StatutReservation.ANNULEE);
        repository.save(reservation);
    }

}
