package com.arcane222.huffmancoding.net.example.async.data;

import java.nio.ByteBuffer;

public class NetSharedBuffer implements NetBuffer {

    private final ByteBuffer sendBuffer;
    private final ByteBuffer recvBuffer;

    public NetSharedBuffer(int sendBufferSize, int recvBufferSize, boolean isDirect) {
        sendBuffer = isDirect ? ByteBuffer.allocateDirect(sendBufferSize)
                : ByteBuffer.allocate(sendBufferSize);
        recvBuffer = isDirect ? ByteBuffer.allocateDirect(recvBufferSize)
                : ByteBuffer.allocate(recvBufferSize);
    }
}
