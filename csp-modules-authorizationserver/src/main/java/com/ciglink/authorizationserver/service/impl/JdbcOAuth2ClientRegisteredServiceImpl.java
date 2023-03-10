package com.ciglink.authorizationserver.service.impl;

import com.ciglink.authorizationserver.domain.dto.OAuth2ClientDto;
import com.ciglink.authorizationserver.service.OAuth2ClientRegisteredService;
import com.ciglink.authorizationserver.service.OAuth2ClientService;
import lombok.AllArgsConstructor;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.Optional;

/**
 * @author WANGKairen
 * @since 2022-12-28 10:17:54
 **/
@AllArgsConstructor
public class JdbcOAuth2ClientRegisteredServiceImpl implements OAuth2ClientRegisteredService {

    private OAuth2ClientService oAuth2ClientService;

    @Override
    public void save(RegisteredClient registeredClient) {
        Assert.notNull(registeredClient, "registeredClient cannot be null");
        OAuth2ClientDto oAuth2ClientDto = OAuth2ClientDto.fromRegisteredClient(registeredClient);
        oAuth2ClientService.insertJoin(oAuth2ClientDto);
    }

    @Override
    public RegisteredClient findById(String id) {
        Assert.hasText(id, "id cannot be empty");
        return Optional.ofNullable(oAuth2ClientService.findJoinById(id))
                .map(OAuth2ClientDto::toRegisteredClient)
                .orElse(null);
    }

    @Override
    public RegisteredClient findByClientId(String clientId) {
        Assert.hasText(clientId, "clientId cannot be empty");
        return Optional.ofNullable(oAuth2ClientService.findJoinByClientId(clientId))
                .map(OAuth2ClientDto::toRegisteredClient)
                .orElse(null);
    }
}
