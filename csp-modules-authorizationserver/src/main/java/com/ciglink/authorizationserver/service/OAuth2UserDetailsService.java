package com.ciglink.authorizationserver.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The interface Oauth2 user Details service.
 *
 * @author WANGKairen
 * @see org.springframework.security.core.userdetails.UserDetailsService // 与此接口功能相同，只名称不同
 * @since 2022-12-22 15:59:57
 */
@FunctionalInterface // SAM 接口，即 Single Abstract Method interfaces，为了使用 Lambda表达式，限制只能声明单个接口
public interface OAuth2UserDetailsService {

    /**
     * Load oauth2 user by username.
     *
     * @param username the username
     * @return the user details
     * @throws UsernameNotFoundException the username not found exception
     */
    UserDetails loadOAuth2UserByUsername(String username) throws UsernameNotFoundException;
}
