package com.arcane222.huffmancoding.net.server;

import java.io.IOException;

public class NetServerMain {

    public static void main(String[] args) throws IOException {
        NetAsyncServer server = NetAsyncServer.newInstance(7000);
        server.run();
    }
}
