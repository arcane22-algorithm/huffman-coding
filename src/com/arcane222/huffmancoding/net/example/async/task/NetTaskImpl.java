package com.arcane222.huffmancoding.net.example.async.task;

import java.util.UUID;

public abstract class NetTaskImpl implements NetTask {

    private final UUID taskId;

    public NetTaskImpl() {
        taskId = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "taskId=" + taskId +
                '}';
    }
}
