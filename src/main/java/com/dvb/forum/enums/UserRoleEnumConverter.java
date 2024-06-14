package com.dvb.forum.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

@Converter(autoApply = true)
public class UserRoleEnumConverter implements AttributeConverter<UserRoleEnum, String> {

    @Override
    public String convertToDatabaseColumn(UserRoleEnum userRoleEnum) {
        if (userRoleEnum == null) {
            return null;
        }

        return userRoleEnum.getLabel();
    }

    @Override
    public UserRoleEnum convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }

        return UserRoleEnum.fromLabel(dbData);
    }

}
