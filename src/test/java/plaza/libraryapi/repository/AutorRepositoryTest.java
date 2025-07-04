package plaza.libraryapi.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
public class AutorRepositoryTest {

    @Autowired
    AutorRepository repository;
    @Autowired
    LivroRepository livroRepository;

    @Test
    public void salvarTest() {
        Autor autor = new Autor();
        autor.setNome("Maria");
        autor.setNacionalidade("Brasileira");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        var autorSalvo = repository.save(autor);
        System.out.println(autorSalvo);
    }
    @Test
    public void atualizarTest() {
        var id = UUID.fromString("e77f78ca-e065-4c91-a1fc-6c4e9c534a5b");
        Optional<Autor> possivelAutor = repository.findById(id);

        if (possivelAutor.isPresent()) {
            Autor autorEncontrado = possivelAutor.get();
            System.out.println(possivelAutor);

            autorEncontrado.setDataNascimento(LocalDate.of(1960, 1, 30));

            repository.save(autorEncontrado);

            System.out.println("\n" + autorEncontrado);
        }
    }
    @Test
    public void listarTest() {
        List<Autor> lista = repository.findAll();
        lista.forEach(System.out::println);
    }
    @Test
    public void countTest(){
        System.out.println("Quantidade de autores: "+repository.count());
    }
    @Test
    public void deleteTest(){
        var id = UUID.fromString("e77f78ca-e065-4c91-a1fc-6c4e9c534a5b");
        repository.deleteById(id);
    }
    @Test
    void salvarAutorComLivrosTest(){
        Autor autor = new Autor();
        autor.setNome("Antonio");
        autor.setNacionalidade("Japones");
        autor.setDataNascimento(LocalDate.of(1951, 1, 31));

        Livro livro = new Livro(null, "66886-84874","Bananas", null,
                GeneroLivro.CIENCIA, BigDecimal.valueOf(200), autor, null, null, null);

        Livro livro2 = new Livro(null, "77886-84874","poteatles", null,
                GeneroLivro.CIENCIA, BigDecimal.valueOf(300), autor, null, null, null);

        autor.setLivros(new ArrayList<>());
        autor.getLivros().add(livro);
        autor.getLivros().add(livro2);

        repository.save(autor);
        livroRepository.saveAll(autor.getLivros());
    }
    @Test
    void imprimir(){
        var id = UUID.fromString("dd2a5ce7-24a5-4d3a-8563-9c3137779413");
        Autor autor = repository.findById(id).orElse(null);

        List<Livro> livros = livroRepository.findByAutor(autor);
        autor.setLivros(livros);

        autor.getLivros().forEach(System.out::println);
    }

}
