package com.dvb.forum.security;

import com.dvb.forum.entity.UserEntity;
import com.dvb.forum.exception.authentication.AuthenticationException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.javatuples.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final UserService userService;
    private final HandlerExceptionResolver handlerExceptionResolver;
    private final TokenBlacklistService tokenBlacklistService;

    @Autowired
    public JwtAuthenticationFilter(JwtTokenService jwtTokenService,
                                   UserService userService,
                                   HandlerExceptionResolver handlerExceptionResolver,
                                   TokenBlacklistService tokenBlacklistService) {
        this.jwtTokenService = jwtTokenService;
        this.userService = userService;
        this.handlerExceptionResolver = handlerExceptionResolver;
        this.tokenBlacklistService = tokenBlacklistService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) {
        try {
            log.info("JwtAuthenticationFilter - doFilterInternal - httpServletRequest: {}, httpServletResponse: {}, filterChain: {}",
                    httpServletRequest, httpServletResponse, filterChain);

            String authHeader = httpServletRequest.getHeader("Authorization");
            log.info("JwtAuthenticationFilter - doFilterInternal - authHeader: {}", authHeader);

            if (StringUtils.isBlank(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
                log.info("JwtAuthenticationFilter - doFilterInternal - authHeader is blank or authHeader does not start with \"Bearer \"");
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }

            String token = authHeader.substring(7);
            log.info("JwtAuthenticationFilter - doFilterInternal - token: {}", token);

            String email = jwtTokenService.extractEmailFromToken(token);
            log.info("JwtAuthenticationFilter - doFilterInternal - email: {}", email);

            tokenBlacklistService.checkBlacklistForToken(token);
            log.info("JwtAuthenticationFilter - doFilterInternal - The token is not present in the blacklist.");

            if (StringUtils.isNotBlank(email) && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.info("JwtAuthenticationFilter - doFilterInternal - email is not blank and SecurityContextHolder SecurityContext Authentication is null.");

                UserEntity userEntity = userService.retrieveUserEntity(email);
                log.info("JwtAuthenticationFilter - doFilterInternal - userEntity: {}", userEntity);

                if (jwtTokenService.validateToken(token, userEntity)) {
                    log.info("JwtAuthenticationFilter - doFilterInternal - Token is valid.");

                    List<GrantedAuthority> grantedAuthorityList = createGrantedAuthorityList(userEntity);
                    log.info("JwtAuthenticationFilter - doFilterInternal - authorities: {}", grantedAuthorityList);

                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = createUsernamePasswordAuthenticationToken(userEntity, grantedAuthorityList, token);
                    log.info("JwtAuthenticationFilter - doFilterInternal - authToken: {}", usernamePasswordAuthenticationToken);

                    createSecurityContext(usernamePasswordAuthenticationToken);
                    log.info("JwtAuthenticationFilter - doFilterInternal - SecurityContextHolder.getContext().getAuthentication(): {}", SecurityContextHolder.getContext().getAuthentication());
                }
            }
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (Exception exception) {
            log.info("JwtAuthenticationFilter - doFilterInternal - httpServletRequest: {}, httpServletResponse: {}, exception.getMessage(): {}, exception.getClass(): {}",
                    httpServletRequest, httpServletResponse, exception.getMessage(), exception.getClass());

            handlerExceptionResolver.resolveException(httpServletRequest, httpServletResponse, null,
                    new AuthenticationException(exception.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

    private List<GrantedAuthority> createGrantedAuthorityList(UserEntity userEntity) {
        log.info("JwtAuthenticationFilter - createGrantedAuthorityList - userEntity: {}", userEntity);

        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        grantedAuthorityList.add(new SimpleGrantedAuthority(userEntity.getUserRole().name()));
        log.info("JwtAuthenticationFilter - createGrantedAuthorityList - grantedAuthorityList: {}", grantedAuthorityList);

        return grantedAuthorityList;
    }

    private UsernamePasswordAuthenticationToken createUsernamePasswordAuthenticationToken(UserEntity userEntity, List<GrantedAuthority> authorities, String token) {
        log.info("JwtAuthenticationFilter - createUsernamePasswordAuthenticationToken - userEntity: {}, authorities: {}, token: {}",
                userEntity, authorities, token);

        Date tokenExpirationDate = jwtTokenService.extractExpirationDateFromToken(token);
        log.info("JwtAuthenticationFilter - createUsernamePasswordAuthenticationToken - tokenExpirationDate: {}", tokenExpirationDate);

        LocalDateTime tokenExpirationDateTime = tokenExpirationDate.toInstant().atZone(ZoneId.of("UTC")).toLocalDateTime();
        log.info("JwtAuthenticationFilter - createUsernamePasswordAuthenticationToken - tokenExpirationDateTime: {}", tokenExpirationDateTime);

        Pair<String, LocalDateTime> tokenTokenExpirationDateTimePair = new Pair<>(token, tokenExpirationDateTime);
        log.info("JwtAuthenticationFilter - createUsernamePasswordAuthenticationToken - tokenTokenExpirationDateTimePair: {}", tokenTokenExpirationDateTimePair);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userEntity, null, authorities);
        usernamePasswordAuthenticationToken.setDetails(tokenTokenExpirationDateTimePair);
        log.info("JwtAuthenticationFilter - createUsernamePasswordAuthenticationToken - usernamePasswordAuthenticationToken: {}", usernamePasswordAuthenticationToken);

        return usernamePasswordAuthenticationToken;
    }

    private void createSecurityContext(UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken) {
        log.info("JwtAuthenticationFilter - createSecurityContext - usernamePasswordAuthenticationToken: {}", usernamePasswordAuthenticationToken);
        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(usernamePasswordAuthenticationToken);
        SecurityContextHolder.setContext(securityContext);
        log.info("JwtAuthenticationFilter - createSecurityContext - SecurityContextHolder.getContext().getAuthentication(): {}", SecurityContextHolder.getContext().getAuthentication());
    }

}