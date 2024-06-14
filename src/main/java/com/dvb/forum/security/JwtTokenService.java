package com.dvb.forum.security;

import com.dvb.forum.entity.AdminEntity;
import com.dvb.forum.entity.BusinessEntity;
import com.dvb.forum.entity.IndividualEntity;
import com.dvb.forum.entity.UserEntity;
import com.dvb.forum.exception.authentication.AuthenticationException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.function.Function;

@Service
@Slf4j
public class JwtTokenService {

    private static final String JWT_TOKEN_ISSUER = "Forum Site";

    private final SecretKey secretKey;
    private final long jwtTokenExpirationTime;

    @Autowired
    public JwtTokenService(@Qualifier("jwtProperties") Properties jwtProperties) {
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtProperties.getProperty("secretkey")));
        this.jwtTokenExpirationTime = Long.parseLong(jwtProperties.getProperty("expiration"));
    }

    public String createToken(UserEntity userEntity) throws AuthenticationException {
        log.info("JwtTokenService - createToken - userEntity: {}", userEntity);

        Map<String, Object> claims = createClaims(userEntity);
        log.info("JwtTokenService - createToken - claims: {}", claims);

        String token = buildToken(claims, userEntity.getEmailEntity().getEmailAddress());
        log.info("JwtTokenService - createToken - token: {}", token);

        return token;
    }

    public boolean validateToken(String token, UserEntity userEntity) {
        log.info("JwtTokenService - validateToken - token: {}, userEntity: {}", token, userEntity);

        // TODO: Validate that token was not created before last password change
//        boolean validToken = tokenSubjectValid(token, userEntity) && !tokenExpired(token);
        boolean validToken = tokenSubjectValid(token, userEntity);
        log.info("JwtTokenService - validateToken - validToken: {}", validToken);

        return validToken;
    }

    public String extractEmailFromToken(String token) {
        log.info("JwtTokenService - extractEmailFromToken - token: {}", token);

        String email = extractClaimFromToken(token, Claims::getSubject);
        log.info("JwtTokenService - extractEmailFromToken - email: {}", email);

        return email;
    }

    public Date extractExpirationDateFromToken(String token) {
        log.info("JwtTokenService - extractExpirationDateFromToken - token: {}", token);

        Date expirationDate = extractClaimFromToken(token, Claims::getExpiration);
        log.info("JwtTokenService - extractExpirationDateFromToken - expirationDate: {}", expirationDate);

        return expirationDate;
    }

    private Map<String, Object> createClaims(UserEntity userEntity) throws AuthenticationException {
        log.info("JwtTokenService - createClaims - userEntity: {}", userEntity);

        Map<String, Object> claims = new HashMap<>();
        claims.put("userUuidId", userEntity.getUserUuidId().toString());
        claims.put("userRole", userEntity.getUserRole().name());

        switch (userEntity) {
            case IndividualEntity individualEntity -> claims.put("individualUuidId", individualEntity.getIndividualUuidId());
            case BusinessEntity businessEntity -> claims.put("businessUuidId", businessEntity.getBusinessUuidId());
            case AdminEntity adminEntity -> claims.put("adminUuidId", adminEntity.getAdminUuidId());
            default -> throw new AuthenticationException("UserEntity is not of the correct type.", HttpStatus.INTERNAL_SERVER_ERROR);
        }

        log.info("JwtTokenService - createClaims - claims: {}", claims);

        return claims;
    }

    private String buildToken(Map<String, Object> claims, String email) {
        log.info("JwtTokenService - buildToken - claims: {}, email: {}", claims, email);

        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(jwtTokenExpirationTime, ChronoUnit.SECONDS);
        String token = Jwts.builder()
                .issuer(JWT_TOKEN_ISSUER)
                .subject(email)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .id(UUID.randomUUID().toString())
                .claims(claims)
                .signWith(secretKey)
                .compact();
        log.info("JwtTokenService - buildToken - token: {}", token);

        return token;
    }

    public boolean tokenSubjectValid(String token, UserEntity userEntity) {
        log.info("JwtTokenService - tokenSubjectValid - token: {}, userEntity: {}", token, userEntity);

        final String email = extractEmailFromToken(token);
        log.info("JwtTokenService - tokenSubjectValid - email: {}", email);

        boolean tokenSubjectValid = email.equals(userEntity.getEmailEntity().getEmailAddress());
        log.info("JwtTokenService - tokenSubjectValid - tokenSubjectValid: {}", tokenSubjectValid);

        return tokenSubjectValid;
    }

//    private boolean tokenExpired(String token) {
//        log.info("JwtTokenService - isTokenExpired - token: {}", token);
//
//        final Date expirationDate = extractExpirationDateFromToken(token);
//        log.info("JwtTokenService - isTokenExpired - expirationDate: {}", expirationDate);
//
//        boolean tokenExpired = expirationDate.before(Date.from(Instant.now()));
//        log.info("JwtTokenService - isTokenExpired - tokenExpired: {}", tokenExpired);
//
//        return tokenExpired;
//    }

    private <T> T extractClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        log.info("JwtTokenService - extractClaimFromToken - token: {}", token);

        final Claims claims = extractAllClaimsFromToken(token);
        log.info("JwtTokenService - extractClaimFromToken - claims: {}", claims);

        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaimsFromToken(String token) {
        log.info("JwtTokenService - extractAllClaimsFromToken - token: {}", token);

        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        log.info("JwtTokenService - extractAllClaimsFromToken - claims: {}", claims);

        return claims;
    }

}
