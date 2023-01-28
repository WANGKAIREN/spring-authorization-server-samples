package com.example.authenticationjwt.jose;

import com.example.authenticationjwt.security.Authentications;
import com.example.authenticationjwt.security.JwtAuthenticationToken;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.JWSVerifier;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimNames;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author WANGKairen
 * @since 2023-01-19 03:39:22
 **/
@Component
public class Jwss implements Serializable {

    private static final long serialVersionUID = 1L;

    @Autowired
    private static RedisTemplate<String, String> redisTemplate;

    /**
     * 用户名称
     */
    private static final String USERNAME = JWTClaimNames.SUBJECT;

    /**
     * 用户ID
     */
    private static final String USER_ID = "userId";

    /**
     * 权限列表
     */
    private static final String AUTHORITIES = "authorities";

    /**
     * 创建时间
     */
    private static final String CREATED = "created";

    /**
     * 密钥
     */
    private static final String SECRET = "CEpTj02uz6nJvNmeVKsVZlYGWefu7oH4";

    /**
     * 有效期12小时  12 * 60 *60 * 1000
     */
    private static final int EXPIRE_TIME = 12 * 60 * 60;

    public static void main(String[] args) {
        // Generate random 256-bit (32-byte) shared secret
        String secret = RandomStringUtils.random(32, true, true);
        System.out.println(secret);
    }

    /**
     * 生成令牌
     *
     * @param authentication
     * @param userId
     * @return 令牌
     */
    public static String generateToken(Authentication authentication, String userId) throws JOSEException {

        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRE_TIME);

        // 负载 自包含
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(Authentications.getUsername(authentication))
                .issuer("https://andaily.com")
                .claim(CREATED, new Date())
                .claim(USER_ID, userId)
                .claim(AUTHORITIES, authentication.getAuthorities())
                .expirationTime(calendar.getTime())
                .build();

        return generateToken(claimsSet);
    }

    /**
     * 根据数据声明生成令牌
     *
     * @param claimsSet 数据声明
     * @return 令牌
     * @throws JOSEException
     */
    private static String generateToken(JWTClaimsSet claimsSet) throws JOSEException {

        // 头部
        JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);

        // 签名
        JWSSigner jwsSigner = new MACSigner(SECRET);

        // JSON
        SignedJWT jwsObject = new SignedJWT(jwsHeader, claimsSet);
        jwsObject.sign(jwsSigner);

        // 令牌
        return jwsObject.serialize();
    }

    /**
     * 根据请求令牌获取登录认证信息
     *
     * @param
     * @return 用户名
     */
    public static Authentication getAuthenticationFromToken(HttpServletRequest request) throws ParseException, JOSEException {

        Authentication authentication = null;

        // 获取请求头中的 token
        String token = getToken(request);
        if (token == null) {
            return null;
        }

        // 请求令牌不能为空

        // 当前用户认证信息
        if (Authentications.getAuthentication() == null) {
            // 上下文中Authentication为空
            JWTClaimsSet jwtClaimsSet = getClaimsFromToken(token);
            if (jwtClaimsSet == null) {
                return null;
            }

            if (!valRedisToken(token, jwtClaimsSet.getStringClaim(USER_ID))) {
                return null;
            }

            String username = jwtClaimsSet.getSubject();
            if (username == null) {
                return null;
            }

            Object authors = jwtClaimsSet.getClaim(AUTHORITIES);
            List<GrantedAuthority> authorities = new ArrayList<>();
            if (authors instanceof List) {
                for (Map<String, String> object : (List<Map<String, String>>) authors) {
                    authorities.add(new SimpleGrantedAuthority(object.get("authority")));
                }
            }

            authentication = new JwtAuthenticationToken(username, null, authorities, token);
        } else {
            if (validateToken(token, Authentications.getUsername())) {
                // 如果上下文中Authentication非空，且请求令牌合法，直接返回当前登录认证信息
                authentication = Authentications.getAuthentication();
            }
        }
        return authentication;
    }

    /**
     * 获取请求头中的 token
     *
     * @param request 请求
     * @return token
     */
    public static String getToken(HttpServletRequest request) {

        String token = request.getHeader("Authorization");
        String tokenHead = "Bearer ";
        if (token == null) {
            token = request.getHeader("token");
        } else if (token.contains(tokenHead)) {
            token = token.substring(tokenHead.length());
        }
        if ("".equals(token)) {
            token = null;
        }
        return token;
    }

    /**
     * 从令牌中获取数据声明并验签
     *
     * @param token 令牌
     * @return 数据声明
     * @throws ParseException
     * @throws JOSEException
     */
    private static JWTClaimsSet getClaimsFromToken(String token) throws ParseException, JOSEException {

        SignedJWT parseJwsObject = SignedJWT.parse(token);

        // 验签对象
        JWSVerifier jwsVerifier = new MACVerifier(token);

        // 验签
        if (!parseJwsObject.verify(jwsVerifier)) {
            return null;
        }

        return parseJwsObject.getJWTClaimsSet();
    }

    /**
     * 判断 Redis 中是否有 token
     *
     * @param token
     * @param userId
     * @return
     */
    public static Boolean valRedisToken(String token, String userId) {

        //查询redis中的token
        if (Boolean.FALSE.equals(redisTemplate.hasKey(userId))) {
            return false;
        }
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String redisToken = valueOperations.get(userId);
        return StringUtils.isNotBlank(redisToken) && token.equals(redisToken);
    }

    /**
     * 验证令牌
     *
     * @param token
     * @param username
     * @return
     */
    public static Boolean validateToken(String token, String username) throws ParseException, JOSEException {

        JWTClaimsSet jwtClaimsSet = getClaimsFromToken(token);
        if (jwtClaimsSet == null) {
            return false;
        }

        if (!valRedisToken(token, jwtClaimsSet.getStringClaim(USER_ID))) {
            return false;
        }

        String subject = jwtClaimsSet.getSubject();

        return StringUtils.isNotBlank(subject) &&
                subject.equals(username);
    }


    /**
     * 刷新令牌
     *
     * @param token
     * @return
     */
    public static String refreshToken(String token) throws ParseException, JOSEException {
        String refreshedToken;

        JWTClaimsSet jwtClaimsSet = getClaimsFromToken(token);
        if (jwtClaimsSet == null) {
            return null;
        }

        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRE_TIME);

        // 负载 自包含
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(jwtClaimsSet.getSubject())
                .issuer(jwtClaimsSet.getIssuer())
                .claim(CREATED, new Date())
                .claim(USER_ID, jwtClaimsSet.getStringClaim(USER_ID))
                .claim(AUTHORITIES, jwtClaimsSet.getStringClaim(AUTHORITIES))
                .expirationTime(calendar.getTime())
                .build();

        return generateToken(claimsSet);
    }
}

