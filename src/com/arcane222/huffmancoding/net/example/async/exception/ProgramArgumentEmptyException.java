package com.arcane222.huffmancoding.net.example.async.exception;

public class ProgramArgumentEmptyException extends RuntimeException {
    private static final long serialVersionUID = 3055522871634932741L;

    public ProgramArgumentEmptyException() {
        super("Program Argument is empty.");
    }

    public ProgramArgumentEmptyException(String msg) {
        super(msg);
    }
}
