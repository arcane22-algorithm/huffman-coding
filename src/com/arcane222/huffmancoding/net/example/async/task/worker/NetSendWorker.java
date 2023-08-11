package com.arcane222.huffmancoding.net.example.async.task.worker;

public class NetSendWorker extends Thread {

    public NetSendWorker(NetSendWorker sendWorker) {
        super(sendWorker);

        this.setName("SendWorker");
        this.setDaemon(false);
    }
}
