package plaza.libraryapi.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import plaza.libraryapi.model.Usuario;
import plaza.libraryapi.service.UsuarioService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LoginSocialSucessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    private final UsuarioService usuarioService;
    private final PasswordEncoder encoder;
    private static final String SENHA_PADRAO = "123";//apenas para teste

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws ServletException, IOException {
        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;
        OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

        String email  = oAuth2User.getAttribute("email");

        Usuario user = usuarioService.obterPorEmail(email);

        if (user == null) {
            user = criarUsuarioPadrao(email);
        }

        authentication = new CustomAuthentication(user);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        super.onAuthenticationSuccess(request, response, authentication);
    }

    private Usuario criarUsuarioPadrao(String email) {
        Usuario user;
        user = new Usuario();
        user.setEmail(email);
        user.setLogin(usuarioPorEmail(email));
        user.setSenha(SENHA_PADRAO);//service ja criptografa a senha
        user.setRoles(List.of("OPERADOR"));//role mais baixa ate o momento
        usuarioService.salvar(user);

        return user;
    }

    private static String usuarioPorEmail(String email){
        return email.substring(0, email.indexOf("@"));
    }
}
