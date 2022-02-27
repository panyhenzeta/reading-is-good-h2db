package com.getir.readingisgood.aop;

import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.response.ErrorResponse;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.persistence.OptimisticLockException;
import java.time.Instant;

@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(value = {DataIntegrityViolationException.class, ConstraintViolationException.class})
    public ResponseEntity<ErrorResponse> handleConstraintViolationException(ConstraintViolationException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Instant.now(), ex.getMessage(), "ConstraintViolationException occured.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Instant.now(), ex.getMessage(), "MethodArgumentNotValidException occured.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = CustomValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomValidationException(CustomValidationException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Instant.now(), ex.getMessage(), "Exception occured.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = OptimisticLockException.class)
    public ResponseEntity<ErrorResponse> handleOptimisticLockException(OptimisticLockException ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Instant.now(), ex.getMessage(), "OptimisticLockException occured.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex){
        ErrorResponse errorResponse = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), Instant.now(), ex.getMessage(), "Exception occured.");
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
