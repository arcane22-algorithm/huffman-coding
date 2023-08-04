package com.arcane222.huffmancoding.net.example.async.server;

public interface NetInstance {

    /* Constant */
    int MIN_PORT = 0x0000, MAX_PORT = 0xFFFF;
    int MIN_POOL_SIZE = 0x01, MAX_POOL_SIZE = 0xFF;

    /* Instance method */
    void Start();

    /* Static method */
    static void checkHost(String host) {
        if(host == null || host.isEmpty())
            throw new IllegalArgumentException("Host is null or empty.");
    }

    static void checkPort(int port) {
        if (port < MIN_PORT || port > MAX_PORT)
            throw new IllegalArgumentException("Port number must be 0 <= port <= 65535.");
    }

    static void checkPoolSize(int poolSize) {
        if(poolSize < MIN_POOL_SIZE || poolSize > MAX_POOL_SIZE)
            throw new IllegalArgumentException("Thread-pool size must be 1 <= size <= 256");
    }
}
