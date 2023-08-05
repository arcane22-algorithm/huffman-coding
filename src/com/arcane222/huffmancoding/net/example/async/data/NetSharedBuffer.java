package com.arcane222.huffmancoding.net.example.async.data;

import java.nio.ByteBuffer;

public class NetSharedBuffer extends NetBufferImpl {

    protected NetSharedBuffer(int bufSize, boolean isDirect) {
        super(bufSize, isDirect);
    }

    @Override
    public void allocate(byte[] data) {
        
    }

    @Override
    public void deallocate(int offset, int data) {

    }

    @Override
    public void freeBuffer() {

    }
}
