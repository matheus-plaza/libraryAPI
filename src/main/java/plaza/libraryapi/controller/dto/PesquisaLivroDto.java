package plaza.libraryapi.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import plaza.libraryapi.model.GeneroLivro;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record PesquisaLivroDto(UUID id,
                               @NotBlank(message = "Campo obrigatorio")
                               String isbn,
                               @NotBlank(message = "Campo obrigatorio")
                               String titulo,
                               @NotNull(message = "Campo obrigatorio")
                               @Past(message = "Nao permitido data futura")
                               LocalDate dataPublicacao,
                               GeneroLivro genero, BigDecimal preco,
                               AutorDTO autor) {
}
