package com.arcane222.huffmancoding.net.server;

import com.arcane222.huffmancoding.net.common.NetTask;
import com.arcane222.huffmancoding.net.example.async.util.ThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.Map;
import java.util.WeakHashMap;

public class NetAsyncServer {
    private static final int MIN_PORT_NUM = 0;
    private static final int MAX_PORT_NUM = (1 << 16) - 1;

    private ThreadPool pool;
    private Map<String, AsynchronousSocketChannel> clientCache;

    private AsynchronousServerSocketChannel server;

    private static class AcceptTask extends NetTask {

        @Override
        public void run() {
            AsynchronousServerSocketChannel server = null;
            try {
                server = AsynchronousServerSocketChannel.open();
                server.bind(new InetSocketAddress(7000));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            NetServerAcceptHandler acceptCompletionHandler = new NetServerAcceptHandler(server);
            server.accept(null, acceptCompletionHandler);
        }
    }

    private NetAsyncServer(int port) throws IOException {
        this.clientCache = new WeakHashMap<>();
        this.pool = ThreadPool.ofCachedPool(5, 200);
    }

    public static NetAsyncServer newInstance(int port) throws IOException {
        if (port < MIN_PORT_NUM || port > MAX_PORT_NUM)
            throw new IllegalArgumentException("Port number out of range");

        return new NetAsyncServer(port);
    }

    public void run() {
        this.pool.execute(new AcceptTask());
    }
}
