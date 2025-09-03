package plaza.libraryapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import plaza.libraryapi.model.Usuario;
import plaza.libraryapi.service.UsuarioService;

@Component
@RequiredArgsConstructor
public class SecurityService {

    private final UsuarioService usuarioService;


    //quando se cadastra um novo livro/autor
    public Usuario obterUsuarioLogado(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication instanceof CustomAuthentication customAuth) {
            return  customAuth.getUsuario();
        }

        return null;
    }
}
