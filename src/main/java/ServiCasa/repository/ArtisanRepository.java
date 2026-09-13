package ServiCasa.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ServiCasa.entity.Artisan;


public interface ArtisanRepository extends JpaRepository<Artisan,Long> {

    Page<Artisan> findBySpecialiteContainingIgnoreCase(String specialite, Pageable pageable);


}
