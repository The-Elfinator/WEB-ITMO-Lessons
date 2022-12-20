package ru.itmo.wp.contoller.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.itmo.wp.exception.ValidationException;

@RestControllerAdvice
public class RestControllerHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<String> onValidationException (ValidationException validationException) {
        String errorMessage = toErrorMessage(validationException.getBindingResult());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    private String toErrorMessage(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            ObjectError objectError = bindingResult.getAllErrors().get(0);
            if (objectError instanceof FieldError fieldError) {
                return fieldError.getField() + ": " + fieldError.getDefaultMessage();
            } else {
                return objectError.getDefaultMessage();
            }
        } else {
            return null;
        }
    }
}
