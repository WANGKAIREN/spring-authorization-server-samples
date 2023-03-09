package com.ciglink.authcenter.authorizationserver.service.impl;

import com.ciglink.authcenter.authorizationserver.jose.JwtUtil;
import com.ciglink.authcenter.authorizationserver.service.OAuth2LoginService;
import com.ciglink.authcenter.authorizationserver.req.OAuth2UserInfoReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * @author WANGKairen
 * @since 2023-01-30 19:12:31
 **/
@Service
public class OAuth2LoginServiceImpl implements OAuth2LoginService {


    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    AuthenticationManager authenticationManager; // Security 提供认证的接口

    @Override
    public String login(OAuth2UserInfoReq req) {

        // 使用 AuthenticationManager 进行认证
        Authentication authentication = new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword());
        Authentication authenticationResult= authenticationManager.authenticate(authentication);
        if (authenticationResult == null) {
            return null;
        }

        // 获取 token
        String token = jwtUtil.generateToken(authenticationResult);

        // TODO 待添加到 Redis 中

        return token;
    }
}
