package hu.endox.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import hu.endox.demo.model.ErrorResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleException(IllegalArgumentException e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.badRequest().body(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e) {
        LOG.error(e.getMessage(), e);
        return ResponseEntity.internalServerError().body(new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }
}
