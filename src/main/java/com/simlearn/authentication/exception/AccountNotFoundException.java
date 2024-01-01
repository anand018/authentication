package com.simlearn.authentication.exception;

public class AccountNotFoundException extends RuntimeException {
    public AccountNotFoundException (String message) {
        super(message);
    }
}
