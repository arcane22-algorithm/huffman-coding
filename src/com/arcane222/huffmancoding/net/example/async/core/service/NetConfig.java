package com.arcane222.huffmancoding.net.example.async.core.service;

import com.arcane222.huffmancoding.net.example.async.util.exception.ProgramArgumentEmptyException;


public class NetConfig {

    public static final NetConfig EMPTY = new NetConfig(null, null, 0);

    private final ServiceType serviceType;
    private final String host;
    private final int port;

    private NetConfig(ServiceType serviceType, String host, int port) {
        this.serviceType = serviceType;
        this.host = host;
        this.port = port;
    }

    private NetConfig(ServiceType serviceType, int port) {
        this(serviceType, "", port);
    }

    public static NetConfig createConfig(String... args) {
        if (args.length == 0) {
            throw new ProgramArgumentEmptyException("Enter program argument, [server/client] [host-client only] [port], ex) client 127.0.0.1 7000");
        }

        String typeStr = args[0].toUpperCase();
        ServiceType type = ServiceType.valueOf(typeStr);
        switch (type) {
            case SERVER: {
                int port = Integer.parseInt(args[1]);
                return new NetConfig(type, port);
            }

            case CLIENT: {
                String host = args[1];
                int port = Integer.parseInt(args[2]);
                return new NetConfig(type, host, port);
            }

            default: {
                return NetConfig.EMPTY;
            }
        }
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }
}
