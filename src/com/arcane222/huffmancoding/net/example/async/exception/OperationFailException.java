package com.arcane222.huffmancoding.net.example.async.exception;

public class OperationFailException extends RuntimeException {

    private static final long serialVersionUID = 3776334212124565954L;

    public OperationFailException(String operationType) {
        super("Fail to process operation, " + operationType);
    }
}
