package com.dvb.forum.enums;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.StringUtils;

@Converter(autoApply = true)
public class ActivityTrackingEnumConverter implements AttributeConverter<ActivityTrackingEnum, String> {

    @Override
    public String convertToDatabaseColumn(ActivityTrackingEnum activityTrackingEnum) {
        if (activityTrackingEnum == null) {
            return null;
        }

        return activityTrackingEnum.getLabel();
    }

    @Override
    public ActivityTrackingEnum convertToEntityAttribute(String dbData) {
        if (StringUtils.isBlank(dbData)) {
            return null;
        }

        return ActivityTrackingEnum.fromLabel(dbData);
    }

}
