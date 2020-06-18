package com.binus.project.forumuaa.exception;


public class SystemErrorException extends RuntimeException {
    public SystemErrorException(String message, Exception cause) {
        super(message,cause);
    }

    public SystemErrorException(String message) {
        super(message);
    }
}
