package plaza.libraryapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.repository.AutorRepository;
import plaza.libraryapi.repository.LivroRepository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class TransacaoService {

    @Autowired
    private AutorRepository autorRepository;
    @Autowired
    private LivroRepository livroRepository;

    @Transactional
    public void executar(){
        Autor autor = new Autor();
        autor.setNome("Jeremias");
        autor.setNacionalidade("Spanish");
        autor.setDataNascimento(LocalDate.of(1991, 1, 31));

        autorRepository.save(autor);

        Livro livro = new Livro(null, "80886-84874","Potatles", LocalDate.of(
                2012, 9, 12), GeneroLivro.FICCAO, BigDecimal.valueOf(100), autor, null, null, null);

        livroRepository.save(livro);

        if(autor.getNome().equals("Jeremias")){
            throw new RuntimeException("Rollback!");
        }
    }

    @Transactional
    public void updateManaged(){
        Livro livro = livroRepository.findByTitulo("UFO");
        livro.setDataPublicacao(LocalDate.of(2024, 8, 29));
        //livro sera salvo sem precisar do save()
    }

}
