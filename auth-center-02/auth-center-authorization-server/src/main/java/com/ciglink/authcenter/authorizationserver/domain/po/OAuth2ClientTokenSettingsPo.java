package com.ciglink.authcenter.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.Duration;

/**
 * OAuth2客户端Token配置
 *
 * @author WANGKairen
 * @since 2022-12-27 17:16:28
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class OAuth2ClientTokenSettingsPo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private Duration accessTokenTimeToLive;

    private String tokenFormat;

    /**
     * 当数据库中无此 client id，默认为 true
     */
    private Boolean reuseRefreshTokens = true;

    private Duration refreshTokenTimeToLive;

    private String idTokenSignatureAlgorithm;
}
