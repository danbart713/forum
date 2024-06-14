package com.dvb.forum.repository;

import com.dvb.forum.entity.TokenBlacklistEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklistEntity, Long> {

    Optional<TokenBlacklistEntity> findByToken(String token);

    @Query("SELECT tokenBlacklist FROM TokenBlacklistEntity tokenBlacklist WHERE tokenBlacklist.tokenExpirationDateTime <= UTC_TIMESTAMP()")
    List<TokenBlacklistEntity> findAllExpired();

}