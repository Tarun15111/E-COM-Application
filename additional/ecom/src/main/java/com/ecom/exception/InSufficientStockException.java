package com.ecom.exception;

public class InSufficientStockException extends RuntimeException{
    public InSufficientStockException(String message){
        super(message);
    }
}
