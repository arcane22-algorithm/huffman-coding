package com.arcane222.huffmancoding.net.deprecated;

import com.arcane222.huffmancoding.net.example.async.util.ThreadPool;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutionException;

public class NetServer {

    private static final int DEFAULT_POOL_SIZE = 10;
    private static final int MAX_POOL_SIZE = 50;
    private static final Object dummy = new Object();

    private final ThreadPool acceptor;
    private final ThreadPool processor;

    private final AsynchronousServerSocketChannel asyncServerChannel;
    private final Map<AsynchronousSocketChannel, Object> channels;

    private boolean initialized;
    private InetSocketAddress hostAddress;

    private static class AcceptHandler implements CompletionHandler<AsynchronousSocketChannel, Void> {

        private final Map<AsynchronousSocketChannel, Object> channels;

        public AcceptHandler(Map<AsynchronousSocketChannel, Object> channels) {
            this.channels = channels;
        }

        @Override
        public void completed(AsynchronousSocketChannel result, Void attachment) {
            try {
                System.out.println(Thread.currentThread());
                System.out.println("Success to connect: " + result.getRemoteAddress().toString());
                channels.put(result, dummy);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        @Override
        public void failed(Throwable exc, Void attachment) {
            System.out.println("Fail to connect: " + exc.getMessage());
        }
    }

    private static class SendHandler implements CompletionHandler<Integer, Void> {

        @Override
        public void completed(Integer result, Void attachment) {

        }

        @Override
        public void failed(Throwable exc, Void attachment) {

        }
    }

    private static class BroadcastTask implements Runnable {

        private final Map<AsynchronousSocketChannel, Object> channels;
        private final ByteBuffer data;

        private BroadcastTask(Map<AsynchronousSocketChannel, Object> channels, ByteBuffer data) {
            this.channels = channels;
            this.data = data;
        }

        public static BroadcastTask of(Map<AsynchronousSocketChannel, Object> channels, ByteBuffer data) {
            return new BroadcastTask(channels, data);
        }

        @Override
        public void run() {
            for (AsynchronousSocketChannel channel : channels.keySet()) {
                if (channel.isOpen()) {
                    data.flip();
                    channel.write(data, null, new SendHandler());
                }
            }
        }
    }

    private NetServer(String host, int port) throws IOException {
        this.hostAddress = host == null ? new InetSocketAddress(port) : new InetSocketAddress(host, port);
        this.acceptor = ThreadPool.ofDefaultPool();
        this.processor = ThreadPool.ofCachedPool(DEFAULT_POOL_SIZE, MAX_POOL_SIZE);

        this.asyncServerChannel = AsynchronousServerSocketChannel.open();
        this.channels = new WeakHashMap<>();
    }

    public static NetServer newInstance(int port) throws IOException {
        return new NetServer(null, port);
    }

    public static NetServer newInstance(String host, int port) throws IOException {
        return new NetServer(host, port);
    }


    public void init() throws IOException, ExecutionException, InterruptedException {
        if (initialized)
            throw new IllegalStateException("Server Already initialized");

        this.initialized = true;
        this.asyncServerChannel.bind(hostAddress);
    }

    public void sendAsync(Integer socketId, ByteBuffer data) {
        if (data.position() > 0)
            data.flip();

        
    }

    public void broadcastAsync(ByteBuffer data) {

    }
}
