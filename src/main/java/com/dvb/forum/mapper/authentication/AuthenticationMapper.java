package com.dvb.forum.mapper.authentication;

import com.dvb.forum.dto.authentication.UserLoginResponse;
import com.dvb.forum.dto.authentication.UserRegistrationRequest;
import com.dvb.forum.dto.authentication.UserRegistrationResponse;
import com.dvb.forum.entity.EmailEntity;
import com.dvb.forum.entity.UserEntity;
import com.dvb.forum.entity.UserLoginRecordEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
@Slf4j
public class AuthenticationMapper {

    public EmailEntity mapToEmailEntity(UserRegistrationRequest userRegistrationRequest) {
        log.info("AuthenticationMapper - mapToEmailEntity - userRegistrationRequest: {}", userRegistrationRequest);

        EmailEntity emailEntity = new EmailEntity();
        emailEntity.setUuidId(UUID.randomUUID());
        emailEntity.setEmailAddress(userRegistrationRequest.getEmail());
        emailEntity.setCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        emailEntity.setUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("AuthenticationMapper - mapToEmailEntity - emailEntity: {}", emailEntity);

        return emailEntity;
    }

    public UserRegistrationResponse mapToUserRegistrationResponse(String token) {
        log.info("AuthenticationMapper - mapToUserRegistrationResponse - token: {}", token);

        UserRegistrationResponse userRegistrationResponse = new UserRegistrationResponse();
        userRegistrationResponse.setToken(token);
        log.info("AuthenticationMapper - mapToUserRegistrationResponse - userRegistrationResponse: {}", userRegistrationResponse);

        return userRegistrationResponse;
    }

    public UserLoginRecordEntity mapToUserLoginRecordEntity(String ipAddress, UserEntity userEntity) {
        log.info("AuthenticationMapper - mapToUserLoginRecordEntity - ipAddress: {}, userEntity: {}", ipAddress, userEntity);

        UserLoginRecordEntity userLoginRecordEntity = new UserLoginRecordEntity();
        userLoginRecordEntity.setUuidId(UUID.randomUUID());
        userLoginRecordEntity.setIpAddress(ipAddress);
        userLoginRecordEntity.setLoginDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        userLoginRecordEntity.setUserEntity(userEntity);
        userLoginRecordEntity.setCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        userLoginRecordEntity.setUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("AuthenticationMapper - mapToUserLoginRecordEntity - userLoginRecordEntity: {}", userLoginRecordEntity);

        return userLoginRecordEntity;
    }

    public UserLoginResponse mapToUserLoginResponse(String token) {
        log.info("AuthenticationMapper - mapToUserLoginResponse - token: {}", token);

        UserLoginResponse userLoginResponse = new UserLoginResponse();
        userLoginResponse.setToken(token);
        log.info("AuthenticationMapper - mapToUserLoginResponse - userLoginResponse: {}", userLoginResponse);

        return userLoginResponse;
    }

}