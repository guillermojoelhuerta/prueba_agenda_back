package com.agenda.agenda.exception;

import com.agenda.agenda.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerAdviceException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(value = BindingResultException.class)
    public ResponseEntity<ErrorResponseDto> bindingResultException(BindingResultException ex) {
        String result = "";
        if (ex.getBindingResult().hasErrors()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ObjectError error : ex.getBindingResult().getAllErrors()) {
                errorMessages.append(error.getDefaultMessage()).append(".");
            }
            result = errorMessages.toString();
        }
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), result);
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> resourceNotFoundException(ResourceNotFoundException ex) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
