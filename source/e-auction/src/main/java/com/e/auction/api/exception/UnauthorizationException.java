package com.e.auction.api.exception;

public class UnauthorizationException extends RuntimeException{
    public UnauthorizationException(String message){
        super(message);
    }
}
