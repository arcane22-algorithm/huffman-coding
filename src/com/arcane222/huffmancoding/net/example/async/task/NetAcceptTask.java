package com.arcane222.huffmancoding.net.example.async.task;

import com.arcane222.huffmancoding.net.example.async.server.NetClientSocketWrapper;

import java.io.IOException;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.LinkedHashSet;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.connectionDump;
import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;

public class NetAcceptTask extends Task implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel serverSocket;
    private final LinkedHashSet<NetClientSocketWrapper> clientPool;

    public NetAcceptTask(AsynchronousServerSocketChannel serverSocket,
                         LinkedHashSet<NetClientSocketWrapper> clientPool) {
        this.serverSocket = serverSocket;
        this.clientPool = clientPool;
    }

    @Override
    public void run() {
        if(serverSocket.isOpen()) {
            serverSocket.accept(null, this);
        }
    }

    @Override
    public void completed(AsynchronousSocketChannel clientSocket, Void attachment) {
        try {
            connectionDump(clientSocket.getLocalAddress(), clientSocket.getRemoteAddress());
            clientPool.add(new NetClientSocketWrapper(clientSocket));
            serverSocket.accept(null, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        errorDump("AcceptError", exc);
    }
}
