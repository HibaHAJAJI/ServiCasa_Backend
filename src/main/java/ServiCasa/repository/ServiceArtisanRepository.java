package ServiCasa.repository;

import ServiCasa.entity.ServiceArtisan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ServiceArtisanRepository extends JpaRepository<ServiceArtisan, Long> {

    Page<ServiceArtisan> findByArtisanId(Long artisanId, Pageable pageable);
}