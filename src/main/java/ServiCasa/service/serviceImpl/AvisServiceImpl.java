package ServiCasa.service.serviceImpl;

import ServiCasa.dto.request.AvisRequestDTO;
import ServiCasa.dto.response.AvisResponseDTO;
import ServiCasa.entity.*;
import ServiCasa.enums.StatutReservation;
import ServiCasa.mapper.AvisMapper;
import ServiCasa.repository.AvisRepository;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ClientRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import ServiCasa.service.AvisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AvisServiceImpl implements AvisService {

    private final AvisMapper mapper;
    private final AvisRepository avisRepository;
    private final ReservationRepository reservationRepository;
    private final ClientRepository clientRepository;
    private final ArtisanRepository artisanRepository;
    private final UserRepository userRepository;

    @Override
    public AvisResponseDTO addAvis(AvisRequestDTO dto, String clientEmail) {

        if (clientEmail == null) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Client non authentifié");
        }

        User user = userRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable"));

        Client client = clientRepository.findById(user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Client introuvable"));

        Reservation reservation = reservationRepository.findById(dto.getReservationId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        if (!reservation.getClient().getId().equals(client.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cette réservation ne vous appartient pas");
        }

        if (reservation.getStatutReservation() != StatutReservation.TERMINEE) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Vous ne pouvez donner un avis que pour une réservation terminée");
        }

        if (avisRepository.findByReservation(reservation).isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Un avis existe déjà pour cette réservation");
        }

        Artisan artisan = reservation.getArtisan();

        Avis avis = mapper.toEntity(dto);
        avis.setClient(client);
        avis.setArtisan(artisan);
        avis.setReservation(reservation);
        avis.setDateCreation(LocalDateTime.now());

        Avis savedAvis = avisRepository.save(avis);

        return mapper.toDto(savedAvis);
    }

    @Override
    public Page<AvisResponseDTO> getAvisByArtisan(Long artisanId, Pageable pageable) {
        Artisan artisan = artisanRepository.findById(artisanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        return avisRepository.findByArtisan(artisan, pageable).map(mapper::toDto);
    }

    @Override
    public Double getMoyenneArtisan(Long artisanId) {
        Artisan artisan = artisanRepository.findById(artisanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        Double moyenne = avisRepository.getAverageNoteByArtisan(artisan);
        return moyenne != null ? moyenne : 0.0;
    }

    @Override
    public Long getNombreAvisArtisan(Long artisanId) {
        Artisan artisan = artisanRepository.findById(artisanId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Artisan introuvable"));

        return avisRepository.countByArtisan(artisan);
    }

    @Override
    public boolean existsByReservationId(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));
        return avisRepository.findByReservation(reservation).isPresent();
    }

    @Override
    public AvisResponseDTO getAvisByReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Réservation introuvable"));

        Avis avis = avisRepository.findByReservation(reservation)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Avis introuvable"));

        return mapper.toDto(avis);
    }
}