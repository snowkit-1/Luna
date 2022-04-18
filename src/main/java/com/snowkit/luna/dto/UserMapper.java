package com.snowkit.luna.dto;

import com.snowkit.luna.database.User;

public class UserMapper {

    public static UserDto toDto(User user) {
        return UserDto.builder()
                .username(user.getUsername())
                .displayName(user.getDisplayName())
                .birth(user.getBirth())
                .phone(user.getPhone())
                .email(user.getEmail())
                .role(user.getRole())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    public static User toEntity(RegisterUserDto registerUserDto) {
        return User.builder()
                .username(registerUserDto.getUsername())
                .password(registerUserDto.getPassword())
                .displayName(registerUserDto.getDisplayName())
                .birth(registerUserDto.getBirth())
                .phone(registerUserDto.getPhone())
                .email(registerUserDto.getEmail())
                .build();
    }
}
