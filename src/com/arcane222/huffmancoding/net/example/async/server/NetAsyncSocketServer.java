package com.arcane222.huffmancoding.net.example.async.server;

import com.arcane222.huffmancoding.net.example.async.packet.NetPacket;
import com.arcane222.huffmancoding.net.example.async.task.NetAcceptTask;
import com.arcane222.huffmancoding.net.example.async.task.NetAcceptWorker;
import com.arcane222.huffmancoding.net.example.async.util.ErrorType;
import com.arcane222.huffmancoding.net.example.async.util.ThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.util.*;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;
import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.serverStartDump;

public class NetAsyncSocketServer {

    /* Constant */
    private static final int MIN_PORT = 0x0000, MAX_PORT = 0xFFFF;
    private static final int MIN_POOL_SIZE = 0x01, MAX_POOL_SIZE = 0xFF;
    private static final int DEFAULT_POOL_SIZE = (Runtime.getRuntime().availableProcessors() << 1);

    /* Instance variables */
    private final InetSocketAddress endpoint;
    private final ThreadPool threadPool;
    private final NetClientSocketPool clientPool;


    private final NetAcceptTask acceptTask;
    private final NetAcceptWorker acceptWorker;
    private final AsynchronousServerSocketChannel serverSocket;


    /* Private constructors */

    private NetAsyncSocketServer(int port) throws IOException {
        this(port, DEFAULT_POOL_SIZE);
    }

    private NetAsyncSocketServer(int port, int poolSize) throws IOException {
        endpoint = new InetSocketAddress(port);
        threadPool = ThreadPool.ofFixedPool(poolSize);
        clientPool = new NetClientSocketPool(true);

        serverSocket = AsynchronousServerSocketChannel.open();
        acceptTask = new NetAcceptTask(serverSocket, clientPool);
        acceptWorker = new NetAcceptWorker(acceptTask);
    }

    public static Optional<NetAsyncSocketServer> newInstance(int port) {
        return newInstance(port, DEFAULT_POOL_SIZE);
    }

    public static Optional<NetAsyncSocketServer> newInstance(int port, int poolSize) {
        checkPort(port);
        checkPoolSize(poolSize);

        Optional<NetAsyncSocketServer> instanceOp = Optional.empty();
        try {
            instanceOp = Optional.of(new NetAsyncSocketServer(port));
        } catch (IOException e) {
            errorDump(ErrorType.CreateServerInstanceError, e);
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
            errorDump(ErrorType.StartServerError, e);
        }
    }

    public <T> void send(int clientId, NetPacket<T> sendPacket) {
        clientPool.getClient(clientId).sendData(sendPacket);
    }

    public <T> void broadcast(NetPacket<T> sendPacket) {
        List<NetClientSocketWrapper> clients = clientPool.getClients();
        for (var clientSocket : clients) {
            clientSocket.sendData(sendPacket);
        }
    }

    public void terminate() throws IOException {
        List<NetClientSocketWrapper> clients = clientPool.getClients();
        for (var clientSocket : clients) {
            clientSocket.disconnect();
        }
    }

    static void checkPort(int port) {
        if (port < MIN_PORT || port > MAX_PORT)
            throw new IllegalArgumentException("Port number must be 0 <= port <= 65535.");
    }

    static void checkPoolSize(int poolSize) {
        if(poolSize < MIN_POOL_SIZE || poolSize > MAX_POOL_SIZE)
            throw new IllegalArgumentException("Thread-pool size must be 1 <= size <= 256");
    }


    @Override
    public String toString() {
        return "NetAsyncSocketServer{" +
                "endpoint=" + endpoint +
                '}';
    }
}
