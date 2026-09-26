package ServiCasa.repository;

import ServiCasa.entity.DemandeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DemandeServiceRepository extends JpaRepository<DemandeService, Long> {

    Page<DemandeService> findByCategorieId(Long categorieId, Pageable pageable);
}