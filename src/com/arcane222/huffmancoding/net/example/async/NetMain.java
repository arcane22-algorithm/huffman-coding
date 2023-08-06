package com.arcane222.huffmancoding.net.example.async;

import com.arcane222.huffmancoding.net.example.async.core.service.NetConfig;
import com.arcane222.huffmancoding.net.example.async.core.service.NetService;
import com.arcane222.huffmancoding.net.example.async.core.service.NetServiceImpl;


public class NetMain {

    public static void main(String[] args) {
        NetConfig config = NetConfig.createConfig(args);
        NetService service = NetServiceImpl.createService(config);
        service.start();
    }
}
