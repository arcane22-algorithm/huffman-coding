package com.arcane222.huffmancoding.net.example.async.data.buf.normal;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class UnsafeBufferImpl extends NormalBufferImpl {

    /* ---------- Static fields ---------- */

    // Unsafe 주소 값 관련 EMPTY_ADDRESS = 0L 지정에 대해 ...
    // 1) sun.misc.Unsafe 의 UNSAFE.allocateMemory 호출
    // 2) 내부에서 jdk.internal.misc.Unsafe 호출
    // 3) internalUnsafe 의 allocateMemory 의 경우 내부에서 JNI (allocateMemory0) 호출
    // 3-1) 할당 크기가 0 (argument 'bytes' is zero)일 경우 그냥 0을 리턴함
    // 3-2) 할당 하려하다가 메모리가 초과 될 경우 JNI call이 0을 리턴함, 이 경우 OutOfMemoryError 발생 (allocateMemory0(bytes) == 0)
    // 4) 메모리가 off-heap영역에 제대로 할당된 경우 long 타입의 메모리 주소 리턴
    // 즉, 비 할당 상태 상수 값을 0으로 지정해도 됨
    private static final long EMPTY_ADDRESS = 0L;
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

    /* ---------- Instance fields ---------- */

    private long address;
    private boolean modified;
    private byte[] tmp;

    protected UnsafeBufferImpl(int size) {
        super(size);
    }

    @Override
    protected void createBuffer(int size) {
        this.tmp = new byte[size];
        this.address = UNSAFE.allocateMemory(size);
    }

    @Override
    public byte getByte(int offset) {
        checkAddress();
        checkOffset(offset);

        return UNSAFE.getByte(address + offset);
    }

    @Override
    public void setByte(byte data, int offset) {
        checkAddress();
        checkOffset(offset);

        modified = true;
        UNSAFE.putByte(address + offset, data);
    }

    @Override
    public byte[] toArray() {
        checkAddress();

        if (modified) {
            UNSAFE.copyMemory(null, address, tmp, Unsafe.ARRAY_BYTE_BASE_OFFSET, size);
            modified = false;
        }
        return tmp;
    }

    @Override
    public void freeBuffer() {
        System.out.println("Free UnsafeBuffer, Address: " + address + ", Size: " + size);

        UNSAFE.freeMemory(address);
        address = EMPTY_ADDRESS;
        tmp = null;
    }

    private void checkAddress() {
        if (address == EMPTY_ADDRESS)
            throw new RuntimeException("Off-heap memory is not allocated");
    }

    private void checkOffset(int offset) {
        if (offset >= size)
            throw new IllegalArgumentException("Offset must be smaller then buffer size");
    }
}
