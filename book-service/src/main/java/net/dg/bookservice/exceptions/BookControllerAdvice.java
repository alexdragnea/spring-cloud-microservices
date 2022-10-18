package net.dg.bookservice.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ValidationException;
import java.util.Collections;
import java.util.Map;

@RestControllerAdvice
public class BookControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {ValidationException.class , Exception.class})
    public ResponseEntity<Map<String, String>> globalExceptionHandler(Exception e) {
        e.printStackTrace();
        Map<String, String> message = Collections.singletonMap("message", e.getMessage());
        return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
    }


}
