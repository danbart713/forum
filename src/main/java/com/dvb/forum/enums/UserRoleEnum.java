package com.dvb.forum.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum UserRoleEnum {

    INDIVIDUAL("Individual", "Individual User Role"),
    BUSINESS("Business", "Business User Role"),
    ADMIN("Admin", "Admin User Role");

    private static final Map<String, UserRoleEnum> BY_LABEL = new HashMap<>();
    private static final Map<String, UserRoleEnum> BY_DESCRIPTION = new HashMap<>();

    private final String label;
    private final String description;

    static {
        for (UserRoleEnum e : values()) {
            BY_LABEL.put(e.label, e);
            BY_DESCRIPTION.put(e.description, e);
        }
    }

    UserRoleEnum(String label,
                 String description) {
        this.label = label;
        this.description = description;
    }

    public static UserRoleEnum fromLabel(String label) {
        return BY_LABEL.get(label);
    }

    public static UserRoleEnum fromDescription(String description) {
        return BY_DESCRIPTION.get(description);
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

    public String getDescription() {
        return description;
    }

}