package com.arcane222.huffmancoding.net.example.async.data;

public interface NetBuffer {

    void allocate(byte[] data);
    void deallocate(int offset, int len);

    void freeBuffer();
}
