package com.lobox.challenge.lobxchallenge.person.exceptions;

public class PersonNotExistsException extends RuntimeException{
    public PersonNotExistsException() {
    }

    public PersonNotExistsException(String message) {
        super(message);
    }

    public PersonNotExistsException(String message , Throwable cause) {
        super(message , cause);
    }
}
