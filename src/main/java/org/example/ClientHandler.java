package org.example;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler extends Thread {
    public static ArrayList<ClientHandler> allClients = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUserName;

    public ClientHandler(Socket socket) {
        try{
            this.socket = socket;
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.clientUserName = bufferedReader.readLine();

            allClients.add(this);
            broadCastMessage("SERVER: User " + clientUserName + " has entered.");

        }catch(Exception ex) {
            closeCurrentConnection();
        }
    }

    @Override
    public void run() {
        try {
            while(!socket.isClosed()) {
                String messageFromClient = bufferedReader.readLine();
                broadCastMessage(this.clientUserName + ": " +messageFromClient);
            }

        }catch (Exception ex) {
            closeCurrentConnection();
        }
    }

    public void closeCurrentConnection() {
        removeCurrentUser();
        try {
            bufferedReader.close();
            bufferedWriter.close();
            socket.close();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void broadCastMessage(String message) {
        try {
            for(ClientHandler currentHandler : allClients) {

                if(currentHandler.clientUserName != this.clientUserName) {
                    currentHandler.bufferedWriter.write(message);
                    currentHandler.bufferedWriter.newLine();
                    currentHandler.bufferedWriter.flush();
                }
            }

        }catch (Exception ex) {
            closeCurrentConnection();
            ex.printStackTrace();
        }
    }


    public void removeCurrentUser() {
        allClients.remove(this);
    }

}
