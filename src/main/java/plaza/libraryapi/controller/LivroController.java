package plaza.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plaza.libraryapi.controller.dto.CadastroLivroDTO;
import plaza.libraryapi.controller.dto.ErroResposta;
import plaza.libraryapi.controller.dto.PesquisaLivroDto;
import plaza.libraryapi.controller.mappers.LivroMapper;
import plaza.libraryapi.exceptions.RegistroDuplicadoException;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.service.LivroService;

import java.net.URI;
import java.util.UUID;

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

    @GetMapping("{id}")
    public ResponseEntity<PesquisaLivroDto> obterDetalhes(@PathVariable String id){

        return  livroService.buscarPorId(UUID.fromString(id))
                .map(livro-> {
                    PesquisaLivroDto dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletar(@PathVariable String id){
        return livroService.buscarPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletarLivro(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}

