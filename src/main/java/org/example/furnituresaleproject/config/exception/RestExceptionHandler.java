package org.example.furnituresaleproject.config.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    private String getMessage(String key)
    {
        return messageSource.getMessage(
            key,
            null,
                "default message",
                LocaleContextHolder.getLocale()
        );
    }
    // handle exception with default annotation
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception,
                                                                  HttpHeaders headers, HttpStatusCode status, WebRequest webRequest) {
        String message = getMessage("MethodArgumentNotValidException.message");
        String detailMessage = exception.getLocalizedMessage();
        //error
        Map<String, String> errors = new HashMap<>();
        for(ObjectError error : exception.getBindingResult().getAllErrors()) {
            String fieldName = ((FieldError)error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        }
        int code = 400;
        String moreInformation = "http://localhost:8080/api/v1/exception/400";
//        ErrorResponse response = new ErrorResponse(message, detailMessage, errors, code, moreInformation);
        return new ResponseEntity<>(errors, status);
    }
}
