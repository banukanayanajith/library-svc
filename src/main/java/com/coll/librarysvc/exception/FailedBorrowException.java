package com.coll.librarysvc.exception;

public class FailedBorrowException extends RuntimeException {
    public FailedBorrowException(String message) {
        super(message);
    }
}