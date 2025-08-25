package plaza.libraryapi.controller;

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
public class UsuarioController implements GenericController{

    private final UsuarioService service;
    private final UsuarioMapper mapper;

    @PreAuthorize("hasRole('GERENTE')")
    @PostMapping
    public ResponseEntity<Object> salvarUsuario(@RequestBody UsuarioDTO dto){
        Usuario user = mapper.toEntity(dto);
        service.salvar(user);

        URI location = gerarHeadLocation(user.getId());
        return ResponseEntity.created(location).build();
    }

    @PreAuthorize("hasRole('GERENTE')")
    @GetMapping
    public ResponseEntity<UsuarioDTO> obterPorLogin(@RequestParam String login){
        return ResponseEntity.ok(mapper.toDTO(service.obterPorLogin(login)));
    }
}
