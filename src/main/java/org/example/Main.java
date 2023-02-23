package org.example;

import javax.sound.sampled.FloatControl;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);

        System.out.println("***** SPECTRE PRIME BASE INTERFACE *****");
        System.out.print("Enter a mode to run in, SERVER/CLIENT: ");
        String appMode = scanner.nextLine();

        if(appMode.toLowerCase().equals("server") ) {
            System.out.print("Enter the port number for the PRIME BASE (default-7070): ");
            String serverPortString = scanner.nextLine();
            int serverPort = serverPortString=="" ? 7070 : Integer.parseInt(serverPortString);
            ServerSocket serverSocket = new ServerSocket(serverPort);
            Server server = new Server(serverSocket);
            System.out.println("The PRIME BASE is up, waiting for vessels to dock in...");
            server.startServer();

        }else if(appMode.toLowerCase().equals("client")) {
            System.out.print("Enter the PRIME BASE HOST (default-localhost): ");
            String serverUrl = scanner.nextLine();
            serverUrl = serverUrl=="" ? "localhost" : serverUrl;
            System.out.println("Enter the port number: ");
            String serverPortString = scanner.nextLine();
            int serverPort = serverPortString=="" ? 7070 : Integer.parseInt(serverPortString);
            Socket socket = new Socket(serverUrl, serverPort);
            System.out.println("Heading for the PRIME BASE...");
            System.out.print("Enter your interGalactic userName: ");
            String userName = scanner.nextLine();
            Client client = new Client(socket,userName);
            client.handleIncomingMessage();
            client.handleOutgoingMessage();

        }else {
            System.out.println("Do you even speak out language?");
            System.out.println("Try again...");
        }
    }
}