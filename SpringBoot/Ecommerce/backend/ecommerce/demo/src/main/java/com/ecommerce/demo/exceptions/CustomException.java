package com.ecommerce.demo.exceptions;

public class CustomException extends IllegalArgumentException{

    public CustomException(String msg){
        super(msg);
    }
}
