package com.haiph.common.exception;

public class AuthorizationException extends RuntimeException{
    public AuthorizationException(){
        super("Access denied!!!");
    }
}
