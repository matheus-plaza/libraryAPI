package plaza.libraryapi.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plaza.libraryapi.controller.dto.UsuarioDTO;
import plaza.libraryapi.controller.mappers.UsuarioMapper;
import plaza.libraryapi.model.Usuario;
import plaza.libraryapi.service.UsuarioService;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("usuarios")
@Tag(name = "Usuarios")
public class UsuarioController implements GenericController{

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @Operation(summary = "Salvar", description = "Cadastrar um novo usuario")
    @ApiResponses({
             @ApiResponse(responseCode = "201", description = "Usuario salvo com sucesso"),
             @ApiResponse(responseCode = "403", description = "Acesso negado"),
             @ApiResponse(responseCode = "409", description = "Usuario já cadastrado"),
    })
    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<Object> salvarUsuario(@RequestBody @Valid UsuarioDTO dto){
        Usuario user = mapper.toEntity(dto);
        service.salvar(user);

        URI location = gerarHeadLocation(user.getId());
        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "Obter por login", description = "Obter um usuario pelo login")

    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuario nao encontrado")
    })
    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping
    public ResponseEntity<UsuarioDTO> obterPorLogin(@RequestParam String login){
        return ResponseEntity.ok(mapper.toDTO(service.obterPorLogin(login)));
    }
}
