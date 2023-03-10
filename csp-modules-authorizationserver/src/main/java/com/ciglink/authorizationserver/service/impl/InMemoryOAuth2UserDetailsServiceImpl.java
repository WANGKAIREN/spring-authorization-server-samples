package com.ciglink.authorizationserver.service.impl;

import com.ciglink.authorizationserver.service.OAuth2UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;

import java.util.HashMap;
import java.util.Map;

/**
 * @author WANGKairen
 * @since 2022-12-23 10:51:08
 **/
public class InMemoryOAuth2UserDetailsServiceImpl implements OAuth2UserDetailsService {

    private final Map<String, UserDetails> users = new HashMap<>();

    public InMemoryOAuth2UserDetailsServiceImpl() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("user")
                .passwordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder()::encode)
                .roles("user","test")
                .build();
        this.users.put(userDetails.getUsername(), userDetails);
    }

    @Override
    public UserDetails loadOAuth2UserByUsername(String username) throws UsernameNotFoundException {
        UserDetails user = this.users.get(username.toLowerCase());
        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getUsername(), user.getPassword(), user.isEnabled(), user.isAccountNonExpired(), user.isCredentialsNonExpired(), user.isAccountNonLocked(), user.getAuthorities());
    }
}
