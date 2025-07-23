package plaza.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
class LivroRepositoryTest {

    @Autowired
    LivroRepository livroRepository;
    @Autowired
    AutorRepository autorRepository;

    @Test
    void salvarTest(){

        var id = UUID.fromString("dd2a5ce7-24a5-4d3a-8563-9c3137779413");
        Autor autor = autorRepository.findById(id).orElse(null);

        Livro livro = new Livro(null, "90886-84874",
                "blabla", LocalDate.of(2000, 9, 2), GeneroLivro.FICCAO,
                BigDecimal.valueOf(100), null, null, null, autor);

        livroRepository.save(livro);
    }
    @Test
    void salvarCascadeTest(){
        Autor autor = new Autor();
        autor.setNome("Joao");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        Livro livro = new Livro(null, "90886-84874","Outro Livro", null,
                GeneroLivro.FICCAO, BigDecimal.valueOf(100), null, null, null, autor);

        livroRepository.save(livro);
    }
    @Test
    void atualizarAutorDoLivroTest(){
        var idLivro = UUID.fromString("d434fb0f-a14b-46fa-b013-1055ad3ce86d");
        Livro livro = livroRepository.findById(idLivro).orElse(null);

        var idAutor = UUID.fromString("72913145-2019-4d70-9657-f105316a9d2d");
        Autor autor = autorRepository.findById(idAutor).orElse(null);

        livro.setAutor(autor);

        livroRepository.save(livro);
    }
    @Test
    void deletarTest(){
        var idLivro = UUID.fromString("0787f826-2b1c-4ca3-905b-3613736b64ce");
        livroRepository.deleteById(idLivro);
    }
    @Test
    void buscarLivroTest(){
        var idLivro = UUID.fromString("d434fb0f-a14b-46fa-b013-1055ad3ce86d");
        Livro livro = livroRepository.findById(idLivro).orElse(null);

        System.out.println("livro: " + livro.getTitulo());
        System.out.println("autor: " + livro.getAutor().getNome());
    }
    @Test
    void buscarPorTitulo(){
        Livro lista = livroRepository.findByTitulo("Bananas");
        System.out.println(lista);
    }
    @Test
    void buscarPorIsbn(){
        Optional<Livro> livro = livroRepository.findByIsbn("90886-84874");
        livro.ifPresent(System.out::println);
    }
    @Test
    void buscarPorTituloAndPreco(){
        List<Livro> lista = livroRepository.findByTituloAndPreco("UFO", new BigDecimal(100));
        lista.forEach(System.out::println);
    }
    @Test
    void queryJPQLTest(){
        List<Livro> l = livroRepository.buscarPorTitulo();
        l.forEach(System.out::println);
    }
    @Test
    void nomeLivrosTest(){
        List<String> l = livroRepository.nomeDosLivros();
        l.forEach(System.out::println);
    }
    @Test
    void tituloAutorTest(){
        List<String> l = livroRepository.livroAutor();
        l.forEach(System.out::println);
    }
    @Test
    void listarPorGeneroTest(){
        List<Livro> l = livroRepository.listarPorGenero(GeneroLivro.CIENCIA);
        l.forEach(System.out::println);
    }
    @Test
    void deletarPorTituloTest(){
        livroRepository.deleteByTitulo("poteatles");
    }
    @Test
    void modificarDataTest(){
        livroRepository.modificarDataPublicacaoPorTitulo(LocalDate.of(
                2015, 10, 23), "poteatles");
    }


}