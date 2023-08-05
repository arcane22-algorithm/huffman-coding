package com.arcane222.huffmancoding.net.example.async.packet;

import java.io.Serializable;

public abstract class NetPacketImpl<T extends Serializable> implements NetPacket<T> {

    private final T data;

    protected NetPacketImpl(T data) {
        this.data = data;
    }


    @Override
    public void setData(T data) {

    }
}
