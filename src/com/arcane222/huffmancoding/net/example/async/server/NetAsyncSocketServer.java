package com.arcane222.huffmancoding.net.example.async.server;

import com.arcane222.huffmancoding.net.example.async.packet.NetPacket;
import com.arcane222.huffmancoding.net.example.async.task.NetAcceptTask;
import com.arcane222.huffmancoding.net.example.async.task.NetAcceptWorker;
import com.arcane222.huffmancoding.net.example.async.util.ThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;
import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.serverStartDump;

public class NetAsyncSocketServer {

    private static final int MIN_PORT = 0x0000, MAX_PORT = 0xFFFF;

    private final InetSocketAddress endpoint;
    private final AsynchronousServerSocketChannel serverSocket;
    private final LinkedHashSet<NetClientSocketWrapper> clientPool;

    private final NetAcceptTask acceptTask;
    private final NetAcceptWorker acceptWorker;
    private final ThreadPool threadPool;

    private NetAsyncSocketServer(int port) throws IOException {
        endpoint = new InetSocketAddress(port);
        serverSocket = AsynchronousServerSocketChannel.open();
        clientPool = new LinkedHashSet<>();

        acceptTask = new NetAcceptTask(serverSocket, clientPool);
        acceptWorker = new NetAcceptWorker(acceptTask);
        threadPool = ThreadPool.ofFixedPool((Runtime.getRuntime().availableProcessors() << 1));
    }

    public static Optional<NetAsyncSocketServer> newInstance(int port) {
        if (port < MIN_PORT || port > MAX_PORT)
            throw new IllegalArgumentException("Port number must be 0 <= port <= 65535.");

        Optional<NetAsyncSocketServer> instanceOp = Optional.empty();
        try {
            instanceOp = Optional.of(new NetAsyncSocketServer(port));
        } catch (IOException e) {
            errorDump("", e);
        }

        return instanceOp;
    }

    public void start() {
        try {
            // bind endpoint
            serverSocket.bind(endpoint);
            // start accept worker thread
            threadPool.execute(acceptTask);
            // logging
            serverStartDump(endpoint);
        } catch (IOException e) {
            errorDump("", e);
        }
    }

    public <T> void broadcast(NetPacket<T> sendPacket) {
        for (var clientSocket : clientPool) {
            clientSocket.sendData(sendPacket);
        }
    }

    public void terminate() throws IOException {
        for (var clientSocket : clientPool) {
            clientSocket.disconnect();
        }
    }


    @Override
    public String toString() {
        return "NetAsyncSocketServer{" +
                "endpoint=" + endpoint +
                '}';
    }
}
