package ServiCasa.repository;

import ServiCasa.entity.Artisan;
import ServiCasa.entity.Ville;
import ServiCasa.enums.StatutCompte;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ArtisanRepository extends JpaRepository<Artisan,Long> {

    Page<Artisan> findBySpecialiteNom(String specialite, Pageable pageable);

    Page<Artisan> findByVilleNom(String ville, Pageable pageable);

    Optional<Artisan> findByEmail(String email);

    Page<Artisan> findByStatutCompte(StatutCompte statutCompte,Pageable pageable);

    @Query("SELECT v.nom FROM Ville v ORDER BY v.nom")
    List<String> findAllVilles();

}
