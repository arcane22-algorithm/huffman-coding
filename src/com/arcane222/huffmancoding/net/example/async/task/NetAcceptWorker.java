package com.arcane222.huffmancoding.net.example.async.task;

public class NetAcceptWorker extends Thread {

    public NetAcceptWorker(NetAcceptTask acceptTask) {
        super(acceptTask);

        this.setName("AcceptWorker");
        this.setDaemon(false);
    }
}
