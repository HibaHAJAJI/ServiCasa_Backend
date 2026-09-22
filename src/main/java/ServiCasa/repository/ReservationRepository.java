package ServiCasa.repository;

import ServiCasa.entity.Reservation;
import ServiCasa.enums.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Page<Reservation> findByClientId(Long clientId, Pageable pageable);

    long countByArtisanIdAndStatutReservation(Long artisanId, StatutReservation statutReservation);

    long countByStatutReservation(StatutReservation statutReservation);

    Page<Reservation> findByArtisanIdAndStatutReservation(Long artisanId, StatutReservation statuts, Pageable pageable);

    Page<Reservation> findByArtisanIdAndStatutReservationIn(Long artisanId, List<StatutReservation> statuts, Pageable pageable);

    Page<Reservation> findByClientIdOrderByDateReservationDesc(Long clientId,Pageable pageable);

    Page<Reservation> findAllByOrderByDateReservationDesc(Pageable pageable);
}
