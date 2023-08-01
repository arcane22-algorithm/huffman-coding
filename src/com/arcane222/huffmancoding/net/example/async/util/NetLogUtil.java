package com.arcane222.huffmancoding.net.example.async.util;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class NetLogUtil {

    public static void errorDump(ErrorType errorType, Throwable exc) {
        StringBuilder log = new StringBuilder();
        log.append("# ThreadInfo=").append(Thread.currentThread());
        log.append(", ErrorType=").append(errorType.getValue());
        log.append(", ErrorMessage=").append(exc.getMessage());
        log.append(exc);

        System.out.println(log);
    }

    public static void serverStartDump(InetSocketAddress endpoint) {
        StringBuilder log = new StringBuilder();
        log.append("# ThreadInfo=").append(Thread.currentThread());
        log.append(", AsyncSocketServer is running on");
        log.append(" Host=").append(endpoint.getHostName());
        log.append(", Port=").append(endpoint.getPort());

        System.out.println(log);
    }

    public static void connectionDump(SocketAddress localAddr, SocketAddress remoteAddr) {
        StringBuilder log = new StringBuilder();
        log.append("# ThreadInfo=").append(Thread.currentThread());
        log.append(", LocalAddress=").append(localAddr);
        log.append(", RemoteAddress=").append(remoteAddr);
        log.append(remoteAddr);

        System.out.println(log);
    }
}
