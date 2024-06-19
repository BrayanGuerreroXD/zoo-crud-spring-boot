package com.api.zoo.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.api.zoo.dto.response.ExceptionResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {
    private static final String MESSAGE = "message";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    public Map<String, String> handleDtoValidatorException (MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField(); 
            errors.put(fieldName, error.getDefaultMessage());
        });
        return errors;
    }
    
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler({JwtAuthException.class})
    public Map<String, String> handleJwtAuthException (JwtAuthException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, ex.getMessage());
        return errors;
    }
    
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({BadCredentialsException.class})
    public Map<String, String> handleBadCredentialsException (BadCredentialsException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Bad credentials");
        return errors;
    }
    
    @ResponseStatus(HttpStatus.FORBIDDEN)
    @ExceptionHandler({AccessDeniedException.class})
    public Map<String, String> handleAccessDeniedException (AccessDeniedException ex) {
        Map<String, String> errors = new HashMap<>();
        errors.put(MESSAGE, "Access is denied");
        return errors;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleException(Exception exception) {
        ExceptionResponseDto exceptionResponseDto = null;
        switch (exception.getClass().toString()) {
            case "class com.api.zoo.exception.EmailAlreadyExistsException":
                exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, "Email already exists");
                break;
            case "class com.api.zoo.exception.TokenNullException":
                exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, "Token cannot be null");
                break;
            case "class com.api.zoo.exception.InvalidTokenPrefixException":
                exceptionResponseDto = new ExceptionResponseDto(HttpStatus.BAD_REQUEST, "Invalid token prefix");
                break;
            case "class com.api.zoo.exception.InvalidTokenException":
                exceptionResponseDto = new ExceptionResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid token signature");
                break;
            case "class com.api.zoo.exception.TokenParsingException":
                exceptionResponseDto = new ExceptionResponseDto(HttpStatus.INTERNAL_SERVER_ERROR, "Invalid token argument");
                break;
            default:
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(Map.of(exception.getClass().toString(), exception.getMessage()));
        }
        return ResponseEntity.status(exceptionResponseDto.getStatus())
                .body(Map.of(MESSAGE, exceptionResponseDto.getMessage()));
    }
}