package com.ciglink.authcenter.authorizationserver.config;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.ConfigurationSettingNames;
import org.springframework.security.oauth2.server.authorization.oidc.web.OidcProviderConfigurationEndpointFilter;

import java.security.KeyStore;

/**
 * @author WANGKairen
 * @since 2023-02-01 14:59:51
 **/
@Configuration(proxyBeanMethods = false)
public class JwtConfig {

    /**
     * keytool
     * 	-genkey
     * 	-alias felordcn
     * 	-keypass felordcn
     * 	-keyalg RSA
     * 	-storetype PKCS12
     * 	-keysize 2048
     * 	-validity 365
     * 	-keystore d:/keystores/felordcn.jks
     * 	-storepass 123456
     * 	-dname "CN=(Felord), OU=(felordcn), O=(felordcn), L=(zz), ST=(hn), C=(cn)"
     * <br>
     * keytool -genkey -alias felordcn -keypass felordcn -keyalg RSA -storetype PKCS12 -keysize 2048 -validity 365 -keystore d:/keystores/felordcn.jks -storepass 123456 -dname "CN=(Felord), OU=(felordcn), O=(felordcn), L=(zz), ST=(hn), C=(cn)"
     *
     * @return
     */
    @Bean
    @SneakyThrows
    public JWKSource<SecurityContext> jwkSource() {

        // jks 文件在 resources 下的 classpath
        String path = "felordcn.jks";
        // keytool 生成的 -alias 值 felordcn
        String alias = "felordcn";
        // keytool 生成的 -storepass 值 123456
        String pass = "123456";

        ClassPathResource resource = new ClassPathResource(path);
        KeyStore jks = KeyStore.getInstance("jks"); // jws 密钥的类型，返回指定类型的密钥库对象
        char[] pin = pass.toCharArray();
        jks.load(resource.getInputStream(), pin);
        RSAKey rsaKey = RSAKey.load(jks, alias, pin);

        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }

    /**
     * OpenID Connect 1.0 UserInfo endpoint
     * OpenID Connect 1.0 Client Registration endpoint
     * 这两个端点必须要使用 JwtDecoder JWT 解码器
     * <br>
     * {@link OidcProviderConfigurationEndpointFilter}
     * {@link ConfigurationSettingNames}
     *
     * @param jwkSource
     * @return
     */
    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }
}
