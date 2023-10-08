package com.ecommerce.demo.exceptions;

public class AuthenticationFailException extends  IllegalArgumentException{
    public AuthenticationFailException(String msg){
        super(msg);
    }
}
