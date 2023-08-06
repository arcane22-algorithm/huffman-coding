package com.arcane222.huffmancoding.net.example.async.core.client;

import com.arcane222.huffmancoding.net.example.async.core.server.NetInstance;
import com.arcane222.huffmancoding.net.example.async.task.NetClientAcceptTask;
import com.arcane222.huffmancoding.net.example.async.util.ErrorType;
import com.arcane222.huffmancoding.net.example.async.util.ThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Optional;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;

public class NetAsyncSocketClient implements NetInstance {

    private static final int MIN_POOL_SIZE = 2;
    private static final int MAX_POOL_SIZE = 4;

    private final InetSocketAddress endpoint;
    private final ThreadPool threadPool;
    private final NetClientAcceptTask clientAcceptTask;

    private NetAsyncSocketClient(String host, int port) throws IOException {
        endpoint = new InetSocketAddress(host, port);
        threadPool = ThreadPool.ofCachedPool(MIN_POOL_SIZE, MAX_POOL_SIZE);
        clientAcceptTask = new NetClientAcceptTask(endpoint);
    }


    public static Optional<NetAsyncSocketClient> newInstance(String host, int port) {
        NetInstance.checkHost(host);
        NetInstance.checkPort(port);

        Optional<NetAsyncSocketClient> clientOp = Optional.empty();
        try {
            clientOp = Optional.of(new NetAsyncSocketClient(host, port));
        } catch (IOException e) {
            errorDump(ErrorType.AcceptConnectionError, e);
        }
        return clientOp;
    }

    @Override
    public void Start() {
        threadPool.execute(clientAcceptTask);
    }
}
