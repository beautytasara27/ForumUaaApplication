package com.binus.project.forumuaa.exception;


public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message, Exception cause) {
        super(message,cause);
    }

    public AccessDeniedException(String message) {
        super(message);
    }
}
