package plaza.libraryapi.repository.specs;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;

public class LivroSpecs {

    public static Specification<Livro> isbnEqual(String isbn){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isbn"), isbn));
    }

    public static Specification<Livro> tituloLike(String titulo){
        return ((root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("titulo")), "%" + titulo.toUpperCase() + "%"));
    }

    public static Specification<Livro> generoEqual(GeneroLivro genero){
        return ((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("genero"), genero));
    }

    public static Specification<Livro> anoPublicacaoEqual(Integer anoPublicacao){
        //and to_char(data_publicacao, 'yyyy') = :anoPublicacao
        return (root, query, cb) -> cb.equal( cb.function("to_char", String.class,root.get("dataPublicacao"), cb.literal("YYYY")), anoPublicacao.toString());
    }

    public static Specification<Livro> nomeAutorLike(String nomeAutor){
        return (root, query, cb) -> {
            Join<Object, Object> joinAutor = root.join("autor", JoinType.INNER);
            return cb.like( cb.upper(joinAutor.get("nome")), "%" + nomeAutor.toUpperCase() + "%");
        };
//                cb.like(cb.upper(root.get("autor").get("nome")), "%" + nomeAutor.toUpperCase() + "%");

    }

}
