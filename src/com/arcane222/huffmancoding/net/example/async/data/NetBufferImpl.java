package com.arcane222.huffmancoding.net.example.async.data;

import java.nio.ByteBuffer;

public abstract class NetBufferImpl implements NetBuffer {

    private final ByteBuffer buf;

    private int bufSize;

    private boolean isDirect;

    protected NetBufferImpl(int bufSize, boolean isDirect) {
        this.buf = isDirect ? ByteBuffer.allocateDirect(bufSize)
                : ByteBuffer.allocate(bufSize);
    }

    public void resize() {

    }

    public int getBufSize() {
        return bufSize;
    }

    public boolean isDirect() {
        return isDirect;
    }
}
