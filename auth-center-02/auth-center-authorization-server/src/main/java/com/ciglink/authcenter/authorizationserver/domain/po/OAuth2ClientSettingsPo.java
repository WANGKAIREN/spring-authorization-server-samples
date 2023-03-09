package com.ciglink.authcenter.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OAuth2客户端配置
 *
 * @author WANGKairen
 * @since 2022-12-27 17:16:21
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class OAuth2ClientSettingsPo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private Boolean requireProofKey = false;

    private Boolean requireAuthorizationConsent = false;

    private String jwkSetUrl;

    private String signingAlgorithm;
}
