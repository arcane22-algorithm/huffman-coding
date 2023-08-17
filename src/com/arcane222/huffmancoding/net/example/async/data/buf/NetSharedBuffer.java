package com.arcane222.huffmancoding.net.example.async.data.buf;

import sun.misc.Unsafe;

import java.io.IOException;
import java.lang.reflect.Field;

public class NetSharedBuffer extends NetBufferImpl {

    private static final Unsafe UNSAFE;

    static {
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            UNSAFE = (Unsafe) unsafeField.get(null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ExceptionInInitializerError(" - Get Unsafe Error: " + e.getMessage());
        }
    }

    public NetSharedBuffer(int bufSize, boolean isDirect) {
        super(bufSize, isDirect);

        // test code
        long address = UNSAFE.allocateMemory(4);
        UNSAFE.freeMemory(address);
    }

    @Override
    public void allocateData(byte[] data) {

    }

    @Override
    public void deallocateData(int offset, int data) {

    }

    @Override
    public void freeBuffer() {

    }
}
