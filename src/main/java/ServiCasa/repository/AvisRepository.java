package ServiCasa.repository;

import ServiCasa.entity.Avis;
import ServiCasa.entity.Artisan;
import ServiCasa.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AvisRepository extends JpaRepository<Avis, Long> {

    Page<Avis> findByArtisan(Artisan artisan, Pageable pageable);

    Page<Avis> findByClient(Client client, Pageable pageable);

    @Query("SELECT AVG(a.note) FROM Avis a WHERE a.artisan = ?1")
    Double getAverageNoteByArtisan(Artisan artisan);

    @Query("SELECT COUNT(a) FROM Avis a WHERE a.artisan = ?1")
    Long countByArtisan(Artisan artisan);
}