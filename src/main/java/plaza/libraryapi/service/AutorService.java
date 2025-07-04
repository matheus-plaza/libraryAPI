package plaza.libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import plaza.libraryapi.exceptions.OperacaoNaoPermitidaException;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.repository.AutorRepository;
import plaza.libraryapi.repository.LivroRepository;
import plaza.libraryapi.validator.AutorValidator;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AutorService {

    private final AutorRepository repository;
    private final AutorValidator autorValidador;
    private final LivroRepository livroRepository;

    public Autor salvar(Autor autor){
        autorValidador.validar(autor);
        return repository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id){
        return repository.findById(id);
    }

    public void deletarAutor(Autor autor){
        if (possuiLivro(autor)){
            throw new OperacaoNaoPermitidaException("Nao é possivel deletar pois o autor possui livros !");
        }
        repository.deleteById(autor.getId());
    }

    public List<Autor> buscarFiltro(String nome, String nacionalidade){
        if(nome != null && nacionalidade != null){
            return repository.findByNomeAndNacionalidade(nome, nacionalidade);
        } else if (nome != null) {
            return repository.findByNome(nome);
        }
        else if(nacionalidade != null){
            return repository.findByNacionalidade(nacionalidade);
        }
        return repository.findAll();
    }

    public List<Autor> pesquisaByExample(String nome, String nacionalidade){
        var autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<Autor> autorExample = Example.of(autor, matcher);
        return repository.findAll(autorExample);
    }

    public void atualizar(Autor autor) {
        if(autor.getId() == null){
            throw new IllegalArgumentException("Autor nao salvo na base de dados");
        }
        autorValidador.validar(autor);
        repository.save(autor);
    }

    public boolean possuiLivro(Autor autor){
        return livroRepository.existsByAutor(autor);
    }
}
