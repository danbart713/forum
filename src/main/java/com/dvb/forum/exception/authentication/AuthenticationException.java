package com.dvb.forum.exception.authentication;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthenticationException extends Exception {

    private final HttpStatus httpStatus;

    public AuthenticationException(String message, HttpStatus httpStatus) {
        this(message, null, httpStatus);
    }

    public AuthenticationException(Throwable cause, HttpStatus httpStatus) {
        this(cause != null ? cause.getMessage() : null, cause, httpStatus);
    }

    public AuthenticationException(String message, Throwable cause, HttpStatus httpStatus) {
        super(message);
        if (cause != null) {
            super.initCause(cause);
        }
        this.httpStatus = httpStatus;
    }

}
