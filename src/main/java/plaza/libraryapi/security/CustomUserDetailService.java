package plaza.libraryapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import plaza.libraryapi.model.Usuario;
import plaza.libraryapi.service.UsuarioService;

@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {

    private final UsuarioService usuarioService;

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario user = usuarioService.obterPorLogin(login);

        if(user == null){
            throw new UsernameNotFoundException("Usuario nao encontrado!");
        }

        return User.builder()
                .username(user.getLogin())
                .password(user.getSenha())
                .roles(user.getRoles().toArray(new String[user.getRoles().size()]))
                .build();
    }
}
