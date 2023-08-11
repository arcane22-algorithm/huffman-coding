package com.arcane222.huffmancoding.net.example.async.task;

import java.nio.channels.CompletionHandler;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;

public class NetAcceptTask extends CallbackTask {

    private final Executor executor;

    public NetAcceptTask(Runnable task, CompletionHandler<Void, Object> callback, Executor executor) {
        this.executor = executor;
    }

    public void execute() {
    }
}
