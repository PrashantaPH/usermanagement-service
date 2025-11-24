package com.usermanagement.exception.handler;

import com.common.dto.ApiResponse;
import com.usermanagement.exception.ApplicationException;
import com.usermanagement.exception.UserAlreadyExistsException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.common.utils.CommonUtil.errorObject;

@RestControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<ApiResponse<Object>> handleTechnicalException(ApplicationException exception) {
        ApiResponse<Object> response = errorObject(exception.getMessage(), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserAlreadyExists(UserAlreadyExistsException exception) {
        ApiResponse<Object> response = errorObject(exception.getMessage(), exception.getErrorCode());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ApiResponse<Object>>> handleInvalidArgument(MethodArgumentNotValidException exception) {
        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        List<ApiResponse<Object>> errorResponses = fieldErrors.stream()
                .map(error -> errorObject(error.getDefaultMessage(), error.getField()))
                .toList();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponses);
    }
}
