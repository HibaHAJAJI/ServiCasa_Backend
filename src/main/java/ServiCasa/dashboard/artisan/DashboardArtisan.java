package ServiCasa.dashboard.artisan;

import ServiCasa.entity.Artisan;
import ServiCasa.entity.Paiement;
import ServiCasa.enums.StatutPaiement;
import ServiCasa.enums.StatutReservation;
import ServiCasa.repository.ArtisanRepository;
import ServiCasa.repository.PaiementRepository;
import ServiCasa.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardArtisan {

    private final ReservationRepository reservationRepository;
    private final ArtisanRepository artisanRepository;
    private final PaiementRepository paiementRepository;

    public DashboardArtisanResponseDTO getDashboard(String email) {

        DashboardArtisanResponseDTO dto = new DashboardArtisanResponseDTO();

        Artisan artisan = artisanRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Artisan non trouvé"));

        dto.setNouvellesDemandes(reservationRepository.countByArtisanIdAndStatutReservation(artisan.getId(), StatutReservation.EN_ATTENTE));
        dto.setInterventionsEnCours(reservationRepository.countByArtisanIdAndStatutReservation(artisan.getId(), StatutReservation.EN_COURS));
        dto.setInterventionsTerminees(reservationRepository.countByArtisanIdAndStatutReservation(artisan.getId(), StatutReservation.TERMINEE));

        LocalDateTime debutMois = LocalDateTime.now().withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime finMois = debutMois.plusMonths(1).minusNanos(1);

        List<Paiement> paiementsDuMois = paiementRepository.findByReservationArtisanIdAndStatutPaiementAndDatePaiementBetween(
                artisan.getId(), StatutPaiement.PAYE, debutMois, finMois);

        BigDecimal total = paiementsDuMois.stream()
                .map(Paiement::getMontant)
                .filter(m -> m != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        dto.setRevenusDuMois(total.doubleValue());

        return dto;
    }
}
