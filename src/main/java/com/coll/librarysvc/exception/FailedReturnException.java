package com.coll.librarysvc.exception;

public class FailedReturnException extends RuntimeException {
    public FailedReturnException(String message) {
        super(message);
    }
}