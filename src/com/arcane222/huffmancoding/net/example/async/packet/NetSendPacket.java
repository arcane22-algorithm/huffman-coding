package com.arcane222.huffmancoding.net.example.async.packet;

import java.io.Serializable;

public class NetSendPacket<T extends Serializable> extends NetPacketImpl<T> {

    public static final int DEFAULT_DEST_ID = -1;

    private final int destId = DEFAULT_DEST_ID;

    protected NetSendPacket(T data) {
        super(data);
    }

    public int getDestId() {
        return destId;
    }
}
