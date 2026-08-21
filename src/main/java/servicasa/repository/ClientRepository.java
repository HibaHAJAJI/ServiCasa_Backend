package servicasa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicasa.entity.Client;


public interface ClientRepository extends JpaRepository<Client,Long> {
}
