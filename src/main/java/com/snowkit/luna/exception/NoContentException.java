package com.snowkit.luna.exception;

public class NoContentException extends RuntimeException {

    public NoContentException() {
        super("Data does not exists.");
    }

    public NoContentException(String message) {
        super(message);
    }
}
