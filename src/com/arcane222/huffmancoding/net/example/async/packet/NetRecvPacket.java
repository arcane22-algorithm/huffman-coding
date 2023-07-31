package com.arcane222.huffmancoding.net.example.async.packet;

import java.nio.ByteBuffer;

public class NetRecvPacket<T> extends NetAbstractPacket<T, ByteBuffer> {

    public NetRecvPacket(T data, ByteBuffer buffer) {
        super(data, buffer);
    }

    @Override
    public void setData(T data) {

    }
}
