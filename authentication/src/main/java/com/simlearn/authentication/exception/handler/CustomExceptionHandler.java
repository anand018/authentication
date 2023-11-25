package com.simlearn.authentication.exception.handler;

import com.simlearn.authentication.error.ErrorObject;
import com.simlearn.authentication.exception.AuthenticationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorObject> handleAuthenticationFailedException(AuthenticationFailedException ae) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError("Authentication Failed");
        errorObject.setMessage("username or password is incorrect");
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }
}