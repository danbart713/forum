package com.dvb.forum.exceptionhandler.validation;

import com.dvb.forum.exceptionhandler.ErrorMapResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class ValidationExceptionHandler {

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorMapResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        log.info("ValidationExceptionHandler - handleMethodArgumentNotValidException - exception.getBindingResult(): {}",
                exception.getBindingResult());

        ErrorMapResponse errorMapResponse = new ErrorMapResponse(createErrors(exception));
        log.info("ValidationExceptionHandler - handleMethodArgumentNotValidException - errorMapResponse: {}", errorMapResponse);

        return new ResponseEntity<>(errorMapResponse, HttpStatus.BAD_REQUEST);
    }

    private Map<String, String> createErrors(MethodArgumentNotValidException exception) {
        return exception.getBindingResult().getAllErrors().stream()
                .collect(Collectors.toMap(
                        error -> ((FieldError) error).getField(),
                        error -> error.getDefaultMessage()
                ));
    }

}
