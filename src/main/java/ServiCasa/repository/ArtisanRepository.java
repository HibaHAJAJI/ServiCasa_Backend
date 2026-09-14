package ServiCasa.repository;

import ServiCasa.entity.Artisan;
import ServiCasa.enums.SpecialiteArtisan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ArtisanRepository extends JpaRepository<Artisan,Long> {

    Page<Artisan> findBySpecialiteContainingIgnoreCase(SpecialiteArtisan specialite, Pageable pageable);

    Page<Artisan> findByVilleContainingIgnoreCase(String ville, Pageable pageable);



}
