package com.ciglink.authcenter.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authcenter.authorizationserver.domain.po.OAuth2ClientSettingsPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.jose.jws.JwsAlgorithm;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.ClientSettings;
import org.springframework.util.StringUtils;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:17:06
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("tmp_oauth2_client_settings")
public class OAuth2ClientSettingsDto extends OAuth2ClientSettingsPo {

    private static final long serialVersionUID = 1L;

    /**
     * From clientSettings to oAuth2ClientSettingsDto.
     *
     * @param clientSettings the clientSettings
     * @return the oAuth2ClientSettingsDto
     */
    public static OAuth2ClientSettingsDto fromClientSettings(ClientSettings clientSettings) {
        OAuth2ClientSettingsDto oAuth2ClientSettingsDto = new OAuth2ClientSettingsDto();
        oAuth2ClientSettingsDto.setRequireProofKey(clientSettings.isRequireProofKey());
        oAuth2ClientSettingsDto.setRequireAuthorizationConsent(clientSettings.isRequireAuthorizationConsent());
        oAuth2ClientSettingsDto.setJwkSetUrl(clientSettings.getJwkSetUrl());
        JwsAlgorithm jwsAlgorithm = clientSettings.getTokenEndpointAuthenticationSigningAlgorithm();
        if (jwsAlgorithm != null) {
            oAuth2ClientSettingsDto.setSigningAlgorithm(jwsAlgorithm.getName());
        }
        return oAuth2ClientSettingsDto;
    }

    /**
     * To client settings.
     *
     * @return the client settings
     */
    public ClientSettings toClientSettings() {
        // @formatter:off
        ClientSettings.Builder builder =
            ClientSettings.builder()
                .requireProofKey(getRequireProofKey())
                .requireAuthorizationConsent(getRequireAuthorizationConsent());
        // @formatter:on

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.from(getSigningAlgorithm());
        JwsAlgorithm jwsAlgorithm = signatureAlgorithm == null ? MacAlgorithm.from(getSigningAlgorithm()) : signatureAlgorithm;
        if (jwsAlgorithm != null) {
            builder.tokenEndpointAuthenticationSigningAlgorithm(jwsAlgorithm);
        }
        if (StringUtils.hasText(getJwkSetUrl())) {
            builder.jwkSetUrl(getJwkSetUrl());
        }
        return builder.build();
    }
}
