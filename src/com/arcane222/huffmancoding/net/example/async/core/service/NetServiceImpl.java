package com.arcane222.huffmancoding.net.example.async.core.service;

import com.arcane222.huffmancoding.net.example.async.core.client.NetAsyncSocketClient;
import com.arcane222.huffmancoding.net.example.async.core.server.NetAsyncSocketServer;
import com.arcane222.huffmancoding.net.example.async.core.server.NetInstance;

public class NetServiceImpl implements NetService {

    private final NetInstance netInstance;

    protected NetServiceImpl(NetInstance netInstance) {
        this.netInstance = netInstance;
    }

    public static NetService createService(NetConfig config) {
        NetInstance netInstance = null;

        switch (config.getServiceType()) {
            case SERVER: {
                netInstance = NetAsyncSocketServer.newInstance(config.getPort())
                        .orElseThrow(() -> new RuntimeException());
            }
            break;

            case CLIENT: {
                netInstance = NetAsyncSocketClient.newInstance(config.getHost(), config.getPort())
                        .orElseThrow(() -> new RuntimeException());
            }
            break;
        }

        return new NetServiceImpl(netInstance);
    }

    @Override
    public void start() {
        netInstance.Start();
    }
}
