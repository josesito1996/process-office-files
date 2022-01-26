package com.example.demo.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse notFoundException(NotFoundException ex) {
        log.error("Recurso no encontrado : " + ex.getMessage());
        return new ErrorResponse(HttpStatus.NOT_FOUND.value(), LocalDateTime.now(), ex.getMessage(),
                new ArrayList<>());
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse badRequestException(BadRequestException ex) {
        log.error("Mal ingreso de Datos : " + ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                ex.getMessage(), new ArrayList<>());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        log.error("Error de validacion : " + ex.getMessage());
        List<ErrorDetail> details = new ArrayList<ErrorDetail>();
        ex.getBindingResult().getFieldErrors().forEach(item -> {
            details.add(new ErrorDetail(item.getField(), item.getDefaultMessage()));
        });
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                "Validacion de campos", details);
    }
    
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMethodArgumentNotValid(HttpMessageNotReadableException ex) {
        log.error("Error de handleMethodArgumentNotValid : " + ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                ex.getMessage(), null);
    }
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleMisingServletRequest(MissingServletRequestParameterException ex) {
        log.error("Error de handleMisingServletRequest : " + ex.getMessage());
        return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                ex.getMessage(), null);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleInternalError(Exception ex) {
    	log.error("Error {}", ex);
    	return new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), LocalDateTime.now(),
                ex.getMessage(), null);
    }
    /**
     * ConstraintViolationException
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInternalError(ConstraintViolationException ex) {
    	return new ErrorResponse(HttpStatus.BAD_REQUEST.value(), LocalDateTime.now(),
                ex.getMessage(), null);
    }
}
