package com.unipi.large.scale.backend.controllers.errors;

import com.unipi.large.scale.backend.service.exceptions.DbException;
import com.unipi.large.scale.backend.service.exceptions.LoginException;
import com.unipi.large.scale.backend.service.exceptions.RegistrationException;
import com.unipi.large.scale.backend.service.exceptions.SimilarityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(
            {DbException.class}
    )
    ResponseEntity<ErrorResponse> notFoundException(Exception e) {
        return generateResponse(e, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            {RegistrationException.class, MethodArgumentNotValidException.class, LoginException.class, SimilarityException.class, RuntimeException.class}
    )
    ResponseEntity<ErrorResponse> badRequestException(Exception e) {
        return generateResponse(e, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<ErrorResponse> generateResponse(Exception e, HttpStatus httpStatus) {

        String exceptionMessage = e.getMessage();

        System.out.println(exceptionMessage);

        return new ResponseEntity<>(new ErrorResponse(httpStatus, exceptionMessage), httpStatus);
    }

}
