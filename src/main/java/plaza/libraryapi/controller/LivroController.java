package plaza.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
@Tag(name = "Livros")
public class LivroController implements GenericController {

    private final LivroService livroService;
    private final LivroMapper livroMapper;

    @Operation(summary = "Salvar", description = "Cadastrar um novo livro")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Livro salvo com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Livro já cadastrado"),
    })
    @PostMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> salvar(@RequestBody @Valid CadastroLivroDTO livroDto) {

        Livro livro = livroMapper.toEntity(livroDto);
        livroService.salvar(livro);
        URI location = gerarHeadLocation(livro.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Obter detalhes", description = "Obter detalhes de um livro pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Livro encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<PesquisaLivroDto> obterDetalhes(@PathVariable String id){

        return  livroService.buscarPorId(UUID.fromString(id))
                .map(livro-> {
                    PesquisaLivroDto dto = livroMapper.toDTO(livro);
                    return ResponseEntity.ok(dto);

                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Deletar", description = "Deletar um livro pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro deletado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado")
    })
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    public ResponseEntity<Object> deletar(@PathVariable String id){
        return livroService.buscarPorId(UUID.fromString(id))
                .map(livro -> {
                    livroService.deletarLivro(livro);
                    return ResponseEntity.noContent().build();
                }).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Pesquisar", description = "Pesquisar livros com filtros opcionais")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Pesquisa realizada com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado")
    })
    @GetMapping
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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

    @Operation(summary = "Atualizar", description = "Atualizar os dados de um livro pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Livro atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Livro não encontrado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação")
    })
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
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

