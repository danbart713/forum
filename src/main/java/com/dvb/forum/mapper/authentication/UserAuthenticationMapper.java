package com.dvb.forum.mapper.authentication;

import com.dvb.forum.dto.authentication.UserRegistrationRequest;
import com.dvb.forum.entity.EmailEntity;
import com.dvb.forum.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Slf4j
public abstract class UserAuthenticationMapper {

    private final PasswordEncoder passwordEncoder;

    protected UserAuthenticationMapper(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    public void mapToUserEntity(UserRegistrationRequest userRegistrationRequest, String ipAddress, EmailEntity emailEntity, UserEntity userEntity) {
        log.info("UserAuthenticationMapper - mapToUserEntity - userRegistrationRequest: {}, ipAddress: {}, emailEntity: {}, userEntity: {}",
                userRegistrationRequest, ipAddress, emailEntity, userEntity);

        userEntity.setUserUuidId(UUID.randomUUID());
        userEntity.setDisplayName(userRegistrationRequest.getDisplayName());
        userEntity.setPassword(passwordEncoder.encode(userRegistrationRequest.getPassword()));
        userEntity.setUserRole(userRegistrationRequest.getUserRole());
        userEntity.setRegistrationIpAddress(ipAddress);
        userEntity.setLastLoginDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        userEntity.setEmailEntity(emailEntity);
        userEntity.setUserCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        userEntity.setUserUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("UserAuthenticationMapper - mapToUserEntity - userEntity: {}", userEntity);
    }

}