package plaza.libraryapi.service;

import org.springframework.stereotype.Service;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.repository.AutorRepository;

import java.util.Optional;
import java.util.UUID;

@Service
public class AutorService {

    private final AutorRepository repository;

    public AutorService(AutorRepository repository){
        this.repository = repository;
    }

    public Autor salvar(Autor autor){
        return repository.save(autor);
    }

    public Optional<Autor> buscarPorId(UUID id){
        return repository.findById(id);
    }
}
