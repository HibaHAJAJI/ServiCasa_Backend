package ServiCasa.repository;

import ServiCasa.entity.Disponibilite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface DisponibiliteRepository extends JpaRepository<Disponibilite,Long> {

    Page<Disponibilite> findByArtisanId(Long artisanId, Pageable pageable);

    List<Disponibilite> findByArtisanIdAndDateAndDisponibleIsTrue(Long artisanId, LocalDate date);
}
