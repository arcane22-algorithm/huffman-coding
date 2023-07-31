package com.arcane222.huffmancoding.net.example.async.task;

public class NetRecvWorker extends Thread {
    public NetRecvWorker(NetRecvTask recvTask) {
        super(recvTask);

        this.setName("RecvWorker");
        this.setDaemon(false);
    }
}
