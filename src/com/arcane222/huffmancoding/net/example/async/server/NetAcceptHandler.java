package com.arcane222.huffmancoding.net.example.async.server;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.*;

public class NetAcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel serverSocket;

    public NetAcceptHandler(AsynchronousServerSocketChannel serverSocket) {
        this.serverSocket = serverSocket;
    }

    @Override
    public void completed(AsynchronousSocketChannel asyncClientSocket, Void attachment) {
        try {
            connectionDump(asyncClientSocket.getLocalAddress(), asyncClientSocket.getRemoteAddress());

            // todo-register client socket channel

            // todo- re-accept next connection


        } catch(IOException e) {
            errorDump("", e);
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {

    }
}
