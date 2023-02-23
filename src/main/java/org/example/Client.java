package org.example;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String userName;

    public Client(Socket socket, String userName) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.userName = userName;

        }catch (Exception ex) {

        }

    }

    public void closeConnection() {
        try {
            if(bufferedWriter != null)  bufferedWriter.close();
            if(bufferedReader != null)  bufferedReader.close();
            if(socket != null)          socket.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void handleIncomingMessage() {
        Thread incomingMessageThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                   while (!socket.isClosed()) {
                       String messageReceived = bufferedReader.readLine();
                       System.out.println(messageReceived);
                   }

                }catch (Exception ex) {
                    closeConnection();
                }
            }
        });

        incomingMessageThread.start();
    }

    public void handleOutgoingMessage() {
        try {
            bufferedWriter.write(userName);
            bufferedWriter.newLine();
            bufferedWriter.flush();

            Scanner scanner = new Scanner(System.in);
            while (!socket.isClosed()) {
                String messageToSend = scanner.nextLine();
                bufferedWriter.write(messageToSend);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }

        }catch (Exception ex) {
            closeConnection();
        }
    }
}
