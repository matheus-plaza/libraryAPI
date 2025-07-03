package plaza.libraryapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import plaza.libraryapi.controller.dto.AutorDTO;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.service.AutorService;

import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("autores")
//http://localgost:8080/autores
public class AutorController {

    private AutorService service;

    public AutorController(AutorService service){
        this.service = service;
    }

//    @GetMapping("{id}")
//    public AutorDTO get(UUID id){
//        service.
//        AutorDTO autor = new AutorDTO();
//    }

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AutorDTO autor) {
        var entidade = autor.mapearAutor();
        service.salvar(entidade);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(entidade.getId())
                .toUri();

        return ResponseEntity.created(location).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<AutorDTO> obterDetalhes(@PathVariable("id") String id){
        UUID idBuscar = UUID.fromString(id);
        Optional<Autor> autorOptional = service.buscarPorId(idBuscar);

        if(autorOptional.isPresent()){
            Autor autor = autorOptional.get();
            AutorDTO dto = new AutorDTO(autor.getId(), autor.getNome(), autor.getDataNascimento(), autor.getNacionalidade());
            return ResponseEntity.ok(dto);
        }
        return ResponseEntity.notFound().build();
    }

}
