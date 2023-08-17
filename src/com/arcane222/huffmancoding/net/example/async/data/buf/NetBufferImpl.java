package com.arcane222.huffmancoding.net.example.async.data.buf;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;

public abstract class NetBufferImpl implements NetBuffer {

    // Get sun.misc.Unsafe instance

    private ByteBuffer buf;

    private int bufSize;
    private boolean isDirect;

    private long address;


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

    @Override
    public int bufferSize() {
        return this.bufSize;
    }

    @Override
    public boolean isDirect() {
        return this.isDirect;
    }

    @Override
    public final void close() throws IOException {
        freeBuffer();
    }
}
