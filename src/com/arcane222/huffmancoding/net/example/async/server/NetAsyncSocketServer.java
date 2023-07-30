package com.arcane222.huffmancoding.net.example.async.server;

import com.arcane222.huffmancoding.net.example.async.common.AcceptTask;
import com.arcane222.huffmancoding.net.example.async.common.AcceptWorker;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;
import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.serverStartDump;

public class NetAsyncSocketServer {

    private static final int MIN_PORT = 0x0000, MAX_PORT = 0xFFFF;

    private AsynchronousServerSocketChannel serverSocket;
    private final LinkedHashSet<AsynchronousSocketChannel> clientPool;

    private final AcceptTask acceptTask;
    private final AcceptWorker acceptWorker;

    private final InetSocketAddress endpoint;

    private NetAsyncSocketServer(int port) throws IOException {
        serverSocket = AsynchronousServerSocketChannel.open();
        clientPool = new LinkedHashSet<>();

        acceptTask = new AcceptTask(serverSocket, clientPool);
        acceptWorker = new AcceptWorker(acceptTask);

        endpoint = new InetSocketAddress(port);
    }

    public static Optional<NetAsyncSocketServer> newInstance (int port) {
        if(port < MIN_PORT || port > MAX_PORT)
            throw new IllegalArgumentException("Port number must be 0 <= port <= 65535.");

        Optional<NetAsyncSocketServer> instanceOp = Optional.empty();
        try {
            instanceOp = Optional.of(new NetAsyncSocketServer(port));
        } catch(IOException e) {
            errorDump("", e);
        }

        return instanceOp;
    }

    public void start() {
        try {
            // bind endpoint & start accept worker thread
            serverSocket.bind(endpoint);
            acceptWorker.start();

            // logging
            serverStartDump(endpoint);
        } catch (IOException e) {
            errorDump("", e);
        }
    }


    @Override
    public String toString() {
        return "NetAsyncSocketServer{" +
                "endpoint=" + endpoint +
                '}';
    }
}
