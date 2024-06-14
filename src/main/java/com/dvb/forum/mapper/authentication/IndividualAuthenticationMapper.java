package com.dvb.forum.mapper.authentication;

import com.dvb.forum.dto.authentication.IndividualRegistrationRequest;
import com.dvb.forum.entity.EmailEntity;
import com.dvb.forum.entity.IndividualEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
@Slf4j
public class IndividualAuthenticationMapper extends UserAuthenticationMapper {

    @Autowired
    public IndividualAuthenticationMapper(PasswordEncoder passwordEncoder) {
        super(passwordEncoder);
    }

    public IndividualEntity mapToIndividualEntity(IndividualRegistrationRequest individualRegistrationRequest, String ipAddress, EmailEntity emailEntity) {
        log.info("IndividualAuthenticationMapper - mapToIndividualEntity - individualRegistrationRequest: {}, ipAddress: {}, emailEntity: {}",
                individualRegistrationRequest, ipAddress, emailEntity);

        IndividualEntity individualEntity = new IndividualEntity();
        individualEntity.setIndividualUuidId(UUID.randomUUID());
        individualEntity.setIndividualCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        individualEntity.setIndividualUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        super.mapToUserEntity(individualRegistrationRequest, ipAddress, emailEntity, individualEntity);
        log.info("IndividualAuthenticationMapper - mapToIndividualEntity - individualEntity: {}", individualEntity);

        return individualEntity;
    }

}
