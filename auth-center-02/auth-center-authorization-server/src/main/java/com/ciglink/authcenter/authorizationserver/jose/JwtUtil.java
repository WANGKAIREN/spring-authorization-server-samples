package com.ciglink.authcenter.authorizationserver.jose;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

/**
 * @author WANGKairen
 * @since 2023-01-30 17:58:11
 **/
@Component
public class JwtUtil {

    /**
     * 用户名称
     */
    public static final String USERNAME = JWTClaimNames.SUBJECT;

    /**
     * 用户ID
     */
    public static final String USER_ID = "userId";

    /**
     * 权限列表
     */
    public static final String AUTHORITIES = "authorities";

    /**
     * 创建时间
     */
    public static final String CREATED = "created";

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
        String secret = RandomUtil.randomString(32);
        System.out.println(secret);
    }

    /**
     * 生成令牌
     *
     * @param authentication
     * @return 令牌
     */
    public String generateToken(Authentication authentication) {
        // 过期时间
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, EXPIRE_TIME);
        // 负载 自包含
        JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
                .subject(getUsername(authentication))
                .issuer("https://test.ciglink.com")
                .claim(CREATED, new Date())
                // TODO 实体类构建后，这里需要修改
                .claim(USER_ID, "123")
                .claim(AUTHORITIES, authentication.getAuthorities())
                .expirationTime(calendar.getTime())
                .build();
        return generateToken(claimsSet);
    }

    /**
     * @param authentication 用户认证信息
     * @return 当前用户名
     */
    private String getUsername(Authentication authentication) {
        String username = null;
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails) {
                username = ((UserDetails) principal).getUsername();
            }
        }
        return username;
    }

    /**
     * 根据数据声明生成令牌
     *
     * @param claimsSet 数据声明
     * @return 令牌
     * @throws JOSEException
     */
    private String generateToken(JWTClaimsSet claimsSet) {
        try {
            // 头部
            JWSHeader jwsHeader = new JWSHeader(JWSAlgorithm.HS256);
            // 签名
            JWSSigner jwsSigner = new MACSigner(SECRET);
            // JSON
            SignedJWT jwsObject = new SignedJWT(jwsHeader, claimsSet);
            jwsObject.sign(jwsSigner);
            // 令牌
            return jwsObject.serialize();
        } catch (JOSEException e) {
            return null;
        }
    }

    /**
     * 检验是否为 jwt 格式的字符串
     * <br>
     * 说明: jwt 字符串由三部分组成, 分别用 . 分隔开, 所以认为有两个 . 的字符串就是 jwt 形式的字符串
     * @param token jwt token串
     * @return boolean
     */
    public boolean isJwtStr(String token){
        return StrUtil.count(token, ".") == 2;
    }

    /**
     * 从令牌中获取数据声明并验签
     *
     * @param token 令牌
     * @return 数据声明
     * @throws ParseException
     * @throws JOSEException
     */
    public JWTClaimsSet getClaimsFromToken(String token) {
        try {
            SignedJWT parseJwsObject = SignedJWT.parse(token);
            // 验签对象
            JWSVerifier jwsVerifier = new MACVerifier(SECRET);

            // 验签
            if (!parseJwsObject.verify(jwsVerifier)) {
                return null;
            }

            return parseJwsObject.getJWTClaimsSet();
        } catch (ParseException | JOSEException e) {
            return null;
        }
    }

}
