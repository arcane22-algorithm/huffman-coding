package com.arcane222.huffmancoding.net.example.async;

import static com.arcane222.huffmancoding.net.example.async.util.NetConstant.*;

import com.arcane222.huffmancoding.net.example.async.exception.ProgramArgumentEmptyException;
import com.arcane222.huffmancoding.net.example.async.exception.UndefinedServiceTypeException;
import com.arcane222.huffmancoding.net.example.async.server.NetAsyncSocketServer;

import java.util.Optional;

public class NetMain {

    private static void emptyAction() {
        System.out.println("Instance is empty. Try again.");
    }

    private static void runServer(String[] args) {
        if(args.length < 2)
            throw new ProgramArgumentEmptyException("Program argument (port) is empty.");

        int port = Integer.parseInt(args[1]);
        Optional<NetAsyncSocketServer> asyncSocketServer = NetAsyncSocketServer.newInstance(port);
        asyncSocketServer.ifPresentOrElse(NetAsyncSocketServer::start, NetMain::emptyAction);
    }

    private static void runClient(String[] args) {

    }

    public static void executeService(String[] args) {
        if (args.length == 0) {
            throw new ProgramArgumentEmptyException();
        }

        String type = args[0].toLowerCase();
        switch (type) {
            case SERVICE_TYPE_SERVER: {
                runServer(args);
            }
            break;

            case SERVICE_TYPE_CLIENT: {
                runClient(args);
            }
            break;

            default: {
                throw new UndefinedServiceTypeException(type);
            }
        }
    }

    public static void main(String[] args) {
        executeService(args);
    }
}
