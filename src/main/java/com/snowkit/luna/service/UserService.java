package com.snowkit.luna.service;

import com.snowkit.luna.database.User;
import com.snowkit.luna.database.UserRepository;
import com.snowkit.luna.dto.UserDto;
import com.snowkit.luna.dto.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserDto register(User user) {
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);

        userRepository.save(user);

        return UserMapper.toDto(user);
    }
}
