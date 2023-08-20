package com.arcane222.huffmancoding.net.example.async;

import com.arcane222.huffmancoding.net.example.async.core.service.NetConfig;
import com.arcane222.huffmancoding.net.example.async.core.service.NetService;
import com.arcane222.huffmancoding.net.example.async.core.service.NetServiceImpl;
import com.arcane222.huffmancoding.net.example.async.data.buf.NetSharedBuffer;
import com.arcane222.huffmancoding.net.example.async.data.buf.normal.NormalBufferType;
import sun.misc.Unsafe;

import java.io.IOException;


public class NetMain {

    public static void main(String[] args) throws InterruptedException, IOException {

        try (NetSharedBuffer sharedBuffer = new NetSharedBuffer(NormalBufferType.UNSAFE, 4)) {
            sharedBuffer.allocateData((byte) 10, 0);
            System.out.println(sharedBuffer.bufferSize());
            System.out.println(sharedBuffer.deallocateData(0, 1)[0]);
        }

        NetConfig config = NetConfig.createConfig(args);
        NetService service = NetServiceImpl.createService(config);
        service.start();
    }
}
