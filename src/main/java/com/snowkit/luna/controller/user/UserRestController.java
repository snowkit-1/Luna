package com.snowkit.luna.controller.user;

import com.snowkit.luna.database.User;
import com.snowkit.luna.dto.RegisterUserDto;
import com.snowkit.luna.dto.UserDto;
import com.snowkit.luna.dto.UserMapper;
import com.snowkit.luna.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RequestMapping
@RestController
public class UserRestController {

    private final UserService userService;

    @PostMapping("/users")
    public ResponseEntity<UserDto> register(@RequestBody @Valid RegisterUserDto registerUserDto) {
        User user = UserMapper.toEntity(registerUserDto);
        UserDto userDto = userService.register(user);

        return ResponseEntity.ok(userDto);
    }
}
