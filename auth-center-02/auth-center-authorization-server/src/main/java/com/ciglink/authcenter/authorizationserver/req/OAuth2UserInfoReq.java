package com.ciglink.authcenter.authorizationserver.req;

import lombok.Data;

/**
 * @author WANGKairen
 * @since 2023-01-30 19:16:09
 **/
@Data
public class OAuth2UserInfoReq {

    private String username;

    private String password;
}
