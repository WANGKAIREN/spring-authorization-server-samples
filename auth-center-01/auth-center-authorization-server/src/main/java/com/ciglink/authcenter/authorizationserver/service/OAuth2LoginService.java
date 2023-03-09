package com.ciglink.authcenter.authorizationserver.service;

import com.ciglink.authcenter.authorizationserver.req.OAuth2UserInfoReq;

/**
 * @author WANGKairen
 * @since 2023-01-30 19:07:09
 **/
public interface OAuth2LoginService {

    String login(OAuth2UserInfoReq req);
}
