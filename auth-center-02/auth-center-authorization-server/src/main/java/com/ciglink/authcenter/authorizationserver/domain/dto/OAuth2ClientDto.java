package com.ciglink.authcenter.authorizationserver.domain.dto;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authcenter.authorizationserver.domain.po.OAuth2ClientPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.core.oidc.OidcScopes;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author WANGKairen
 * @since 2022-12-27 15:29:41
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("tmp_oauth2_client")
public class OAuth2ClientDto extends OAuth2ClientPo {

    private static final long serialVersionUID = 1L;

    @TableField(exist = false)
    private Set<OAuth2ClientMethodsDto> clientAuthenticationMethods;

    @TableField(exist = false)
    private Set<OAuth2ClientGrantTypesDto> authorizationGrantTypes;

    @TableField(exist = false)
    private Set<OAuth2ClientRedirectUrisDto> redirectUris;

    @TableField(exist = false)
    private Set<OAuth2ClientScopesDto> scopes;

    @TableField(exist = false)
    private OAuth2ClientSettingsDto clientSettings;

    @TableField(exist = false)
    private OAuth2ClientTokenSettingsDto tokenSettings;

    /**
     * From registeredClient to oAuth2ClientDto.
     *
     * @param registeredClient the registeredClient
     * @return the oauth2 client dto
     */
    public static OAuth2ClientDto fromRegisteredClient(RegisteredClient registeredClient) {
        OAuth2ClientDto oAuth2ClientDto = new OAuth2ClientDto();
        oAuth2ClientDto.setClientId(registeredClient.getClientId());
        oAuth2ClientDto.setClientIdIssuedAt(DateUtil.toLocalDateTime(registeredClient.getClientIdIssuedAt()));
        oAuth2ClientDto.setClientSecret(registeredClient.getClientSecret());
        // ignore clientSecret clientSecretExpiresAt
        // oAuth2ClientDto.setClientSecretExpiresAt(DateUtil.toLocalDateTime(registeredClient.getClientSecretExpiresAt()));
        oAuth2ClientDto.setClientName(registeredClient.getClientName());

        // @formatter:off
        oAuth2ClientDto.setClientAuthenticationMethods(
            registeredClient
                .getClientAuthenticationMethods()
                .stream()
                .map(method -> {
                    OAuth2ClientMethodsDto oAuth2ClientMethodsDto = new OAuth2ClientMethodsDto();
                    oAuth2ClientMethodsDto.setClientId(registeredClient.getClientId());
                    oAuth2ClientMethodsDto.setClientAuthenticationMethod(method.getValue());
                    return oAuth2ClientMethodsDto;
                })
                .collect(Collectors.toSet())
        );

        oAuth2ClientDto.setAuthorizationGrantTypes(
            registeredClient
                .getAuthorizationGrantTypes()
                .stream()
                .map(grantType -> {
                    OAuth2ClientGrantTypesDto oAuth2ClientGrantTypesDto = new OAuth2ClientGrantTypesDto();
                    oAuth2ClientGrantTypesDto.setClientId(registeredClient.getClientId());
                    oAuth2ClientGrantTypesDto.setGrantTypeName(grantType.getValue());
                    return oAuth2ClientGrantTypesDto;
                })
                .collect(Collectors.toSet())
        );

        oAuth2ClientDto.setRedirectUris(
            registeredClient
                .getRedirectUris()
                .stream()
                .map(redirectUri -> {
                    OAuth2ClientRedirectUrisDto oAuth2ClientRedirectUrisDto = new OAuth2ClientRedirectUrisDto();
                    oAuth2ClientRedirectUrisDto.setClientId(registeredClient.getClientId());
                    oAuth2ClientRedirectUrisDto.setRedirectUri(redirectUri);
                    return oAuth2ClientRedirectUrisDto;
                })
                .collect(Collectors.toSet())
        );

        oAuth2ClientDto.setScopes(
            registeredClient
                .getScopes()
                .stream()
                .map(scope -> {
                    OAuth2ClientScopesDto oAuth2ClientScopesDto = new OAuth2ClientScopesDto();
                    oAuth2ClientScopesDto.setClientId(registeredClient.getClientId());
                    oAuth2ClientScopesDto.setScope(scope);
                    return oAuth2ClientScopesDto;
                })
                .collect(Collectors.toSet())
        );
        // @formatter:on

        OAuth2ClientSettingsDto oAuth2ClientSettingsDto = OAuth2ClientSettingsDto.fromClientSettings(registeredClient.getClientSettings());
        oAuth2ClientSettingsDto.setClientId(registeredClient.getClientId());
        oAuth2ClientDto.setClientSettings(oAuth2ClientSettingsDto);

        OAuth2ClientTokenSettingsDto oAuth2ClientTokenSettingsDto = OAuth2ClientTokenSettingsDto.fromTokenSettings(registeredClient.getTokenSettings());
        oAuth2ClientTokenSettingsDto.setClientId(registeredClient.getClientId());
        oAuth2ClientDto.setTokenSettings(oAuth2ClientTokenSettingsDto);

        return oAuth2ClientDto;
    }

    /**
     * To registered client.
     *
     * @return the registered client
     */
    public RegisteredClient toRegisteredClient() {
        Assert.notNull(getId(), "id cannot be null");
        Assert.notNull(getClientId(), "clientId cannot be null");

        Set<OAuth2ClientMethodsDto> oAuth2ClientMethodsDtoSet = clientAuthenticationMethods == null ? Collections.emptySet() : clientAuthenticationMethods;
        Set<OAuth2ClientGrantTypesDto> oAuth2ClientGrantTypesDtoSet = authorizationGrantTypes == null ? Collections.emptySet() : authorizationGrantTypes;
        Set<OAuth2ClientRedirectUrisDto> oAuth2ClientRedirectUrisDtoSet = redirectUris == null ? Collections.emptySet() : redirectUris;
        Set<OAuth2ClientScopesDto> oAuth2ClientScopesDtoSet = scopes == null ? Collections.emptySet() : scopes;

        // @formatter:off
        RegisteredClient.Builder builder =
            RegisteredClient
                .withId(getId().toString())
                .clientId(getClientId())
                .clientSecret(getClientSecret())
                .clientIdIssuedAt(DateUtil.toInstant(getClientIdIssuedAt()))
                .clientSecretExpiresAt(DateUtil.toInstant(getClientSecretExpiresAt()))
                .clientName(getClientName())
                .clientAuthenticationMethods(clientAuthenticationMethodsConsumer ->
                    clientAuthenticationMethodsConsumer
                        .addAll(
                            oAuth2ClientMethodsDtoSet
                                .stream()
                                .map(OAuth2ClientMethodsDto::toClientAuthenticationMethod)
                                .collect(Collectors.toSet())
                        )
                )
                .authorizationGrantTypes(authorizationGrantTypesConsumer ->
                    authorizationGrantTypesConsumer
                        .addAll(
                            oAuth2ClientGrantTypesDtoSet
                                .stream()
                                .map(OAuth2ClientGrantTypesDto::toAuthorizationGrantType)
                                .collect(Collectors.toSet())
                        )
                )
                .redirectUris(redirectUrisConsumer ->
                    redirectUrisConsumer
                        .addAll(
                            oAuth2ClientRedirectUrisDtoSet
                                .stream()
                                .map(OAuth2ClientRedirectUrisDto::getRedirectUri)
                                .collect(Collectors.toSet())
                        )
                )
                .scopes(scopesConsumer ->
                    scopesConsumer
                        .addAll(
                            oAuth2ClientScopesDtoSet
                                .stream()
                                .map(OAuth2ClientScopesDto::getScope)
                                .collect(Collectors.toSet())
                        )
                )
                .scope(OidcScopes.OPENID)
                .clientSettings(this.clientSettings.toClientSettings())
                .tokenSettings(this.tokenSettings.toTokenSettings());
        // @formatter:on

        return builder.build();
    }
}
