package com.snowkit.luna.config;

import com.snowkit.luna.config.security.*;
import com.snowkit.luna.model.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @RequiredArgsConstructor
    @Configuration
    @Order(1)
    public static class AdminConfig extends WebSecurityConfigurerAdapter {

        private final CustomAccessDeniedHandler customAccessDeniedHandler;

        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        private final ExceptionHandlerFilter exceptionHandlerFilter;

        private final AdminDetailsServiceImpl adminDetailsService;

        @Override
        public void configure(WebSecurity web) {
            web.ignoring().mvcMatchers("/css/**", "/js/**", "/image/**");
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.mvcMatcher("/admin/**")
                    .authorizeRequests()
                    .mvcMatchers("/admin/register/**", "/admin/login/**").permitAll()
                    .mvcMatchers("/admin/**").hasRole(Role.ADMIN.name())
                    .and()
                .addFilterBefore(new JwtAdminFilter(adminDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtAdminFilter.class)
                .exceptionHandling()
                    .accessDeniedHandler(customAccessDeniedHandler)
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                .csrf()
                    .disable()
                .cors()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @RequiredArgsConstructor
    @Configuration
    @Order(2)
    public static class CompanyConfig extends WebSecurityConfigurerAdapter {

        private final CustomAccessDeniedHandler customAccessDeniedHandler;

        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        private final ExceptionHandlerFilter exceptionHandlerFilter;

        private final UserDetailsServiceImpl userDetailsService;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http.mvcMatcher("/**")
                    .authorizeRequests()
                    .mvcMatchers("/register/**", "/login/**").permitAll()
                    .mvcMatchers("/**").hasAnyRole(Role.ADMIN.name(), Role.USER.name())
                    .and()
                .addFilterBefore(new JwtUserFilter(userDetailsService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(exceptionHandlerFilter, JwtUserFilter.class)
                .exceptionHandling()
                    .accessDeniedHandler(customAccessDeniedHandler)
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                    .and()
                .csrf()
                    .disable()
                .cors()
                    .disable()
                .sessionManagement()
                    .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        }
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
