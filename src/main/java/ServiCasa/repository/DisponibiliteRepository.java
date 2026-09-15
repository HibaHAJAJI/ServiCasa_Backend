package ServiCasa.repository;

import ServiCasa.entity.Disponibilite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface DisponibiliteRepository extends JpaRepository<Disponibilite,Long> {

    List<Disponibilite> findByArtisanId(Long artisanId);
}
