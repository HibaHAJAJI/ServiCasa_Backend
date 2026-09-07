package ServiCasa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ServiCasa.entity.Client;


public interface ClientRepository extends JpaRepository<Client,Long> {

}
