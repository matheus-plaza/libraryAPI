package plaza.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plaza.libraryapi.model.Autor;

import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {
}
