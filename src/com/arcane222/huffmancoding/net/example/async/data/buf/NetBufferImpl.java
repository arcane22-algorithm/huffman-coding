package com.arcane222.huffmancoding.net.example.async.data.buf;

import java.nio.ByteBuffer;

public abstract class NetBufferImpl implements NetBuffer {

    private ByteBuffer buf;

    private int bufSize;

    private boolean isDirect;

    protected NetBufferImpl(int bufSize, boolean isDirect) {
        initBuffer(bufSize, isDirect);
    }

    public void resize(int bufSize, boolean isDirect) {
        freeBuffer();
        initBuffer(bufSize, isDirect);
    }

    private void initBuffer(int bufSize, boolean isDirect) {
        this.bufSize = bufSize;
        this.isDirect = isDirect;
        this.buf = isDirect ? ByteBuffer.allocateDirect(bufSize)
                : ByteBuffer.allocate(bufSize);
    }

    protected void read(byte[] data, int offset, int len) {
        buf.flip();
        buf.get(data, offset, len);
    }

    protected void write(byte[] data, int offset, int len) {
        buf.flip();
        buf.put(data, offset, len);
    }

    public int getBufSize() {
        return bufSize;
    }

    public boolean isDirect() {
        return isDirect;
    }
}
