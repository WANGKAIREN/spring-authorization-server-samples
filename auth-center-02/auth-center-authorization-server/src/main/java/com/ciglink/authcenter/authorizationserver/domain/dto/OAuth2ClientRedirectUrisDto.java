package com.ciglink.authcenter.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authcenter.authorizationserver.domain.po.OAuth2ClientRedirectUrisPo;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:16:53
 **/
@EqualsAndHashCode(callSuper = true)
@Data
@TableName("tmp_oauth2_client_redirect_uris")
public class OAuth2ClientRedirectUrisDto extends OAuth2ClientRedirectUrisPo {

    private static final long serialVersionUID = 1L;
}
