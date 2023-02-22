package org.example;

import java.io.IOException;
import java.net.ServerSocket;

public class Main {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(5050);
        Server server = new Server(serverSocket);
        server.startServer();

        System.out.println("Hello world!");
    }
}