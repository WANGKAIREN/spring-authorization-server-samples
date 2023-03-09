package com.ciglink.authcenter.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * OAuth2客户端认证方式
 *
 * @author WANGKairen
 * @since 2022-12-27 17:14:55
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class OAuth2ClientMethodsPo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private String clientAuthenticationMethod;
}
