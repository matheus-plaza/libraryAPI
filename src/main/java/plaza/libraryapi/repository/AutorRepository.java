package plaza.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import plaza.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AutorRepository extends JpaRepository<Autor, UUID> {

    List<Autor>findByNome(String nome);
    List<Autor>findByNacionalidade(String nacionalidade);
    List<Autor>findByNomeAndNacionalidade(String nome, String nacionalidade);
    Optional<Autor>findByNomeAndNacionalidadeAndDataNascimento(String nome, String nacionalidade, LocalDate dataNascimento);
}
