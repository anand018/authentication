package com.simlearn.authentication.exception;

public class InvalidOTPException extends RuntimeException {
    public InvalidOTPException (String message) {
        super(message);
    }
}
