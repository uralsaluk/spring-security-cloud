package com.ural.security.firstsecurity.config;

import lombok.var;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;


@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class MyGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(AuthenticationException.class )
    protected ResponseEntity<ErrorResponse> handleAuthenticationException(Exception ex) {
        List<String> errors = new ArrayList<>();

        var message = String.join(", ", errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .code(401)
                                .message(message)
                                .build());
    }

    @ExceptionHandler(BadCredentialsException.class )
    protected ResponseEntity<ErrorResponse> handleABadnException(Exception ex) {
        List<String> errors = new ArrayList<>();

        var message = String.join(", ", errors);
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ErrorResponse.builder()
                                .code(401)
                                .message(message)
                                .build());
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<ErrorResponse> handleException(Exception ex) {
        return ResponseEntity.status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .code(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                                .message(ex.getMessage())
                                .build());
    }
}
