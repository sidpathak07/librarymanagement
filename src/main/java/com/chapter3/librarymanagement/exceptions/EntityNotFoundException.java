package com.chapter3.librarymanagement.exceptions;

public class EntityNotFoundException extends Exception{
    public EntityNotFoundException(String message){
        super(message);
    }
}
