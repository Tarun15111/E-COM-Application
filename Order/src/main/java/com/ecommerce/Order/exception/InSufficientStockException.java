package com.ecommerce.Order.exception;

public class InSufficientStockException extends RuntimeException{
    public InSufficientStockException(String message){
        super(message);
    }
}
