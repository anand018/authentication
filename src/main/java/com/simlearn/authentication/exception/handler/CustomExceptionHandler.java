package com.simlearn.authentication.exception.handler;

import com.simlearn.authentication.error.ErrorObject;
import com.simlearn.authentication.exception.AuthenticationFailedException;
import com.simlearn.authentication.exception.InvalidOTPException;
import com.simlearn.authentication.exception.InvalidUsernameException;
import com.simlearn.authentication.exception.OtpException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler {

    @ExceptionHandler(AuthenticationFailedException.class)
    public ResponseEntity<ErrorObject> handleAuthenticationFailedException(AuthenticationFailedException ae) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError("01");
        errorObject.setMessage(ae.getMessage());
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidUsernameException.class)
    public ResponseEntity<ErrorObject> handleAInvalidUsernameException(InvalidUsernameException ae) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError("02");
        errorObject.setMessage("invalid username");
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ErrorObject> handleInvalidPasswordException(InvalidPasswordException ae) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError("03");
        errorObject.setMessage("invalid old password");
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(InvalidOTPException.class)
    public ResponseEntity<ErrorObject> handleInvalidOTPException(InvalidOTPException ae) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError("04");
        errorObject.setMessage("invalid otp");
        return new ResponseEntity<>(errorObject, HttpStatus.UNAUTHORIZED);
    }
    @ExceptionHandler(OtpException.class)
    public ResponseEntity<ErrorObject> handleOtpException(OtpException ae) {
        ErrorObject errorObject = new ErrorObject();
        errorObject.setError("05");
        errorObject.setMessage("failed to send OTP");
        return new ResponseEntity<>(errorObject, HttpStatus.BAD_REQUEST);
    }
}