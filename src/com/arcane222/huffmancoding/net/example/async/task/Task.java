package com.arcane222.huffmancoding.net.example.async.task;

import java.util.UUID;

public abstract class Task implements Runnable {

    private final UUID taskId;

    public Task() {
        taskId = UUID.randomUUID();
    }

    @Override
    public String toString() {
        return "taskId=" + taskId +
                '}';
    }
}
