package servicasa.client.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import servicasa.client.entity.Client;

import java.util.Optional;


public interface ClientRepository extends JpaRepository<Client,Long> {

    Optional<Client> findByEmail(String email);
    boolean existsByEmail(String email);
}
