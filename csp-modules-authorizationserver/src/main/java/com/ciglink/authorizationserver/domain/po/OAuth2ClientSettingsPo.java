package com.ciglink.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:16:21
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("OAuth2客户端配置")
public class OAuth2ClientSettingsPo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private Boolean requireProofKey = false;

    private Boolean requireAuthorizationConsent = false;

    private String jwkSetUrl;

    private String signingAlgorithm;
}
