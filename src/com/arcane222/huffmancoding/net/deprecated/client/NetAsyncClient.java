package com.arcane222.huffmancoding.net.deprecated.client;

import com.arcane222.huffmancoding.net.deprecated.common.NetAbstractInstance;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;

public class NetAsyncClient extends NetAbstractInstance {

    public static void main(String[] args) throws IOException {
        AsynchronousSocketChannel asyncClient = AsynchronousSocketChannel.open();

        NetClientAcceptHandler acceptCompletionHandler = new NetClientAcceptHandler(asyncClient);
        asyncClient.connect(new InetSocketAddress("127.0.0.1", 7000), null, acceptCompletionHandler);

        System.in.read();
    }
}
