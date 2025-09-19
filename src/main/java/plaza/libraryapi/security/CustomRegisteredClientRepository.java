package plaza.libraryapi.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.stereotype.Component;
import plaza.libraryapi.service.ClientService;

@Component
@RequiredArgsConstructor
public class CustomRegisteredClientRepository implements RegisteredClientRepository {

    private final ClientService service;
    private final TokenSettings tokenSettings;
    private final ClientSettings clientSettings;

    @Override
    public void save(RegisteredClient registeredClient) {

    }

    @Override
    public RegisteredClient findById(String id) {
        return null;
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        var client = service.obterPorClientID(clientId);
        if (client == null) {
            return null;
        }

        return RegisteredClient
                .withId(client.getId().toString())
                .clientId(client.getClientId())
                .clientSecret(client.getClientSecret())
                .redirectUri(client.getRedirectURI())
                .scope(client.getScope())
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) //usuario
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS) //aplicação para aplicação
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) //renovar o token atual
                .tokenSettings(tokenSettings)
                .clientSettings(clientSettings)//configuracao global de configuration do tokenSettings e ClientSettings
                .build();
    }
}
