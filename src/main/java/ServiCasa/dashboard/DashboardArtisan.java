package ServiCasa.dashboard;

import ServiCasa.enums.StatutReservation;
import ServiCasa.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DashboardArtisan {

    private final ReservationRepository reservationRepository;

    public DashboardArtisanResponseDTO getDashboard(Long artisanId) {

        DashboardArtisanResponseDTO dto = new DashboardArtisanResponseDTO();

        dto.setNouvellesDemandes(
                reservationRepository.countByArtisanIdAndStatut(
                        artisanId,
                        StatutReservation.EN_ATTENTE
                )
        );

        dto.setInterventionsEnCours(
                reservationRepository.countByArtisanIdAndStatut(
                        artisanId,
                        StatutReservation.EN_COURS
                )
        );

        dto.setInterventionsTerminees(
                reservationRepository.countByArtisanIdAndStatut(
                        artisanId,
                        StatutReservation.TERMINEE
                )
        );

        return dto;
    }
}