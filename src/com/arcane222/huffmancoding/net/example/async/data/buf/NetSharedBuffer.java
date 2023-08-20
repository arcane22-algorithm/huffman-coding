package com.arcane222.huffmancoding.net.example.async.data.buf;


import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBufferType;


public class NetSharedBuffer extends NetBufferImpl {

    public NetSharedBuffer(NormalBufferType type, int size) {
        super(type, size);
    }

    @Override
    public void allocateData(byte data, int offset) {
        this.write(data, offset);
    }

    @Override
    public void allocateData(byte[] data, int size, int srcOffset, int destOffset) {
        this.writeAll(data, size, srcOffset, destOffset);
    }

    @Override
    public byte[] deallocateData(int offset, int len) {
        byte[] data = new byte[len];
        for (int i = 0; i < len; i++) {
            int idx = offset + i;
            data[i] = this.read(idx);
            this.write((byte) 0x00, idx);
        }

        return data;
    }

    @Override
    public void freeBuffer() {
        // do data calculate logic

        // free real buffer
        super.freeBuffer();
    }
}
