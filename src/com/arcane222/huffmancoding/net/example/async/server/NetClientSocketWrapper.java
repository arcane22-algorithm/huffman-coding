package com.arcane222.huffmancoding.net.example.async.server;

import com.arcane222.huffmancoding.net.example.async.packet.NetPacket;

import java.io.IOException;
import java.nio.channels.AsynchronousSocketChannel;

public class NetClientSocketWrapper {

    private final AsynchronousSocketChannel clientSocket;

    public NetClientSocketWrapper(AsynchronousSocketChannel clientSocket) {
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
}
