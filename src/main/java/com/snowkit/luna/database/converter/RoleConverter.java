package com.snowkit.luna.database.converter;

import com.snowkit.luna.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class RoleConverter implements AttributeConverter<Role, String> {

    @Override
    public String convertToDatabaseColumn(Role role) {
        String dbData = null;
        if (role != null) {
            dbData = role.name();
        }

        return dbData;
    }

    @Override
    public Role convertToEntityAttribute(String dbData) {
        Role role = null;
        if (dbData != null) {
            role = Role.valueOf(dbData);
        }

        return role;
    }
}
