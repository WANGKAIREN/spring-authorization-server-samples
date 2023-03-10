package com.ciglink.authorizationserver.domain.dto;

import com.baomidou.mybatisplus.annotation.TableName;
import com.ciglink.authorizationserver.domain.po.OAuth2ClientMethodsPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

/**
 * @author WANGKairen
 * @since 2022-12-27 17:16:36
 **/
@Data
@EqualsAndHashCode(callSuper=true)
@TableName("tmp_oauth2_client_methods")
public class OAuth2ClientMethodsDto extends OAuth2ClientMethodsPo {

    private static final long serialVersionUID = 1L;

    /**
     * To client authentication method.
     *
     * @return the client authentication method
     */
    public ClientAuthenticationMethod toClientAuthenticationMethod() {
        return new ClientAuthenticationMethod(getClientAuthenticationMethod());
    }
}
