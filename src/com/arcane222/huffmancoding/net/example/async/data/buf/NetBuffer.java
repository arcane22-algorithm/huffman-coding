package com.arcane222.huffmancoding.net.example.async.data.buf;

import java.io.Closeable;

public interface NetBuffer extends Closeable {

    void allocateData(byte[] data);
    void deallocateData(int offset, int len);
    void freeBuffer();

    int bufferSize();

    boolean isDirect();
}
