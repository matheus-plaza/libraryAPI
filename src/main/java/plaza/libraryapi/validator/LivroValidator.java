package plaza.libraryapi.validator;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import plaza.libraryapi.exceptions.RegistroDuplicadoException;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.repository.LivroRepository;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LivroValidator {

    private final LivroRepository repository;

    public void validarISBN(Livro livro){
        if (isbnRepetido(livro)){
            throw new RegistroDuplicadoException("Este ISBN ja existe em outro registro");
        }

    }

    private boolean isbnRepetido(Livro livro) {
        Optional<Livro> byIsbn = repository.findByIsbn(livro.getIsbn());

        if (livro.getId() == null){
            return byIsbn.isPresent();
        }
            //return livro.getId() != byIsbn.get().getId();
            return byIsbn.map(Livro::getId)
                    .stream()
                    .anyMatch(id -> !id.equals(livro.getId()));

    }
}
