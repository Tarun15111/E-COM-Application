package com.ecommerce.User.exception;

import com.ecommerce.User.utils.APIResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandling {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<com.ecommerce.User.utils.APIResponse> resourceNotFoundException(ResourceNotFoundException exception){
            String message = exception.getMessage();
            APIResponse apiResponse = new APIResponse(message, false);
            return new ResponseEntity<>(apiResponse, HttpStatus.NOT_FOUND);
    }

//    @ExceptionHandler(InSufficientStockException.class)
//    public ResponseEntity<APIResponse> handlingOutOfStockException(InSufficientStockException exception){
//        String message = exception.getMessage();
//        APIResponse apiResponse = new APIResponse(message, false);
//        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
//    }

}
