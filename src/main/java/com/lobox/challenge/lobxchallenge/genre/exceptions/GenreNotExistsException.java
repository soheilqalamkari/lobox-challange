package com.lobox.challenge.lobxchallenge.genre.exceptions;

public class GenreNotExistsException extends RuntimeException {

    public GenreNotExistsException() {
    }

    public GenreNotExistsException(String message) {
        super(message);
    }

    public GenreNotExistsException(String message , Throwable cause) {
        super(message , cause);
    }
}
