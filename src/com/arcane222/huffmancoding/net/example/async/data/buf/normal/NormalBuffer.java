package com.arcane222.huffmancoding.net.example.async.data.buf.normal;

public interface NormalBuffer extends AutoCloseable {

    default byte getByte(int offset) {
        throw new UnsupportedOperationException("getByte does not supported");
    }

    default byte[] getBytes(int size, int offset) {
        throw new UnsupportedOperationException("getBytes does not supported");
    }

    default void setByte(byte data, int offset) {
        throw new UnsupportedOperationException("setByte does not supported");
    }

    default void setBytes(byte[] data, int size, int srcOffset, int destOffset) {
        throw new UnsupportedOperationException("setBytes does not supported");
    }

    int getSize();

    boolean isDirect();

    byte[] toArray();

    void freeBuffer();
}
