package com.arcane222.huffmancoding.net.example.async;

import com.arcane222.huffmancoding.net.example.async.core.service.NetConfig;
import com.arcane222.huffmancoding.net.example.async.core.service.NetService;
import com.arcane222.huffmancoding.net.example.async.core.service.NetServiceImpl;
import com.arcane222.huffmancoding.net.example.async.data.buf.NetSharedBuffer;
import sun.misc.Unsafe;

import java.io.IOException;


public class NetMain {

    public static void main(String[] args) throws InterruptedException, IOException {

        try (NetSharedBuffer sharedBuffer = new NetSharedBuffer(4, false)) {

        }

        NetConfig config = NetConfig.createConfig(args);
        NetService service = NetServiceImpl.createService(config);
        service.start();
    }
}
