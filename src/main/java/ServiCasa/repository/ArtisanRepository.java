package ServiCasa.repository;

import ServiCasa.entity.Artisan;
import ServiCasa.enums.StatutCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ArtisanRepository extends JpaRepository<Artisan,Long> {

    Page<Artisan> findBySpecialite(String specialite, Pageable pageable);

    Page<Artisan> findByVille(String ville, Pageable pageable);

    Optional<Artisan> findByEmail(String email);

    Page<Artisan> findByStatutCompte(StatutCompte statutCompte,Pageable pageable);


}
