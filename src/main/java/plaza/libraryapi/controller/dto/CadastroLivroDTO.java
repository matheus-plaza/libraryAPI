package plaza.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import org.hibernate.validator.constraints.ISBN;
import plaza.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Schema(name = "CadastroLivro")
public record CadastroLivroDTO(
                               @ISBN
                               @NotBlank(message = "Campo obrigatorio")
                               String isbn,
                               @NotBlank(message = "Campo obrigatorio")
                               String titulo,
                               @NotNull(message = "Campo obrigatorio")
                               @Past(message = "Nao permitido data futura")
                               LocalDate dataPublicacao,
                               GeneroLivro genero,
                               BigDecimal preco,
                               @NotNull(message = "Campo obrigatorio")
                               UUID idAutor) {
}
