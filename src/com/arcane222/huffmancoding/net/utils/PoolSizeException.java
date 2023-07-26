package com.arcane222.huffmancoding.net.utils;

public class PoolSizeException extends RuntimeException {
    private static final long serialVersionUID = 0L;

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
