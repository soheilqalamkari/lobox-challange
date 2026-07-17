package com.lobox.challenge.lobxchallenge.utils.exceptions.handler;

import com.lobox.challenge.lobxchallenge.utils.exceptions.exception.CustomException;
import com.lobox.challenge.lobxchallenge.utils.exceptions.message.ExceptionMessage;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.time.ZoneId;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ControllerAdvice {


    @ExceptionHandler(CustomException.class)
    public final ResponseEntity<ExceptionMessage> handleCustomException(CustomException exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionMessage(
                Instant.now().atZone(ZoneId.systemDefault()).toInstant() ,
                exception.getMessage(),
                request.getRequestURI()
        ) , HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler({IllegalArgumentException.class , ConstraintViolationException.class})
    public ResponseEntity<ExceptionMessage> handleBadRequest(Exception exception , HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionMessage(
                Instant.now().atZone(ZoneId.systemDefault()).toInstant() ,
                exception.getMessage() ,
                request.getRequestURI()
        ) , HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionMessage> handleValidation(MethodArgumentNotValidException exception, HttpServletRequest request) {
        String message = exception.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldErrorOnValidations)
                .collect(Collectors.joining(", "));
        return new ResponseEntity<>(new ExceptionMessage(
                Instant.now().atZone(ZoneId.systemDefault()).toInstant() ,
                message ,
                request.getRequestURI()
        ) , HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionMessage> handleInternalServerException(Exception exception, HttpServletRequest request) {
        return new ResponseEntity<>(new ExceptionMessage(
                Instant.now().atZone(ZoneId.systemDefault()).toInstant() ,
                "Internal server exception." ,
                request.getRequestURI()
        ) , HttpStatus.INTERNAL_SERVER_ERROR);
    }



    private String formatFieldErrorOnValidations(FieldError error) {
        return error.getField() + ": " + error.getDefaultMessage();
    }
}
