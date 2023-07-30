package com.arcane222.huffmancoding.net.example.async.common;

public class AcceptWorker extends Thread {

    public AcceptWorker(AcceptTask acceptTask) {
        super(acceptTask);

        this.setName("AcceptWorker");
        this.setDaemon(false);
    }
}
