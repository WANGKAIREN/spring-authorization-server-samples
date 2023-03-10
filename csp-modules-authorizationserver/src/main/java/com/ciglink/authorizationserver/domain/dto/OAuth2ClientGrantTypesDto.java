package com.ciglink.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authorizationserver.domain.po.OAuth2ClientGrantTypesPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.core.AuthorizationGrantType;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:16:45
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("tmp_oauth2_client_grant_types")
public class OAuth2ClientGrantTypesDto extends OAuth2ClientGrantTypesPo {

    private static final long serialVersionUID = 1L;

    /**
     * To authorization grant type.
     *
     * @return the authorization grant type
     */
    public AuthorizationGrantType toAuthorizationGrantType() {
        return new AuthorizationGrantType(getGrantTypeName());
    }
}
