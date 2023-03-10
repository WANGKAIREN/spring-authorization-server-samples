package com.ciglink.authorizationserver.config;

import com.ciglink.authorizationserver.service.OAuth2UserDetailsService;
import com.ciglink.authorizationserver.service.RootUserDetailsService;
import com.ciglink.authorizationserver.service.RootUserInfoService;
import com.ciglink.authorizationserver.service.impl.InMemoryOAuth2UserDetailsServiceImpl;
import com.ciglink.authorizationserver.service.impl.JdbcRootUserDetailsServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * 用户详情配置
 *
 * @author WANGKairen
 * @since 2022-12-22 14:50:06
 **/
//@Configuration(proxyBeanMethods = false) // Lite 模式
public class UserDetailsConfig {

    /**
     * 全局缺省设置，多个 UserDetailsService 下需要一个全局兜底的不可用实现
     *
     * @return the user details service
     */
    //@Bean
    public UserDetailsService notFoundUserDetailsService() {
        // Lambda 匿名内部类 {@link UserDetailsService#loadUserByUsername(String)} 的接口实现
        return username -> {
            throw new UsernameNotFoundException("用户未找到");
        };
    }

    /**
     * 缓存，授权用户
     *
     * @return the oauth2 user details service
     */
    //@Bean
    //@ConditionalOnMissingBean // 只允许有一个 OAuth2UserDetailsService 实例
    OAuth2UserDetailsService oAuth2UserDetailsService() {
        return new InMemoryOAuth2UserDetailsServiceImpl();
    }

    /**
     * 临时缓存，授权用户
     *
     * @return UserDetailsService
     */
    // @formatter:off
    @Bean
    UserDetailsService users() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user1")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }
    // @formatter:on

    /**
     * 数据库，Root 用户
     *
     * @return the root user details service
     */
    @Bean
    @ConditionalOnMissingBean
    RootUserDetailsService rootUserDetailsService(RootUserInfoService rootUserInfoService) {
        return new JdbcRootUserDetailsServiceImpl(rootUserInfoService);
    }
}
