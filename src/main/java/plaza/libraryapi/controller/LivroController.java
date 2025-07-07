package plaza.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import plaza.libraryapi.controller.dto.CadastroLivroDTO;
import plaza.libraryapi.controller.dto.ErroResposta;
import plaza.libraryapi.controller.dto.PesquisaLivroDto;
import plaza.libraryapi.controller.mappers.LivroMapper;
import plaza.libraryapi.exceptions.RegistroDuplicadoException;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.service.LivroService;

import java.net.URI;

@RestController
@RequestMapping("livros")
@RequiredArgsConstructor
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDto) {

        Livro livro = livroMapper.toEntity(livroDto);
        livroService.salvar(livro);
        URI location = gerarHeadLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

}

