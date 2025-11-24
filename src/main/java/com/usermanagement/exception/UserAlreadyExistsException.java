package com.usermanagement.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String errorCode;

    public UserAlreadyExistsException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public UserAlreadyExistsException(String message, String errorCode, Throwable throwable) {
        super(message, throwable);
        this.errorCode = errorCode;
    }
}
