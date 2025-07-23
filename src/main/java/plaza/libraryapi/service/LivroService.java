package plaza.libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import plaza.libraryapi.controller.dto.CadastroLivroDTO;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.repository.LivroRepository;
import plaza.libraryapi.repository.specs.LivroSpecs;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static plaza.libraryapi.repository.specs.LivroSpecs.*;

@Service
@RequiredArgsConstructor
public class LivroService {

    public final LivroRepository livroRepository;

    public Livro salvar(Livro livro) {
        return livroRepository.save(livro);
    }

    public Optional<Livro> buscarPorId(UUID id) {
        return livroRepository.findById(id);
    }

    public void deletarLivro(Livro livro) {
        livroRepository.delete(livro);
    }

    public List<Livro> pesquisa(String isbn, String titulo, String nomeAutor, GeneroLivro genero, Integer anoPublicacao) {

//        Specification<Livro> specs = Specification.where(LivroSpecs.isbnEqual(isbn)
//                .and(LivroSpecs.tituloLike(titulo)
//                .and(LivroSpecs.generoEqual(genero))));

        //select * from livro where 0=0
        Specification<Livro> specs = Specification.where((root, query, criteriaBuilder) ->
                criteriaBuilder.conjunction());
        if (isbn != null) {
            specs = specs.and(isbnEqual(isbn));
        }
        if (titulo != null) {
            specs = specs.and(tituloLike(titulo));
        }
        if (genero != null) {
            specs = specs.and(generoEqual(genero));
        }

        if (anoPublicacao != null) {
            specs = specs.and(anoPublicacaoEqual(anoPublicacao));
        }

        if (nomeAutor != null) {
            specs = specs.and(nomeAutorLike(nomeAutor));
        }

        return livroRepository.findAll(specs);
    }

    public void atualizar(Livro livro) {
        if (livro.getId() == null) {
            throw new IllegalArgumentException("Para atualizar eh necessario o livro ja estar cadastrado");
        }
        livroRepository.save(livro);
    }

}
