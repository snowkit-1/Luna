package com.snowkit.luna.database;

import com.snowkit.luna.database.converter.RoleConverter;
import com.snowkit.luna.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Convert;
import javax.persistence.Entity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class User extends CommonEntity {

    private String username;

    private String password;

    private String birth;

    private String phone;

    private String email;

    @Convert(converter = RoleConverter.class)
    private Role role;
}
