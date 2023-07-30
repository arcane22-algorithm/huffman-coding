package com.arcane222.huffmancoding.net.example.async.util;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class NetLogUtil {

    public static void errorDump(String errorType, Throwable exc) {
        StringBuilder log = new StringBuilder();
        log.append("# Thread=").append(Thread.currentThread());
        log.append(", Error type=").append(errorType);
        log.append(", Error message=").append(exc.getMessage());
        log.append(exc);

        System.out.println(log);
    }

    public static void serverStartDump(InetSocketAddress endpoint) {
        StringBuilder log = new StringBuilder();
        log.append("# AsyncSocketServer is running on ");
        log.append("host=").append(endpoint.getHostName());
        log.append(", port=").append(endpoint.getPort());

        System.out.println(log);
    }

    public static void connectionDump(SocketAddress localAddr, SocketAddress remoteAddr) {
        StringBuilder log = new StringBuilder();
        log.append("# Local Address=");
        log.append(localAddr);
        log.append(", Remote Address=");
        log.append('\n').append(Thread.currentThread());
        log.append(remoteAddr);

        System.out.println(log);
    }
}
