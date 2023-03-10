package com.ciglink.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:16:13
 **/
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel("OAuth2客户端授权范围")
public class OAuth2ClientScopesPo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private String clientId;

    private String scope;

    private String description;
}
