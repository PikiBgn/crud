package com.crud.Exceptions;

public class BookNotFoundException extends Throwable {
    public BookNotFoundException(String message){
        super(message);
    }
}
