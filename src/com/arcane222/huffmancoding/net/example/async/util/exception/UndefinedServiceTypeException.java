package com.arcane222.huffmancoding.net.example.async.util.exception;

public class UndefinedServiceTypeException extends RuntimeException {
    private static final long serialVersionUID = 1711249573474608214L;

    public UndefinedServiceTypeException() {
        super();
    }

    public UndefinedServiceTypeException(String type) {
        super("Undefined server type: " + type);
    }
}
