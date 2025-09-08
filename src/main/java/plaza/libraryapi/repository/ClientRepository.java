package plaza.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plaza.libraryapi.model.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID>{
    Client findByClientId(String clientId);
}
