package plaza.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import plaza.libraryapi.model.Autor;

import java.time.LocalDate;
import java.util.UUID;

public record AutorDTO(UUID id,
                       @NotBlank(message = "Campo obrigatorio")
                       @Size(max = 50, message = "Tamanhao maximo do campo nome: 100")
                       String nome,
                       @NotNull(message = "campo obrigatorio")
                       @Past(message = "Não pode ser uma data futura")
                       LocalDate dataNascimento,
                       @NotBlank(message = "Campo obrigatorio")
                       @Size(max = 50, message = "Tamanhao maximo do campo nacionalidade: 50")
                       String nacionalidade) {

    public Autor mapearAutor(){
        Autor autor = new Autor();
        autor.setNome(nome);
        autor.setNacionalidade(nacionalidade);
        autor.setDataNascimento(dataNascimento);
        return autor;
    }

}
