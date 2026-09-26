package ServiCasa.repository;

import ServiCasa.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface VilleRepository extends JpaRepository<Ville, Long> {

    Optional<Ville> findByNom(String nom);
}
