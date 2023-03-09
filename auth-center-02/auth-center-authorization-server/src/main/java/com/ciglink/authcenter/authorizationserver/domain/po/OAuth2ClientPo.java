package com.ciglink.authcenter.authorizationserver.domain.po;

import com.ciglink.common.core.web.entity.base.CommonBaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * OAuth2客户端
 *
 * @author WANGKairen
 * @since 2022-12-27 14:51:58
 **/
@EqualsAndHashCode(callSuper = false)
@Data
public class OAuth2ClientPo extends CommonBaseEntity {

    private static final long serialVersionUID = 1L;

    private String clientId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime clientIdIssuedAt;

    private String clientSecret;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime clientSecretExpiresAt;

    private String clientName;
}
