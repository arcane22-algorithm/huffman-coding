package com.arcane222.huffmancoding.net.example.async.task;

import com.arcane222.huffmancoding.net.example.async.server.NetClientSocketPool;
import com.arcane222.huffmancoding.net.example.async.server.NetClientSocketWrapper;
import com.arcane222.huffmancoding.net.example.async.util.ErrorType;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.ReadPendingException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.connectionDump;
import static com.arcane222.huffmancoding.net.example.async.util.NetLogUtil.errorDump;

public class NetServerAcceptTask extends Task implements CompletionHandler<AsynchronousSocketChannel, Void> {

    private final AsynchronousServerSocketChannel serverSocket;
    private final NetClientSocketPool clientPool;

    public NetServerAcceptTask(AsynchronousServerSocketChannel serverSocket,
                               NetClientSocketPool clientPool) {
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
            // print log
            connectionDump(clientSocket.getLocalAddress(), clientSocket.getRemoteAddress());
            // add client to pool
            clientPool.addClient(new NetClientSocketWrapper(clientSocket));
            // prepare again to accept new client

            ByteBuffer buf = ByteBuffer.allocate(4);
            ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
            poolExecutor.scheduleAtFixedRate(() -> {
                buf.clear();
                try {
                    clientSocket.read(buf, null, new CompletionHandler<>() {
                        @Override
                        public void completed(Integer result, Object attachment) {
                            buf.flip();
                            System.out.println("Recv data is: " + buf.getInt());
                        }
                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            errorDump(ErrorType.ReadDataError, exc);
                        }
                    });
                } catch(ReadPendingException e) {
                    errorDump(ErrorType.ReadDataError, e);
                }
            }, 0, 500, TimeUnit.MILLISECONDS);

            serverSocket.accept(null, this);
        } catch (IOException e) {
            errorDump(ErrorType.AcceptTaskCompleteError, e);
        }
    }

    @Override
    public void failed(Throwable exc, Void attachment) {
        errorDump(ErrorType.AcceptTaskFailError, exc);
    }
}
