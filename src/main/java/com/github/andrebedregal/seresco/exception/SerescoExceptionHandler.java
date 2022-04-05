package com.github.andrebedregal.seresco.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;

@RestControllerAdvice
@Slf4j
public class SerescoExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ErrorNoEncontrado.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private ResponseEntity<ErrorModel> handleNotFound(ErrorNoEncontrado ex) {
        log.info("NOT FOUND: " + ex.getDetails());

        ErrorModel error = new ErrorModel(HttpStatus.NOT_FOUND, ex.getMessage(), ex.getDetails());
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ErrorInesperado.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    private ResponseEntity<ErrorModel> handleUnexpected(ErrorInesperado ex) {
        log.info("UNEXPECTED: " + ex.getDetails());

        ErrorModel error = new ErrorModel(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage(), ex.getDetails());
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ErrorNegocio.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    private ResponseEntity<ErrorModel> handleUnexpected(ErrorNegocio ex) {
        log.info("BUSINESS LOGIC ERROR: " + ex.getDetails());

        ErrorModel error = new ErrorModel(HttpStatus.CONFLICT, ex.getMessage(), ex.getDetails());
        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        log.info("ARGUMENT NOT VALID: " + ex.getMessage());

        ArrayList<String> messages = new ArrayList<>();
        for (FieldError fe : ex.getBindingResult().getFieldErrors())
            messages.add(fe.getField() + ": " + fe.getDefaultMessage());

        ErrorModel error = new ErrorModel(status, "Los argumentos no son válidos.", messages.toString());
        return new ResponseEntity<>(error, status);
    }

}
