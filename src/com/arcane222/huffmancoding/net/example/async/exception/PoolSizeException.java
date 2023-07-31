package com.arcane222.huffmancoding.net.example.async.exception;

public class PoolSizeException extends RuntimeException {

    private static final long serialVersionUID = 8313264525295523696L;

    public PoolSizeException() {
        super();
    }

    public PoolSizeException(String s) {
        super(s);
    }

    public PoolSizeException(int size) {
        super("ThreadPool size out of range: " + size);
    }
}
