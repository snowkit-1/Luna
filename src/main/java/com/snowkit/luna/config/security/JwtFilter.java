package com.snowkit.luna.config.security;

import com.auth0.jwt.JWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public abstract class JwtFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String jwt = request.getHeader("X-Auth-Token");
        if (jwt != null) {
            Authentication authentication = this.getAuthentication(jwt);

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }

    protected abstract Authentication getAuthentication(String jwt);

    protected String getUserName(String jwt) {
        if (jwt == null || jwt.isEmpty()) {
            throw new IllegalArgumentException("인증 토큰이 없습니다.");
        }

        return JWT.decode(jwt).getClaim("username").asString();
    }
}
