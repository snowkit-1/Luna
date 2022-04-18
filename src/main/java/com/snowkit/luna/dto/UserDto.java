package com.snowkit.luna.dto;

import com.snowkit.luna.model.Role;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Builder
@ToString
public class UserDto {

    private Long id;

    private String username;

    private String displayName;

    private String birth;

    private String phone;

    private String email;

    private Role role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
