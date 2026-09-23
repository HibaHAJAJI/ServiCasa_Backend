package ServiCasa.repository;

import ServiCasa.entity.Disponibilite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface DisponibiliteRepository extends JpaRepository<Disponibilite,Long> {

    Page<Disponibilite> findByArtisanId(Long artisanId, Pageable pageable);
}
