package ServiCasa.repository;

import ServiCasa.entity.Paiement;
import ServiCasa.entity.Reservation;
import ServiCasa.enums.StatutPaiement;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PaiementRepository extends JpaRepository<Paiement,Long> {

    Optional<Paiement> findByReservation(Reservation reservation);

    Optional<Paiement> findByReservationId(Long reservationId);

    List<Paiement> findByReservationArtisanIdAndDatePaiementBetween(Long artisanId, LocalDateTime start, LocalDateTime end);

    long countByStatutPaiement(StatutPaiement statutPaiement);

    List<Paiement> findByReservationArtisanIdAndStatutPaiementAndDatePaiementBetween(Long artisanId, StatutPaiement statutPaiement, LocalDateTime start, LocalDateTime end);

    List<Paiement> findByReservationArtisanIdAndStatutPaiement(Long artisanId, StatutPaiement statutPaiement);
}
