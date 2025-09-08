package plaza.libraryapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import plaza.libraryapi.model.Client;
import plaza.libraryapi.service.ClientService;

import java.net.URI;

@RestController
@RequestMapping("clients")
@RequiredArgsConstructor
public class ClientController implements GenericController {

    private final ClientService service;

    @PostMapping
    @PreAuthorize("hasRole('GERENTE')")
    public ResponseEntity<Void> salvar(@RequestBody Client client){
        service.salvar(client);

        URI location = gerarHeadLocation(client.getId());

        return ResponseEntity.created(location).build();
    }
}
