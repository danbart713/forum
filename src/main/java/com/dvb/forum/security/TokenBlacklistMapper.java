package com.dvb.forum.security;

import com.dvb.forum.entity.TokenBlacklistEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

@Component
@Slf4j
public class TokenBlacklistMapper {

    public TokenBlacklistEntity mapToTokenBlacklistEntity(String token, LocalDateTime tokenExpirationDateTime) {
        log.info("TokenBlacklistMapper - mapToTokenBlacklistEntity - token: {}, tokenExpirationDateTime: {}", token, tokenExpirationDateTime);

        TokenBlacklistEntity tokenBlacklistEntity = new TokenBlacklistEntity();
        tokenBlacklistEntity.setUuidId(UUID.randomUUID());
        tokenBlacklistEntity.setToken(token);
        tokenBlacklistEntity.setTokenExpirationDateTime(tokenExpirationDateTime);
        tokenBlacklistEntity.setTokenBlacklistedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        tokenBlacklistEntity.setCreatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        tokenBlacklistEntity.setUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("TokenBlacklistMapper - mapToTokenBlacklistEntity - tokenBlacklistEntity: {}", tokenBlacklistEntity);

        return tokenBlacklistEntity;
    }

}