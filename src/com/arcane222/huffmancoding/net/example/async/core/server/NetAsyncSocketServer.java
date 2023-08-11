package com.arcane222.huffmancoding.net.example.async.core.server;

import com.arcane222.huffmancoding.net.example.async.data.packet.NetPacket;
import com.arcane222.huffmancoding.net.example.async.task.NetAcceptTask;
import com.arcane222.huffmancoding.net.example.async.task.NetServerAcceptTask;
import com.arcane222.huffmancoding.net.example.async.task.worker.NetAcceptWorker;
import com.arcane222.huffmancoding.net.example.async.util.ErrorType;
import com.arcane222.huffmancoding.net.example.async.util.ThreadPool;

import java.io.IOException;
import java.io.Serializable;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.*;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;
import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.serverStartDump;

public class NetAsyncSocketServer implements NetInstance {

    private static final int DEFAULT_POOL_SIZE = (Runtime.getRuntime().availableProcessors() << 1);

    /* Instance variables */
    private final InetSocketAddress endpoint;
    private final ThreadPool threadPool;
    private final NetClientSocketPool clientPool;


    private final NetServerAcceptTask acceptTask;

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
        acceptTask = new NetServerAcceptTask(serverSocket, clientPool);
        acceptWorker = new NetAcceptWorker(acceptTask);
    }

    public static Optional<NetAsyncSocketServer> newInstance(int port) {
        return newInstance(port, DEFAULT_POOL_SIZE);
    }

    public static Optional<NetAsyncSocketServer> newInstance(int port, int poolSize) {
        NetInstance.checkPort(port);
        NetInstance.checkPoolSize(poolSize);

        Optional<NetAsyncSocketServer> serverOp = Optional.empty();
        try {
            serverOp = Optional.of(new NetAsyncSocketServer(port));
        } catch (IOException e) {
            errorDump(ErrorType.CreateServerInstanceError, e);
        }

        return serverOp;
    }

    public <T extends Serializable> void send(int clientId, NetPacket<T> sendPacket) {
        clientPool.getClient(clientId).sendData(sendPacket);
    }

    public <T extends Serializable> void broadcast(NetPacket<T> sendPacket) {
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

    @Override
    public void Start() {
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

    @Override
    public String toString() {
        return "NetAsyncSocketServer{" +
                "endpoint=" + endpoint +
                '}';
    }
}
