package com.dvb.forum.mapper.authentication;

import com.dvb.forum.dto.authentication.BusinessRegistrationRequest;
import com.dvb.forum.entity.BusinessEntity;
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
public class BusinessAuthenticationMapper extends UserAuthenticationMapper {

    @Autowired
    public BusinessAuthenticationMapper(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    public BusinessEntity mapToBusinessEntity(BusinessRegistrationRequest businessRegistrationRequest, String ipAddress, EmailEntity emailEntity) {
        log.info("BusinessAuthenticationMapper - mapToBusinessEntity - businessRegistrationRequest: {}, ipAddress: {}, emailEntity: {}",
                businessRegistrationRequest, ipAddress, emailEntity);

        BusinessEntity businessEntity = new BusinessEntity();
        businessEntity.setBusinessUuidId(UUID.randomUUID());
        businessEntity.setBusinessName(businessRegistrationRequest.getBusinessName());
        businessEntity.setContactFirstName(businessRegistrationRequest.getContactFirstName());
        businessEntity.setContactLastName(businessRegistrationRequest.getContactLastName());
        businessEntity.setBusinessCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        businessEntity.setBusinessUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        super.mapToUserEntity(businessRegistrationRequest, ipAddress, emailEntity, businessEntity);
        log.info("BusinessAuthenticationMapper - mapToBusinessEntity - businessEntity: {}", businessEntity);

        return businessEntity;
    }

}