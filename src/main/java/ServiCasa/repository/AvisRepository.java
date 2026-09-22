package ServiCasa.repository;

import ServiCasa.entity.Avis;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import ServiCasa.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AvisRepository extends JpaRepository<Avis, Long> {

    Page<Avis> findByArtisan(Artisan artisan, Pageable pageable);

    Page<Avis> findByClient(Client client, Pageable pageable);

    Optional<Avis> findByReservation(Reservation reservation);

    Double getAverageNoteByArtisan(Artisan artisan);

    Long countByArtisan(Artisan artisan);
}