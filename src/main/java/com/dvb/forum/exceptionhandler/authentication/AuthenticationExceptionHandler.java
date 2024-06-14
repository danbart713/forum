package com.dvb.forum.exceptionhandler.authentication;

import com.dvb.forum.exception.authentication.AuthenticationException;
import com.dvb.forum.exceptionhandler.ErrorListResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Collections;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class AuthenticationExceptionHandler {

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<ErrorListResponse> handleAuthenticationException(AuthenticationException exception) {
        log.info("AuthenticationExceptionHandler - handleAuthenticationException - exception.getMessage(): {}, exception.getHttpStatus(): {}",
                exception.getMessage(), exception.getHttpStatus());

        List<String> errorList = Collections.singletonList(exception.getMessage());

        ErrorListResponse errorListResponse = new ErrorListResponse(errorList);
        log.info("AuthenticationExceptionHandler - handleAuthenticationException - errorListResponse: {}", errorListResponse);

        return new ResponseEntity<>(errorListResponse, exception.getHttpStatus());
    }

    @ExceptionHandler({org.springframework.security.core.AuthenticationException.class})
    public ResponseEntity<ErrorListResponse> handleSpringAuthenticationException(org.springframework.security.core.AuthenticationException exception) {
        log.info("AuthenticationExceptionHandler - handleSpringAuthenticationException - exception.getMessage(): {}",
                exception.getMessage());

        List<String> errorList = Collections.singletonList(exception.getMessage());

        ErrorListResponse errorListResponse = new ErrorListResponse(errorList);
        log.info("AuthenticationExceptionHandler - handleSpringAuthenticationException - errorListResponse: {}", errorListResponse);

        HttpStatus httpStatus = createHttpStatus(exception);
        log.info("AuthenticationExceptionHandler - handleSpringAuthenticationException - httpStatus: {}", httpStatus);

        return new ResponseEntity<>(errorListResponse, httpStatus);
    }

    private HttpStatus createHttpStatus(org.springframework.security.core.AuthenticationException exception) {
        return switch (exception) {
            case InsufficientAuthenticationException insufficientAuthenticationException -> HttpStatus.UNAUTHORIZED;
            default -> HttpStatus.BAD_REQUEST;
        };
    }

}
