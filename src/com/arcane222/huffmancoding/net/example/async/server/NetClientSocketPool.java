package com.arcane222.huffmancoding.net.example.async.server;

import com.arcane222.huffmancoding.net.example.async.exception.OperationFailException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class NetClientSocketPool extends AbstractUniquePool<Integer, NetClientSocketWrapper> {
    private final AtomicInteger clientCount;

    private List<NetClientSocketWrapper> clientSocketListCache;

    public NetClientSocketPool(boolean concurrency) {
        super(concurrency);
        this.clientCount = new AtomicInteger(0);
    }

    public void addClient(NetClientSocketWrapper clientSocket) {
        if (!this.add(clientSocket.getClientId(), clientSocket))
            throw new OperationFailException("AddClientSocket");
    }

    public NetClientSocketWrapper getClient(Integer clientId) {
        return this.get(clientId)
                .orElseThrow(() -> new OperationFailException("GetClientSocket"));
    }

    public List<NetClientSocketWrapper> getClients() {
        if (checkModification()) {
            this.clientCount.setRelease(this.size());
            this.clientSocketListCache = new ArrayList<>(this.values());
        }
        return clientSocketListCache;
    }

    public boolean checkModification() {
        int oldVal = this.clientCount.getAcquire();
        int newVal = this.size();

        return oldVal != newVal;
    }
}
