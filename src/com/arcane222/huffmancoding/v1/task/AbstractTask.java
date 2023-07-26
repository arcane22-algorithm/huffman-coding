package com.arcane222.huffmancoding.v1.task;

import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Supplier;

public abstract class AbstractTask<T> implements Supplier<T> {

    private final UUID taskId;

    public AbstractTask() {
        this.taskId = UUID.randomUUID();
    }

    public UUID getTaskId() {
        return taskId;
    }

    public static void dumpExecTime() {

    }
}
