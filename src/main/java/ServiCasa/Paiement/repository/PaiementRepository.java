package ServiCasa.Paiement.repository;

import ServiCasa.Paiement.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaiementRepository extends JpaRepository<Paiement,Long> {

}
