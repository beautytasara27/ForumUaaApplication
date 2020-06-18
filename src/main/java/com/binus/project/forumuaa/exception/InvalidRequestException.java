package com.binus.project.forumuaa.exception;


public class InvalidRequestException extends RuntimeException {
    public InvalidRequestException(String message, Exception cause) {
        super(message,cause);
    }

    public InvalidRequestException(String message) {
        super(message);
    }
}
