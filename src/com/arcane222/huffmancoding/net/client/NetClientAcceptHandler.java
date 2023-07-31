package com.arcane222.huffmancoding.net.client;

import com.arcane222.huffmancoding.net.utils.NetLogUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class NetClientAcceptHandler implements CompletionHandler<Void, Object> {

    private static final InetSocketAddress Endpoint = new InetSocketAddress("127.0.0.1", 7000);

    private AsynchronousSocketChannel asyncClient;

    public NetClientAcceptHandler(AsynchronousSocketChannel asyncClient) {
        this.asyncClient = asyncClient;
    }

    @Override
    public void completed(Void result, Object attachment) {
        try {
            NetLogUtils.connectionDump(asyncClient.getLocalAddress(), asyncClient.getRemoteAddress());
        } catch (IOException e) {
            NetLogUtils.errorDump("Address Error", e);
        }
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        NetLogUtils.errorDump("[Fail to connect server]", exc);

        if (!asyncClient.isOpen()) {
            try {
                asyncClient = AsynchronousSocketChannel.open();
                asyncClient.connect(Endpoint, null, this);
            } catch (IOException e) {
                NetLogUtils.errorDump("", e);
            }
        }
    }
}