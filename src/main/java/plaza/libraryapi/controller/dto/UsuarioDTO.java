package plaza.libraryapi.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

public record UsuarioDTO(String login,
                         @Email(message = "Email invalido") @NotBlank(message = "Campo Obrigatorio") String email,
                         @NotBlank(message = "Campo Obrigatorio") String senha,
                         List<String> roles){ }