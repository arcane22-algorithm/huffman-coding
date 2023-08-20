package com.arcane222.huffmancoding.net.example.async.data.buf;

import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBuffer;
import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBufferImpl;

import java.io.Closeable;

public interface NetBuffer extends AutoCloseable {


    void allocateData(byte data, int offset);

    void allocateData(byte[] data, int size, int srcOffset, int destOffset);

    byte[] deallocateData(int offset, int len);

    void freeBuffer();

    int bufferSize();

    boolean isDirect();
}
