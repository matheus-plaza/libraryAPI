package plaza.libraryapi.validator;

import org.springframework.stereotype.Component;
import plaza.libraryapi.controller.dto.AutorDTO;
import plaza.libraryapi.exceptions.RegistroDuplicadoException;
import plaza.libraryapi.model.Autor;
import plaza.libraryapi.repository.AutorRepository;

import java.util.List;
import java.util.Optional;

@Component
public class AutorValidator {

    private AutorRepository autorRepository;

    public AutorValidator(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

//    public void validar(Autor autor){
//        Optional<Autor> optionalValidador = autorRepository.findById(autor.getId());
//
//        AutorDTO autorDTO = new AutorDTO(autor.getId(),
//                autor.getNome(),
//                autor.getDataNascimento(),
//                autor.getNacionalidade());
//
//        if(optionalValidador.isPresent()){
//           Autor validador = optionalValidador.get();
//            AutorDTO validadorDTO = new AutorDTO(validador.getId(),
//                    validador.getNome(),
//                    validador.getDataNascimento(),
//                    validador.getNacionalidade());
//
//
//            if(validadorDTO.equals(autorDTO)){
//                throw new RegistroDuplicadoException("Registro duplicado");
//            }
//        }
//    }

    public void validar(Autor autor){
        if(existeAutorCadastrado(autor)){
            throw new RegistroDuplicadoException("Autor ja cadastrado!");
        }
    }

    private boolean existeAutorCadastrado(Autor autor){
        Optional<Autor> autorEncontrado = autorRepository.findByNomeAndNacionalidadeAndDataNascimento(
                autor.getNome(), autor.getNacionalidade(), autor.getDataNascimento());

        if (autor.getId() == null){
            return autorEncontrado.isPresent();
        }
        return !autor.getId().equals(autorEncontrado.get().getId()) && autorEncontrado.isPresent();
    }
}
