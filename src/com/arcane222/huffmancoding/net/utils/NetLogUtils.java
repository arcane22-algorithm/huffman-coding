package com.arcane222.huffmancoding.net.utils;

import java.net.SocketAddress;

public class NetLogUtils {

    public static void errorDump(String errorType, Throwable exc) {
        StringBuilder log = new StringBuilder();
        log.append("# Error type ");
        log.append(errorType);
        log.append(", Error Msg ");
        log.append('\n').append(Thread.currentThread());
        log.append(exc);
    }

    public static void connectionDump(SocketAddress localAddr, SocketAddress remoteAddr) {
        StringBuilder log = new StringBuilder();
        log.append("# Local Address: ");
        log.append(localAddr);
        log.append(", Remote Address: ");
        log.append('\n').append(Thread.currentThread());
        log.append(remoteAddr);

        System.out.println(log);
    }
}
