package com.dvb.forum.controller.authentication;

import com.dvb.forum.dto.authentication.*;
import com.dvb.forum.exception.authentication.AuthenticationException;
import com.dvb.forum.service.authentication.AuthenticationService;
import com.dvb.forum.util.CommonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authentication")
@Slf4j
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserRegistrationResponse> register(@Valid @RequestBody UserRegistrationRequest userRegistrationRequest,
                                                                       HttpServletRequest httpServletRequest) throws AuthenticationException {
        log.info("AuthenticationController - register - userRegistrationRequest: {}, httpServletRequest: {}",
                userRegistrationRequest, httpServletRequest);

        String ipAddress = CommonUtil.retrieveIpAddress(httpServletRequest);
        log.info("AuthenticationController - register - ipAddress: {}", ipAddress);

        UserRegistrationResponse userRegistrationResponse = authenticationService.register(userRegistrationRequest, ipAddress);
        log.info("AuthenticationController - register - userRegistrationResponse: {}", userRegistrationResponse);

        return new ResponseEntity<>(userRegistrationResponse, HttpStatus.CREATED);
    }

    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest,
                                                   HttpServletRequest httpServletRequest) throws AuthenticationException {
        log.info("AuthenticationController - login - userLoginRequest: {}, httpServletRequest: {}", userLoginRequest, httpServletRequest);

        String ipAddress = CommonUtil.retrieveIpAddress(httpServletRequest);
        log.info("AuthenticationController - login - ipAddress: {}", ipAddress);

        UserLoginResponse userLoginResponse = authenticationService.login(userLoginRequest, ipAddress);
        log.info("AuthenticationController - login - userLoginResponse: {}", userLoginResponse);

        return new ResponseEntity<>(userLoginResponse, HttpStatus.OK);
    }

    @RequestMapping(path = "/logout", method = RequestMethod.GET)
    public ResponseEntity<String> logout(HttpServletRequest httpServletRequest) {
        log.info("AuthenticationController - logout - httpServletRequest: {}", httpServletRequest);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        log.info("AuthenticationController - logout - usernamePasswordAuthenticationToken: {}", usernamePasswordAuthenticationToken);

        authenticationService.logout(usernamePasswordAuthenticationToken);

        return new ResponseEntity<>("Logout successful.", HttpStatus.OK);
    }

    @RequestMapping(path = "/refreshToken", method = RequestMethod.GET)
    public ResponseEntity<RefreshTokenResponse> refreshToken(HttpServletRequest httpServletRequest) {
        log.info("AuthenticationController - refreshToken - httpServletRequest: {}", httpServletRequest);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = (UsernamePasswordAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        log.info("AuthenticationController - refreshToken - usernamePasswordAuthenticationToken: {}", usernamePasswordAuthenticationToken);

        RefreshTokenResponse refreshTokenResponse = authenticationService.refreshToken(usernamePasswordAuthenticationToken);
        log.info("AuthenticationController - refreshToken - refreshTokenResponse: {}", refreshTokenResponse);

        return new ResponseEntity<>(refreshTokenResponse, HttpStatus.OK);
    }

}
