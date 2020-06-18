package com.binus.project.forumuaa.exception;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;


@ControllerAdvice(annotations = {RestController.class, Controller.class})
class ExceptionHandlerController {

    private final static Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

    @ExceptionHandler(InvalidRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error invalidRequest(InvalidRequestException e) {
        LOGGER.info("Validation error: {}", e.getMessage());
        return Error.createError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
    Error invalidRequest(ConstraintViolationException e) {
        String errorMessage = e.getConstraintName();
        LOGGER.info("Validation error: {}", e.getMessage());
        return Error.createError(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
 Error invalidRequest(MethodArgumentNotValidException e) {
        String errorMessage = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        LOGGER.info("Validation error: {}", e.getMessage());
        return Error.createError(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(BindException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
Error invalidRequest(BindException e) {
        String errorMessage = e.getBindingResult().getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(","));
        LOGGER.info("Validation error: {}", e.getMessage());
        return Error.createError(HttpStatus.BAD_REQUEST.value(), errorMessage);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public @ResponseBody
Error invalidRequest(DataIntegrityViolationException e) {
        LOGGER.info("Validation error: {}", e.getMessage());
        return Error.createError(HttpStatus.BAD_REQUEST.value(), e.getMessage());
    }

    @ExceptionHandler(RecordNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public @ResponseBody
    Error recordNotFound(RecordNotFoundException e) {
        LOGGER.info("Record not found error: {}", e.getMessage());
        return Error.createError(HttpStatus.NOT_FOUND.value(), e.getMessage());
    }

    @ExceptionHandler(SystemErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
    Error systemError(SystemErrorException e) {
        LOGGER.error("System error: message={},cause={}", e.getMessage(),e.getCause());
        return Error.createError(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage());
    }

    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public @ResponseBody
   Error systemError(DataAccessException e) {
        LOGGER.error("System error: {}", e.getMessage());
        return Error.createError(HttpStatus.INTERNAL_SERVER_ERROR.value(), "System error, kindly try again later");
    }

}
