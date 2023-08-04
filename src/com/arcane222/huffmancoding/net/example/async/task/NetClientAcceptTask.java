package com.arcane222.huffmancoding.net.example.async.task;

import com.arcane222.huffmancoding.net.deprecated.utils.NetLogUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.WritePendingException;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetClientAcceptTask extends Task implements CompletionHandler<Void, Object> {

    private final InetSocketAddress endpoint;
    private AsynchronousSocketChannel clientSocket;

    public NetClientAcceptTask(InetSocketAddress endpoint) {
        this.endpoint = endpoint;
    }

    @Override
    public void run() {
        try {
            clientSocket = AsynchronousSocketChannel.open();
            clientSocket.connect(endpoint, null, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void completed(Void result, Object attachment) {
        try {
            NetLogUtils.connectionDump(clientSocket.getLocalAddress(), clientSocket.getRemoteAddress());
            ByteBuffer buf = ByteBuffer.allocate(4);

            ScheduledThreadPoolExecutor poolExecutor = new ScheduledThreadPoolExecutor(1);
            poolExecutor.scheduleAtFixedRate(() -> {
                try {
                    buf.clear();
                    int data = (int) (Math.random() * 0x100);
                    buf.putInt(data);
                    buf.flip();

                    clientSocket.write(buf);
                    System.out.println("Send data is: " + data);
                } catch (WritePendingException e) {
                    NetLogUtils.errorDump("Write Error", e);
                }
            }, 0, 1, TimeUnit.SECONDS);

        } catch (IOException e) {
            NetLogUtils.errorDump("Address Error", e);
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        NetLogUtils.errorDump("[Fail to connect server]", exc);

        if (!clientSocket.isOpen()) {
            try {
                clientSocket = AsynchronousSocketChannel.open();
                clientSocket.connect(endpoint, null, this);
            } catch (IOException e) {
                NetLogUtils.errorDump("", e);
            }
        }
    }
}
