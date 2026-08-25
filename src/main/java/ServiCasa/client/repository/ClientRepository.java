package ServiCasa.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ServiCasa.client.entity.Client;


public interface ClientRepository extends JpaRepository<Client,Long> {

}
