package com.arcane222.huffmancoding.net.example.async.data.packet;

import java.io.Serializable;

public interface NetPacket<T extends Serializable> {

    void setData(T data);
}
