package com.dvb.forum.service.authentication;

import com.dvb.forum.dto.authentication.*;
import com.dvb.forum.entity.EmailEntity;
import com.dvb.forum.entity.UserEntity;
import com.dvb.forum.entity.UserLoginRecordEntity;
import com.dvb.forum.exception.authentication.AuthenticationException;
import com.dvb.forum.mapper.authentication.AdminAuthenticationMapper;
import com.dvb.forum.mapper.authentication.AuthenticationMapper;
import com.dvb.forum.mapper.authentication.BusinessAuthenticationMapper;
import com.dvb.forum.mapper.authentication.IndividualAuthenticationMapper;
import com.dvb.forum.repository.UserLoginRecordRepository;
import com.dvb.forum.repository.UserRepository;
import com.dvb.forum.security.JwtTokenService;
import com.dvb.forum.security.TokenBlacklistService;
import lombok.extern.slf4j.Slf4j;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final AuthenticationMapper authenticationMapper;
    private final IndividualAuthenticationMapper individualAuthenticationMapper;
    private final BusinessAuthenticationMapper businessAuthenticationMapper;
    private final AdminAuthenticationMapper adminAuthenticationMapper;
    private final UserRepository userRepository;
    private final UserLoginRecordRepository userLoginRecordRepository;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtTokenService jwtTokenService,
                                 AuthenticationMapper authenticationMapper,
                                 IndividualAuthenticationMapper individualAuthenticationMapper,
                                 BusinessAuthenticationMapper businessAuthenticationMapper,
                                 AdminAuthenticationMapper adminAuthenticationMapper,
                                 UserRepository userRepository,
                                 UserLoginRecordRepository userLoginRecordRepository,
                                 TokenBlacklistService tokenBlacklistService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.authenticationMapper = authenticationMapper;
        this.individualAuthenticationMapper = individualAuthenticationMapper;
        this.businessAuthenticationMapper = businessAuthenticationMapper;
        this.adminAuthenticationMapper = adminAuthenticationMapper;
        this.userRepository = userRepository;
        this.userLoginRecordRepository = userLoginRecordRepository;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    public UserRegistrationResponse register(UserRegistrationRequest userRegistrationRequest, String ipAddress) throws AuthenticationException {
        log.info("AuthenticationService - register - userRegistrationRequest: {}, ipAddress: {}", userRegistrationRequest, ipAddress);

        verifyEmailDoesNotAlreadyExist(userRegistrationRequest);
        verifyDisplayNameDoesNotAlreadyExist(userRegistrationRequest);

        EmailEntity emailEntity = authenticationMapper.mapToEmailEntity(userRegistrationRequest);
        log.info("AuthenticationService - register - emailEntity: {}", emailEntity);

        UserEntity userEntity = createUserEntity(userRegistrationRequest, ipAddress, emailEntity);
        log.info("AuthenticationService - register - userEntity before saving: {}", userEntity);

        userEntity = userRepository.save(userEntity);
        log.info("AuthenticationService - register - userEntity after saving: {}", userEntity);

        UserLoginRecordEntity userLoginRecordEntity = authenticationMapper.mapToUserLoginRecordEntity(ipAddress, userEntity);
        log.info("AuthenticationService - register - userLoginRecordEntity before saving: {}", userLoginRecordEntity);

        userLoginRecordEntity = userLoginRecordRepository.save(userLoginRecordEntity);
        log.info("AuthenticationService - register - userLoginRecordEntity after saving: {}", userLoginRecordEntity);

        String token = jwtTokenService.createToken(userEntity);
        log.info("AuthenticationService - register - token: {}", token);

        UserRegistrationResponse userRegistrationResponse = authenticationMapper.mapToUserRegistrationResponse(token);
        log.info("AuthenticationService - register - userRegistrationResponse: {}", userRegistrationResponse);

        return userRegistrationResponse;
    }

    public UserLoginResponse login(UserLoginRequest userLoginRequest, String ipAddress) throws AuthenticationException {
        log.info("AuthenticationService - login - userLoginRequest: {}, ipAddress: {}", userLoginRequest, ipAddress);

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
        log.info("AuthenticationService - login - User has been authenticated.");

        UserEntity userEntity = userRepository.findByEmailAddress(userLoginRequest.getEmail())
                .orElseThrow(() -> new AuthenticationException("User not found with supplied email.", HttpStatus.BAD_REQUEST));
        log.info("AuthenticationService - login - userEntity: {}", userEntity);

        UserLoginRecordEntity userLoginRecordEntity = authenticationMapper.mapToUserLoginRecordEntity(ipAddress, userEntity);
        log.info("AuthenticationService - login - userLoginRecordEntity before saving: {}", userLoginRecordEntity);

        userLoginRecordEntity = userLoginRecordRepository.save(userLoginRecordEntity);
        log.info("AuthenticationService - login - userLoginRecordEntity after saving: {}", userLoginRecordEntity);

        String token = jwtTokenService.createToken(userEntity);
        log.info("AuthenticationService - login - token: {}", token);

        UserLoginResponse userLoginResponse = authenticationMapper.mapToUserLoginResponse(token);
        log.info("AuthenticationService - login - userLoginResponse: {}", userLoginResponse);

        updateUserEntity(userEntity);
        log.info("AuthenticationService - login - userEntity before saving: {}", userEntity);

        userEntity = userRepository.save(userEntity);
        log.info("AuthenticationService - login - userEntity after saving: {}", userEntity);

        return userLoginResponse;
    }

    public void logout(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        log.info("AuthenticationService - logout - usernamePasswordAuthenticationToken: {}", usernamePasswordAuthenticationToken);

        Pair<String, LocalDateTime> tokenTokenExpirationDateTimePair = (Pair<String, LocalDateTime>) usernamePasswordAuthenticationToken.getDetails();
        log.info("AuthenticationService - logout - tokenTokenExpirationDateTimePair: {}", tokenTokenExpirationDateTimePair);

        tokenBlacklistService.addTokenToBlacklist(tokenTokenExpirationDateTimePair.getValue0(), tokenTokenExpirationDateTimePair.getValue1());
        log.info("AuthenticationService - logout - Token blacklisted: {}", tokenTokenExpirationDateTimePair.getValue0());
    }

    public RefreshTokenResponse refreshToken(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        // TODO: finish this
        return null;
    }

    private void verifyEmailDoesNotAlreadyExist(UserRegistrationRequest userRegistrationRequest) throws AuthenticationException {
        if (userRepository.findByEmailAddress(userRegistrationRequest.getEmail()).isPresent()) {
            log.info("AuthenticationService - register - The email address is already associated with a user.");
            throw new AuthenticationException("The email address is already associated with a user.", HttpStatus.BAD_REQUEST);
        }
    }

    private void verifyDisplayNameDoesNotAlreadyExist(UserRegistrationRequest userRegistrationRequest) throws AuthenticationException {
        if (userRepository.findByDisplayName(userRegistrationRequest.getDisplayName()).isPresent()) {
            log.info("AuthenticationService - register - A user already exists with the display name entered.");
            throw new AuthenticationException("A user already exists with the display name entered.", HttpStatus.BAD_REQUEST);
        }
    }

    private UserEntity createUserEntity(UserRegistrationRequest userRegistrationRequest, String ipAddress, EmailEntity emailEntity) throws AuthenticationException {
        switch (userRegistrationRequest.getUserRole()) {
            case INDIVIDUAL:
                return individualAuthenticationMapper.mapToIndividualEntity((IndividualRegistrationRequest) userRegistrationRequest, ipAddress, emailEntity);
            case BUSINESS:
                return businessAuthenticationMapper.mapToBusinessEntity((BusinessRegistrationRequest) userRegistrationRequest, ipAddress, emailEntity);
            case ADMIN:
                verifyProspectiveAdminEmailAndToken((AdminRegistrationRequest) userRegistrationRequest);
                return adminAuthenticationMapper.mapToAdminEntity((AdminRegistrationRequest) userRegistrationRequest, ipAddress, emailEntity);
            default:
                throw new AuthenticationException("The user role is invalid.", HttpStatus.BAD_REQUEST);
        }
    }

    private void verifyProspectiveAdminEmailAndToken(AdminRegistrationRequest adminRegistrationRequest) throws AuthenticationException {
        // TODO: Implement check where super admin adds person's email and some kind of token to a table so that person with that email and token can register to become admin
        // TODO: Throw exception if verification fails
    }

    private void updateUserEntity(UserEntity userEntity) {
        log.info("AuthenticationService - updateUserEntity - userEntity before updating: {}", userEntity);
        userEntity.setLastLoginDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        userEntity.setUserUpdatedDateTime(LocalDateTime.now(ZoneId.of("UTC")));
        log.info("AuthenticationService - updateUserEntity - userEntity after updating: {}", userEntity);
    }

}