package com.ciglink.authcenter.authorizationserver.security.oauth2;

import cn.hutool.core.util.StrUtil;
import com.ciglink.authcenter.authorizationserver.jose.JwtUtil;
import com.nimbusds.jwt.JWTClaimsSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * {@link OncePerRequestFilter} Spring 提供只执行一次的 Filter，兼容不同的版本的 Servlet
 * {@link BasicAuthenticationFilter} 该过滤器会从 HTTP BASIC authorization 头部解析出相应的用户名和密码然后调用 AuthenticationManager 进行认证
 *
 * @author WANGKairen
 * @since 2023-01-30 17:08:48
 **/

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取 token
        String token = getToken(request);
        // 解析 token
        if (StrUtil.isNotBlank(token)) {
            analysisToken(token);
        }
        // 放行
        filterChain.doFilter(request, response);
    }

    /**
     * 解析 token
     *
     * @param token
     */
    private void analysisToken(String token) throws RemoteException {

        Authentication authentication1 = getAuthentication();
        System.out.println(authentication1);

        // 校验字符格式
        if (!jwtUtil.isJwtStr(token)) {
            throw new RemoteException("token不合法");
        }

        // 取 Jwt 解析对象
        JWTClaimsSet jwtClaimsSet = jwtUtil.getClaimsFromToken(token);
        if (jwtClaimsSet == null) {
            throw new RemoteException("token已经失效");
        }

        // TODO 待实现 获取 Redis 用户

        // TODO 临时 username 代替 UserInfo 对象
        String username = jwtClaimsSet.getSubject();
        if (StrUtil.isBlank(username)) {
            throw new RemoteException("username为空");
        }

        // 权限列表
        Object authors = jwtClaimsSet.getClaim(JwtUtil.AUTHORITIES);
        List<GrantedAuthority> authorities = new ArrayList<>();
        if (authors instanceof List) {
            for (Map<String, String> map : (List<Map<String, String>>) authors) {
                String authority = map.get("authority");
                if (StrUtil.isBlank(authority)) {
                    continue;
                }
                authorities.add(new SimpleGrantedAuthority(authority));
            }
        }

        // 告诉 SpringSecurity 该用户已经认证成功
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        // 存入 SecurityContext 中，因为 SpringSecurity 后面过滤器需要内部验证
        SecurityContextHolder.getContext().setAuthentication(authentication);

        authentication1 = getAuthentication();
        System.out.println(authentication1);
    }

    /**
     * @return 当前用户认证信息
     */
    public Authentication getAuthentication() {
        if (SecurityContextHolder.getContext() == null) {
            return null;
        }
        return SecurityContextHolder.getContext().getAuthentication();
    }

    /**
     * 获取 token
     * 目前暂定使用 token 作为 head
     * 因为资源服务器 JwtAuthenticationProvider 也会读取 HTTP BASIC authorization
     *
     * @param request
     * @return
     */
    private String getToken(HttpServletRequest request) {
        //String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = null;
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
}
