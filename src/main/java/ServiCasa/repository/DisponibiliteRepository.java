package ServiCasa.repository;

import ServiCasa.entity.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface DisponibiliteRepository extends JpaRepository<Disponibilite,Long> {

    Optional<Disponibilite> findByArtisanId(Long artisanId);
}
