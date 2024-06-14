package com.dvb.forum.dto.activitytracking;

import com.dvb.forum.enums.ActivityTrackingEnum;
import com.dvb.forum.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActivityTrackingKafkaEvent {

    private Long userId;
    private UUID userUuidId;
    private String displayName;
    private UserRoleEnum userRole;
    private String ipAddress;
    private ActivityTrackingEnum activityTracking;
    private String url;

}
