package plaza.libraryapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import plaza.libraryapi.controller.dto.AutorDTO;
import plaza.libraryapi.controller.dto.ErroResposta;
import plaza.libraryapi.exceptions.OperacaoNaoPermitidaException;
import plaza.libraryapi.exceptions.RegistroDuplicadoException;
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
//http://localgost:8080/autores
public class AutorController {

    private final AutorService service;

    @PostMapping
    public ResponseEntity<Object> salvar(@RequestBody AutorDTO autor) {
        try{
            var entidade = autor.mapearAutor();
            service.salvar(entidade);
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(entidade.getId())
                    .toUri();

            return ResponseEntity.created(location).build();
    }catch (RegistroDuplicadoException e) {

            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> atualizar(@PathVariable String id, @RequestBody AutorDTO dto){
        try {
            Optional<Autor> autorOptional = service.buscarPorId(UUID.fromString(id));
            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            Autor autor = autorOptional.get();
            autor.setNome(dto.nome());
            autor.setNacionalidade(dto.nacionalidade());
            autor.setDataNascimento(dto.dataNascimento());

            service.atualizar(autor);

            return ResponseEntity.ok().build();
        }catch(RegistroDuplicadoException e) {
            var erroDTO = ErroResposta.conflito(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
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

    @DeleteMapping("{id}")
    public ResponseEntity<Object> deletarAutor(@PathVariable String id) {
        try{
            UUID idBuscar = UUID.fromString(id);
            Optional<Autor> autorOptional = service.buscarPorId(idBuscar);
            if (autorOptional.isEmpty()) {
                return ResponseEntity.notFound().build();
            }
            service.deletarAutor(autorOptional.get());
            return ResponseEntity.ok().build();
        } catch (OperacaoNaoPermitidaException e) {
            var erroDTO = ErroResposta.respostaPadrao(e.getMessage());
            return ResponseEntity.status(erroDTO.status()).body(erroDTO);
        }
    }

    @GetMapping
    public ResponseEntity<List<AutorDTO>> buscar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) String nacionalidade){

        List<Autor> autores = service.buscarFiltro(nome, nacionalidade);
        List<AutorDTO> autoresDTO = autores.stream().
                map(autor -> new AutorDTO(autor.getId(),
                        autor.getNome(), autor.getDataNascimento(),
                        autor.getNacionalidade())).collect(Collectors.toList());

        return ResponseEntity.ok(autoresDTO);
    }

}
