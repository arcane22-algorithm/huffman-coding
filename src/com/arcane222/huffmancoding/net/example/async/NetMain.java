package com.arcane222.huffmancoding.net.example.async;

import com.arcane222.huffmancoding.net.example.async.service.NetConfig;
import com.arcane222.huffmancoding.net.example.async.service.NetService;
import com.arcane222.huffmancoding.net.example.async.service.NetServiceImpl;


public class NetMain {

    public static void main(String[] args) {
        NetConfig config = NetConfig.createConfig(args);
        NetService service = NetServiceImpl.createService(config);
        service.start();
    }
}
