package com.arcane222.huffmancoding.net.example.async.task;

import java.nio.channels.CompletionHandler;

public class NetSendTask extends NetTaskImpl implements CompletionHandler<Integer, Void> {

    @Override
    public void run() {

    }

    @Override
    public void completed(Integer result, Void attachment) {

    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }
}
