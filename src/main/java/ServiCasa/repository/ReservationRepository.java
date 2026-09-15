package ServiCasa.repository;

import ServiCasa.entity.Reservation;
import ServiCasa.enums.StatutReservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation,Long> {

    Page<Reservation> findByClientId(Long clientId, Pageable pageable);

    long countByArtisanIdAndStatut(Long artisanId, StatutReservation statut);
}
