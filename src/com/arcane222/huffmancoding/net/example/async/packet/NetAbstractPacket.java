package com.arcane222.huffmancoding.net.example.async.packet;

import java.nio.ByteBuffer;

public abstract class NetAbstractPacket<T, B extends ByteBuffer> implements NetPacket<T> {

    private final T data;
    private final B buffer;

    public NetAbstractPacket(T data, B buffer) {
        this.data = data;
        this.buffer = buffer;
    }
}
