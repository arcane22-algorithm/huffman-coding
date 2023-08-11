package com.arcane222.huffmancoding.net.example.async.task.worker;

import com.arcane222.huffmancoding.net.example.async.task.NetServerAcceptTask;

public class NetAcceptWorker extends Thread {

    public NetAcceptWorker(NetServerAcceptTask acceptTask) {
        super(acceptTask);

        this.setName("AcceptWorker");
        this.setDaemon(false);
    }
}
