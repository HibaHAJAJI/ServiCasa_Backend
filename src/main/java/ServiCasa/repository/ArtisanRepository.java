package ServiCasa.repository;

import ServiCasa.entity.Artisan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArtisanRepository extends JpaRepository<Artisan,Long> {

    Page<Artisan> findBySpecialite(String specialite, Pageable pageable);

    Page<Artisan> findByVille(String ville, Pageable pageable);



}
