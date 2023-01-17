package com.example.defaultauthorizationserver.web;

import com.example.defaultauthorizationserver.entity.Client;
import com.example.defaultauthorizationserver.jpa.JpaRegisteredClientRepository;
import com.example.defaultauthorizationserver.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

/**
 * @author WANGKairen
 * @since 2022-12-18 14:38:06
 **/
@RestController
public class TestController {

    @Autowired
    ClientRepository clientRepository;

    @GetMapping(value = "/test")
    public void test() {
        JpaRegisteredClientRepository registeredClientRepository = new JpaRegisteredClientRepository(clientRepository);

        RegisteredClient byClientId1 = registeredClientRepository.findByClientId("messaging-client");
        System.out.println(byClientId1);

        Optional<Client> byClientId = clientRepository.findByClientId("messaging-client");
        System.out.println(byClientId);

        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("messaging-client")
                .clientSecret("{noop}secret")
                .clientAuthenticationMethod(ClientAuthenticationMethod.CLIENT_SECRET_BASIC)
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE)
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .redirectUri("http://127.0.0.1:8080/login/oauth2/code/messaging-client-oidc")
                .redirectUri("http://127.0.0.1:8080/authorized")
                .scope(OidcScopes.OPENID)
                .scope(OidcScopes.PROFILE)
                .scope("message.read")
                .scope("message.write")
                .clientIdIssuedAt(Instant.now())
                .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
                .build();
        registeredClientRepository.save(registeredClient);
    }

}
