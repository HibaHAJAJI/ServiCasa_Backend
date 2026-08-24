package servicasa.Artisan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicasa.Artisan.entity.Artisan;


public interface ArtisanRepository extends JpaRepository<Artisan,Long> {

}
