package com.ciglink.authcenter.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authcenter.authorizationserver.domain.po.OAuth2ClientTokenSettingsPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.core.OAuth2TokenFormat;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.server.authorization.config.TokenSettings;

import java.time.Duration;
import java.util.Optional;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:17:13
 **/
@EqualsAndHashCode(callSuper = false)
@Data
@TableName("tmp_oauth2_client_token_settings")
public class OAuth2ClientTokenSettingsDto extends OAuth2ClientTokenSettingsPo {

    private static final long serialVersionUID = 1L;

    /**
     * From tokenSettings to oauth2TokenSettings.
     *
     * @param tokenSettings the tokenSettings
     * @return the oauth2TokenSettings
     */
    public static OAuth2ClientTokenSettingsDto fromTokenSettings(TokenSettings tokenSettings) {
        OAuth2ClientTokenSettingsDto oAuth2ClientTokenSettingsDto = new OAuth2ClientTokenSettingsDto();
        oAuth2ClientTokenSettingsDto.setAccessTokenTimeToLive(tokenSettings.getAccessTokenTimeToLive());
        oAuth2ClientTokenSettingsDto.setTokenFormat(tokenSettings.getAccessTokenFormat().getValue());
        oAuth2ClientTokenSettingsDto.setReuseRefreshTokens(tokenSettings.isReuseRefreshTokens());
        oAuth2ClientTokenSettingsDto.setRefreshTokenTimeToLive(tokenSettings.getRefreshTokenTimeToLive());
        oAuth2ClientTokenSettingsDto.setIdTokenSignatureAlgorithm(tokenSettings.getIdTokenSignatureAlgorithm().getName());
        return oAuth2ClientTokenSettingsDto;
    }

    /**
     * To token settings.
     *
     * @return the token settings
     */
    public TokenSettings toTokenSettings() {
        // @formatter:off
        return TokenSettings.builder()
            .accessTokenTimeToLive(Optional.ofNullable(getAccessTokenTimeToLive()).orElse(Duration.ofMinutes(5)))
            .accessTokenFormat(Optional.ofNullable(getTokenFormat()).map(OAuth2TokenFormat::new).orElse(OAuth2TokenFormat.SELF_CONTAINED))
            .reuseRefreshTokens(getReuseRefreshTokens())
            .refreshTokenTimeToLive(Optional.ofNullable(getRefreshTokenTimeToLive()).orElse(Duration.ofMinutes(60)))
            .idTokenSignatureAlgorithm(Optional.ofNullable(getIdTokenSignatureAlgorithm()).map(SignatureAlgorithm::from).orElse(SignatureAlgorithm.RS256))
            .build();
        // @formatter:off
    }
}
