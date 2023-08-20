package com.arcane222.huffmancoding.net.example.async.data.buf;

import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBuffer;
import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBufferImpl;
import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBufferType;


public abstract class NetBufferImpl implements NetBuffer {

    private final NormalBuffer buf;

    protected NetBufferImpl(NormalBufferType type, int size) {
        this.buf = NormalBufferImpl.of(type, size);
    }

    protected void write(byte data, int offset) {
        buf.setByte(data, offset);
    }

    protected void writeAll(byte[] data, int size, int srcOffset, int destOffset) {
        buf.setBytes(data, size, srcOffset, destOffset);
    }

    protected byte read(int offset) {
        return buf.getByte(offset);
    }

    protected byte[] readAll(int size, int offset) {
        return buf.getBytes(size, offset);
    }

    @Override
    public int bufferSize() {
        return this.buf.getSize();
    }

    @Override
    public boolean isDirect() {
        return this.buf.isDirect();
    }

    @Override
    public void freeBuffer() {
        this.buf.freeBuffer();
    }

    @Override
    public final void close() {
        freeBuffer();
    }
}
