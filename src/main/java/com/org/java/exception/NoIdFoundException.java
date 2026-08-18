package com.org.java.exception;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;


@Setter
@Getter
public class NoIdFoundException extends RuntimeException{

    private String errorMessage;
    private String errorCode;

    public NoIdFoundException(String errorMessage, String errorCode) {
        this.errorMessage = errorMessage;
        this.errorCode = errorCode;
    }
    public NoIdFoundException(){

    }



}