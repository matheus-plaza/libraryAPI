package plaza.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface LivroRepository extends JpaRepository<Livro, UUID> {

    //Query method
//    select * from livro where id_autor  = id
    List<Livro> findByAutor(Autor autor);

    Livro findByTitulo(String titulo);

    List<Livro> findByIsbn(String isbn);

    List<Livro> findByTituloAndPreco(String titulo, BigDecimal preco);

    //select * from livro where data_publicacao between ? and ?
    List<Livro> findByDataPublicacaoBetween(LocalDate localDate1, LocalDate localDate2);


    //JPQL query
    @Query(" select l from Livro as l order by l.titulo ")
    List<Livro> buscarPorTitulo();

    @Query("""
            select l.titulo 
            from Livro as l
            """)
    List<String> nomeDosLivros();

    @Query("""
            select l.titulo, a.nome
            from Livro l
            join l.autor a""")
    List<String> livroAutor();

    @Query("select l from Livro l where l.genero = ?1")
    List<Livro> listarPorGenero(GeneroLivro generoLivro);

    @Modifying
    @Transactional
    @Query("update Livro set dataPublicacao = ?1 where titulo = ?2")
    void modificarDataPublicacaoPorTitulo(LocalDate date, String titulo);


    @Modifying
    @Transactional
    void deleteByTitulo(String titulo);







}
