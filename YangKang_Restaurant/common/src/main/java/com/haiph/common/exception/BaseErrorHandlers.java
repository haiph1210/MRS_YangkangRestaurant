package com.haiph.common.exception;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;

import java.util.HashMap;
import java.util.Map;

import com.haiph.common.dto.response.Response;
import com.haiph.common.dto.response.ResponseBody;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Log4j2(topic = "ErrorLogger")
@RestControllerAdvice
public class BaseErrorHandlers {

    @ExceptionHandler(value = ValidationException.class)
    public ResponseEntity<String> handleException(ValidationException exception) {
        log.error(exception.getMessage(),exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMsg());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseBody handleValidationException(MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(),ex);
        List<FieldError> fieldErrors = ex.getBindingResult().getAllErrors().stream().map(FieldError.class::cast).collect(Collectors.toList());
        Response response = Response.PARAM_INVALID;
        if (fieldErrors.stream().map(s -> s.unwrap(ConstraintViolation.class))
                .anyMatch(e -> e.getConstraintDescriptor().getAnnotation() instanceof NotBlank)) {
            response = Response.MISSING_PARAM;
        }
        return new ResponseBody(response,
                fieldErrors.stream()
                        .collect(Collectors.toMap(FieldError::getField,
                                error -> String.valueOf(error.getDefaultMessage()), (p, q) -> p)));
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseBody handleConstrainsException(ConstraintViolationException ex) {
        log.error(ex.getMessage(),ex);
        Response response = Response.PARAM_INVALID;
        Map<String, String> mapErrMess = new HashMap<>();
        int i = 1;
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            mapErrMess.put("violation_" + i, violation.getMessage());
        }
        return new ResponseBody(response, mapErrMess);
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseBody handleAuthorizationException(AccessDeniedException ex) {
        log.error("Authorization Error: ", ex);
        return new ResponseBody(Response.ACCESS_DENIED.getResponseCode(),
                Response.ACCESS_DENIED.getResponseMessage(),
                ex.getMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = EntityNotFoundException.class)
    public ResponseBody handleException(EntityNotFoundException exception) {
        log.error("EntityNotFound Exception Error: ", exception);
        return new ResponseBody(Response.OBJECT_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    @ExceptionHandler(value = NoResultException.class)
    public ResponseBody handleException(NoResultException exception) {
        log.error("NoResult Exception Error: ", exception);
        return new ResponseBody(Response.DATA_NOT_FOUND);
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ResponseBody handleGeneralException(Exception ex) {
        log.error("General Error: ", ex);
        return new ResponseBody(Response.SYSTEM_ERROR);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseBody handleCommonException(CommonException e) {
        log.error("Common exception Error: ", e);
        return new ResponseBody(e.getResponse(), e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseBody handleIllegalArgumentException(IllegalArgumentException e) {
        log.error("Exception Error: ", e);
        return new ResponseBody(Response.PARAM_NOT_VALID, e.getMessage());
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseBody handleIllegalArgumentException(HttpMessageNotReadableException e) {
        log.error("Http message Error: ", e);
        Map<String, String> mapErrMess = ((MismatchedInputException) e.getCause()).getPath().stream()
                .collect(Collectors.toMap(JsonMappingException.Reference::getFieldName, s -> "Không đúng định dạng"));
        return new ResponseBody(Response.PARAM_INVALID, mapErrMess);
    }

    @ExceptionHandler(AuthorizationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseBody handleAuthorizationException(AuthorizationException e){
        log.error("Access denied Error: ", e);
        return new ResponseBody(Response.ACCESS_DENIED.getResponseCode(),
                Response.ACCESS_DENIED.getResponseMessage(),
                e.getMessage());
    }
}
