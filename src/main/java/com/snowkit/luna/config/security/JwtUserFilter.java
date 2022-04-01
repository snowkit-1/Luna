package com.snowkit.luna.config.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;

@RequiredArgsConstructor
public class JwtUserFilter extends JwtFilter {

    private final UserDetailsServiceImpl userDetailsService;

    @Override
    public Authentication getAuthentication(String jwt) {
        String username = getUserName(jwt);
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }
}
