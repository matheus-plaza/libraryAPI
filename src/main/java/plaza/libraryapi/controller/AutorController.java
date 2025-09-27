package plaza.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plaza.libraryapi.controller.dto.AutorDTO;
import plaza.libraryapi.controller.mappers.AutorMapper;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.service.AutorService;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("autores")
@Tag(name = "Autores")
@Slf4j
//http://localgost:8080/autores
public class AutorController implements GenericController {

    private final AutorService service;
    private final AutorMapper mapper;

    @Operation(summary = "Salvar", description = "Cadastrar um novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Autor salvo com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "422", description = "Erro de validação"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado"),
    })
    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody @Valid AutorDTO dto) {
        log.info("Salvando autor: {}", dto.nome());
        var autor = mapper.toEntity(dto);
        service.salvar(autor);
        URI location = gerarHeadLocation(autor.getId());

        log.info("Autor salvo com sucesso: {}", autor.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Atualizar", description = "Atualizar um autor existente")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Autor atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Autor nao encontrado"),
            @ApiResponse(responseCode = "409", description = "Autor já cadastrado"),
    })
    @PreAuthorize("hasRole('GERENTE')")
    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody @Valid AutorDTO dto) {
        Optional<Autor> autorOptional = service.buscarPorId(UUID.fromString(id));
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Autor autor = autorOptional.get();
        autor.setNome(dto.nome());
        autor.setNacionalidade(dto.nacionalidade());
        autor.setDataNascimento(dto.dataNascimento());

        service.atualizar(autor);

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obter detalhes", description = "Retorna os dados do autor pelo ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Autor encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Autor não encontrado"),
    })
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id) {
        UUID idBuscar = UUID.fromString(id);

        return service.buscarPorId(idBuscar)
                .map(autor -> {
                    AutorDTO dto = mapper.toDTO(autor);
                    return ResponseEntity.ok(dto);
                }).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @Operation(summary = "Deletar", description = "Cadastrar um novo autor")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Nao encontrado"),
            @ApiResponse(responseCode = "400", description = "Autor vinculado a um livro"),
    })
    @PreAuthorize("hasRole('GERENTE')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable String id) {
        log.info("Deletando autor de id: {}", id);
        UUID idBuscar = UUID.fromString(id);
        Optional<Autor> autorOptional = service.buscarPorId(idBuscar);
        if (autorOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        service.deletarAutor(autorOptional.get());
        log.info("Autor deletado com sucesso: {}", id);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Pesquisar", description = "Realiza pesquisa de autores por parametros")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sucesso na pesquisa"),
    })
    @PreAuthorize("hasAnyRole('OPERADOR', 'GERENTE')")
    @GetMapping
    public ResponseEntity<List<AutorDTO>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String nacionalidade) {

        List<Autor> autores = service.pesquisaByExample(nome, nacionalidade);
        List<AutorDTO> autoresDTO = autores.stream().
                map(mapper::toDTO).collect(Collectors.toList());

        return ResponseEntity.ok(autoresDTO);
    }

}
