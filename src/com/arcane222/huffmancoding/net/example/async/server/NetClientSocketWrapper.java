package com.arcane222.huffmancoding.net.example.async.server;

import com.arcane222.huffmancoding.net.example.async.packet.NetPacket;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;
import java.util.concurrent.atomic.AtomicInteger;

public class NetClientSocketWrapper {
    private static final AtomicInteger counter = new AtomicInteger(0);

    private final int clientId;
    private final AsynchronousSocketChannel clientSocket;

    public NetClientSocketWrapper(AsynchronousSocketChannel clientSocket) {
        this.clientId = counter.getAndIncrement();
        this.clientSocket = clientSocket;
    }

    public boolean isOpen() {
        return clientSocket.isOpen();
    }

    public void disconnect() throws IOException {
        if(clientSocket.isOpen())
            clientSocket.close();
    }

    public <T> void sendData(NetPacket<T> packet) {

    }

    public final int getClientId() {
        return clientId;
    }
}
