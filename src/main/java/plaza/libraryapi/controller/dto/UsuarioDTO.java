package plaza.libraryapi.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(name = "Usuario")
public record UsuarioDTO(String login,
                         @Email(message = "Email invalido") @NotBlank(message = "Campo Obrigatorio") String email,
                         @NotBlank(message = "Campo Obrigatorio") String senha,
                         List<String> roles){ }