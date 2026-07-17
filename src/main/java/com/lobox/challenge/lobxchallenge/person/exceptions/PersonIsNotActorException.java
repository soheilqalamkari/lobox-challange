package com.lobox.challenge.lobxchallenge.person.exceptions;

public class PersonIsNotActorException extends RuntimeException {

    public PersonIsNotActorException() {
    }

    public PersonIsNotActorException(String message) {
        super(message);
    }

    public PersonIsNotActorException(String message , Throwable cause) {
        super(message , cause);
    }
}
