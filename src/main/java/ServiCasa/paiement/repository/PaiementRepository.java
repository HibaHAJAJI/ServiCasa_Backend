package ServiCasa.paiement.repository;

import ServiCasa.paiement.entity.Paiement;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaiementRepository extends JpaRepository<Paiement,Long> {

}
