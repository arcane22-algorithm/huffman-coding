package com.arcane222.huffmancoding.net.example.async.common;

import com.arcane222.huffmancoding.net.example.async.server.NetAcceptHandler;

import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.LinkedHashSet;
import java.util.concurrent.*;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;

public class AcceptTask extends Task {
    private static final int MAX_ACCEPT_TIMEOUT = 2;

    private final LinkedHashSet<AsynchronousSocketChannel> clientPool;
    private final AsynchronousServerSocketChannel serverSocket;

    public AcceptTask(AsynchronousServerSocketChannel serverSocket,
                      LinkedHashSet<AsynchronousSocketChannel> clientPool) {
        this.serverSocket = serverSocket;
        this.clientPool = clientPool;
    }

    @Override
    public void run() {
        while(serverSocket.isOpen()) {
            try {
                AsynchronousSocketChannel clientSocket = serverSocket.accept()
                        .get(MAX_ACCEPT_TIMEOUT, TimeUnit.SECONDS);

                this.clientPool.add(clientSocket);
            } catch (InterruptedException | ExecutionException | TimeoutException e) {
                errorDump("", e);
            }
        }
    }
}
