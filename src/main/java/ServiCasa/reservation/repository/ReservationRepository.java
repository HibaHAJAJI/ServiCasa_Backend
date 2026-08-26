package ServiCasa.reservation.repository;

import ServiCasa.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ReservationRepository extends JpaRepository<Reservation,Long> {

}
