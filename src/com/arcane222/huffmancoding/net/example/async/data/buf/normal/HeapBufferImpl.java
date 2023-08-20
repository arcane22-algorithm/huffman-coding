package com.arcane222.huffmancoding.net.example.async.data.buf.normal;

import java.io.IOException;

public class HeapBufferImpl extends NormalBufferImpl {

    protected HeapBufferImpl(int size) {
        super(size);
    }

    @Override
    public byte getByte(int offset) {
        return super.getByte(offset);
    }

    @Override
    public byte[] getBytes(int size, int offset) {
        return super.getBytes(size, offset);
    }

    @Override
    public void setByte(byte data, int offset) {
        super.setByte(data, offset);
    }

    @Override
    public void setBytes(byte[] data, int size, int srcOffset, int destOffset) {
        super.setBytes(data, size, srcOffset, destOffset);
    }

    @Override
    public byte[] toArray() {
        return new byte[0];
    }

    @Override
    protected void createBuffer(int size) {

    }

    @Override
    public void freeBuffer() {

    }
}
