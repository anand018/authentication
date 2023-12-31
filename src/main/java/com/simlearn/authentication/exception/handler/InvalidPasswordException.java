package com.simlearn.authentication.exception.handler;

public class InvalidPasswordException extends RuntimeException {
    public InvalidPasswordException (String message) {
        super(message);
    }
}
