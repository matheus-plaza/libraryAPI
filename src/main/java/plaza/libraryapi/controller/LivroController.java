package plaza.libraryapi.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import plaza.libraryapi.controller.dto.CadastroLivroDTO;
import plaza.libraryapi.controller.dto.ErroResposta;
import plaza.libraryapi.controller.dto.PesquisaLivroDto;
import plaza.libraryapi.controller.mappers.LivroMapper;
import plaza.libraryapi.exceptions.RegistroDuplicadoException;
import plaza.libraryapi.model.GeneroLivro;
import plaza.libraryapi.model.Livro;
import plaza.libraryapi.service.LivroService;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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

    @GetMapping
    public ResponseEntity<Page<PesquisaLivroDto>> pesquisa(
            @RequestParam(value = "isbn", required = false)
            String isbn,
            @RequestParam(value = "titulo", required = false)
            String titulo,
            @RequestParam(value = "nome-autor", required = false)
            String nomeAutor,
            @RequestParam(value = "genero", required = false)
            GeneroLivro genero,
            @RequestParam(value = "ano-publicacao", required = false)
            Integer anoPublicacao,
            @RequestParam(value = "pagina", defaultValue = "0")
            Integer pagina,
            @RequestParam(value = "tamanho-pagina", defaultValue = "0")
            Integer tamanhoPagina){

        Page<Livro> paginaResultado = livroService.pesquisa(isbn, titulo, nomeAutor, genero, anoPublicacao, pagina, tamanhoPagina);

         Page<PesquisaLivroDto> resultado = paginaResultado.map(livroMapper::toDTO);

        return ResponseEntity.ok(resultado);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid CadastroLivroDTO dto){
        return livroService.buscarPorId(UUID.fromString(id)).map(livro -> {
            Livro entity = livroMapper.toEntity(dto);
            livro.setGenero(entity.getGenero());
            livro.setIsbn(entity.getIsbn());
            livro.setPreco(entity.getPreco());
            livro.setTitulo(entity.getTitulo());
            livro.setDataPublicacao(entity.getDataPublicacao());
            livro.setAutor(entity.getAutor());

            livroService.atualizar(livro);

            return ResponseEntity.noContent().build();
        }).orElseGet(() -> ResponseEntity.notFound().build());
    }

}

