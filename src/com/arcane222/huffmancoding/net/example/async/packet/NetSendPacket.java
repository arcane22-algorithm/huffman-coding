package com.arcane222.huffmancoding.net.example.async.packet;

import java.nio.ByteBuffer;

public class NetSendPacket<T> extends NetAbstractPacket<T, ByteBuffer> {

    public NetSendPacket(T data, ByteBuffer buffer) {
        super(data, buffer);
    }

    @Override
    public void setData(T data) {

    }
}
