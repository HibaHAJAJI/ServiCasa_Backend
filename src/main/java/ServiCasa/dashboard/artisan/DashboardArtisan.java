package ServiCasa.dashboard.artisan;

import ServiCasa.entity.Artisan;
import ServiCasa.entity.User;
import ServiCasa.enums.StatutReservation;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.ReservationRepository;
import ServiCasa.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardArtisan {

    private final ReservationRepository reservationRepository;
    private final ArtisanRepository artisanRepository;

    public DashboardArtisanResponseDTO getDashboard(String email) {

        DashboardArtisanResponseDTO dto = new DashboardArtisanResponseDTO();

        Artisan artisan = artisanRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Artisan non trouvé"));

        dto.setNouvellesDemandes(reservationRepository.countByArtisan_IdAndStatutReservation(artisan.getId(), StatutReservation.EN_ATTENTE));
        dto.setInterventionsEnCours(reservationRepository.countByArtisan_IdAndStatutReservation(artisan.getId(), StatutReservation.EN_COURS));
        dto.setInterventionsTerminees(reservationRepository.countByArtisan_IdAndStatutReservation(artisan.getId(), StatutReservation.TERMINEE));

        return dto;
    }
}