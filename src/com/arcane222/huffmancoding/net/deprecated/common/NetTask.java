package com.arcane222.huffmancoding.net.deprecated.common;

import java.nio.ByteBuffer;
import java.nio.channels.NetworkChannel;
import java.util.UUID;

public abstract class NetTask implements Runnable {

    private final UUID taskId;

    private NetworkChannel netResource;
    private ByteBuffer buff;

    private boolean sendOnly;
    private boolean recvOnly;

    public NetTask() {
        taskId = UUID.randomUUID();
    }

    public NetTask(int buffSize, boolean isDirect) {
        this();
        this.buff = createBuffer(buffSize, isDirect);
    }

    public NetTask(int bufferSize, boolean isDirect, boolean sendOnly) {
        this(bufferSize, isDirect);

        if (sendOnly) this.sendOnly = true;
        else this.recvOnly = true;
    }

    private ByteBuffer createBuffer(int bufferSize, boolean isDirect) {
        return isDirect ? ByteBuffer.allocateDirect(bufferSize)
                : ByteBuffer.allocate(bufferSize);
    }

    @Override
    public String toString() {
        return "NetTask{" +
                "taskId=" + taskId +
                '}';
    }
}
