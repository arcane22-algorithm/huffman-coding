package com.arcane222.huffmancoding.net.example.async.data.buf.normal;

import java.io.Closeable;

public abstract class NormalBufferImpl implements NormalBuffer {

    protected int size;
    boolean isDirect;

    protected NormalBufferImpl(int size) {
        this.size = size;
        createBuffer(size);
    }

    protected abstract void createBuffer(int size);


    @Override
    public final int getSize() {
        return size;
    }

    @Override
    public final boolean isDirect() {
        return isDirect;
    }

    @Override
    public final void close() {
        freeBuffer();
    }

    public static NormalBufferImpl of(NormalBufferType bufferType, int size) {
        switch (bufferType) {
            case HEAP:
                return new HeapBufferImpl(size);
            case DIRECT:
                return new DirectBufferImpl(size);
            case UNSAFE:
                return new UnsafeBufferImpl(size);
            default:
                throw new RuntimeException("Unsupported buffer type");
        }
    }
}
