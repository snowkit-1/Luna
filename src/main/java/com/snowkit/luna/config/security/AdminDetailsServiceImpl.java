package com.snowkit.luna.config.security;

import com.snowkit.luna.database.Admin;
import com.snowkit.luna.database.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AdminDetailsServiceImpl implements UserDetailsService {

    private final AdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin = adminRepository.findByUsername(username);

        UserDetails userDetails = User.builder()
                .username(admin.getUsername())
                .password("")
                .roles(admin.getRole().name())
                .build();

        return userDetails;
    }
}
