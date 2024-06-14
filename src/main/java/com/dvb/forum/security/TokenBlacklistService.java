package com.dvb.forum.security;

import com.dvb.forum.entity.TokenBlacklistEntity;
import com.dvb.forum.exception.authentication.AuthenticationException;
import com.dvb.forum.repository.TokenBlacklistRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class TokenBlacklistService {

    private final TokenBlacklistMapper tokenBlacklistMapper;
    private final TokenBlacklistRepository tokenBlacklistRepository;

    @Autowired
    public TokenBlacklistService(TokenBlacklistMapper tokenBlacklistMapper,
                                 TokenBlacklistRepository tokenBlacklistRepository) {
        this.tokenBlacklistMapper = tokenBlacklistMapper;
        this.tokenBlacklistRepository = tokenBlacklistRepository;
    }

    public void addTokenToBlacklist(String token, LocalDateTime tokenExpirationDateTime) {
        log.info("TokenBlacklistService - addTokenToBlacklist - token: {}, tokenExpirationDateTime: {}", token, tokenExpirationDateTime);

        TokenBlacklistEntity tokenBlacklistEntity = tokenBlacklistMapper.mapToTokenBlacklistEntity(token, tokenExpirationDateTime);
        log.info("TokenBlacklistService - addTokenToBlacklist - tokenBlacklistEntity before saving: {}", tokenBlacklistEntity);

        tokenBlacklistEntity = tokenBlacklistRepository.save(tokenBlacklistEntity);
        log.info("TokenBlacklistService - addTokenToBlacklist - tokenBlacklistEntity after saving: {}", tokenBlacklistEntity);
    }

    public void checkBlacklistForToken(String token) throws AuthenticationException {
        log.info("TokenBlacklistService - checkBlacklistForToken - token: {}", token);

        if (tokenBlacklistRepository.findByToken(token).isPresent()) {
            log.info("TokenBlacklistService - checkBlacklistForToken - The token is present in the blacklist.");
            throw new AuthenticationException("The token is present in the blacklist.", HttpStatus.BAD_REQUEST);
        }

        log.info("TokenBlacklistService - checkBlacklistForToken - The token is not present in the blacklist.");
    }

    public void removeExpiredTokensFromBlacklist() {
        List<TokenBlacklistEntity> expiredTokenBlacklistEntityList = tokenBlacklistRepository.findAllExpired();
        log.info("TokenBlacklistService - removeExpiredTokensFromBlacklist - tokenBlacklistEntityList: {}", expiredTokenBlacklistEntityList);

        if (CollectionUtils.isNotEmpty(expiredTokenBlacklistEntityList)) {
            tokenBlacklistRepository.deleteAll(expiredTokenBlacklistEntityList);
            log.info("TokenBlacklistService - removeExpiredTokensFromBlacklist - TokenBlacklistEntities deleted.");
        }
    }

}
