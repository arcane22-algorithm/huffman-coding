package com.arcane222.huffmancoding.net.example.async.server;

import java.nio.channels.CompletionHandler;

public class NetSendHandler implements CompletionHandler<Void, Void> {

    @Override
    public void completed(Void result, Void attachment) {

    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }
}
