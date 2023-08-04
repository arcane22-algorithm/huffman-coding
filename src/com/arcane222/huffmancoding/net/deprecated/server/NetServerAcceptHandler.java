package com.arcane222.huffmancoding.net.deprecated.server;

import com.arcane222.huffmancoding.net.deprecated.utils.NetLogUtils;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class NetServerAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel asyncServer;

    public NetServerAcceptHandler(AsynchronousServerSocketChannel asyncServer) {
        this.asyncServer = asyncServer;
    }

    @Override
    public void completed(AsynchronousSocketChannel result, Void attachment) {
        try {
            NetLogUtils.connectionDump(result.getLocalAddress(), result.getRemoteAddress());
            asyncServer.accept(null, this);
        } catch (IOException e) {
            NetLogUtils.errorDump("", e);
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        NetLogUtils.errorDump("Client Connection Fail", exc);
    }
}
