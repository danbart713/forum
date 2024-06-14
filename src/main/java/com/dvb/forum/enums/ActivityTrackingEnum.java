package com.dvb.forum.enums;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.HashMap;
import java.util.Map;

public enum ActivityTrackingEnum {

    REGISTRATION("Registration"),
    LOGIN("Login");

    private static final Map<String, ActivityTrackingEnum> BY_LABEL = new HashMap<>();

    private final String label;

    static {
        for (ActivityTrackingEnum e : values()) {
            BY_LABEL.put(e.label, e);
        }
    }

    ActivityTrackingEnum(String label) {
        this.label = label;
    }

    public static ActivityTrackingEnum fromLabel(String label) {
        return BY_LABEL.get(label);
    }

    @JsonValue
    public String getLabel() {
        return label;
    }

}
