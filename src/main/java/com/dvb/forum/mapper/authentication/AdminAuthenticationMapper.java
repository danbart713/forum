package com.dvb.forum.mapper.authentication;

import com.dvb.forum.dto.authentication.AdminRegistrationRequest;
import com.dvb.forum.entity.AdminEntity;
import com.dvb.forum.entity.EmailEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
@Slf4j
public class AdminAuthenticationMapper extends UserAuthenticationMapper {

    @Autowired
    public AdminAuthenticationMapper(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    public AdminEntity mapToAdminEntity(AdminRegistrationRequest adminRegistrationRequest, String ipAddress, EmailEntity emailEntity) {
        log.info("AdminAuthenticationMapper - mapToAdminEntity - adminRegistrationRequest: {}, ipAddress: {}, emailEntity: {}",
                adminRegistrationRequest, ipAddress, emailEntity);

        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setAdminUuidId(UUID.randomUUID());
        adminEntity.setFirstName(adminRegistrationRequest.getFirstName());
        adminEntity.setLastName(adminRegistrationRequest.getLastName());
        adminEntity.setAdminCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        adminEntity.setAdminUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        super.mapToUserEntity(adminRegistrationRequest, ipAddress, emailEntity, adminEntity);
        log.info("AdminAuthenticationMapper - mapToAdminEntity - adminEntity: {}", adminEntity);

        return adminEntity;
    }

}