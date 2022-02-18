package com.iti.tictactoeclient.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.responses.LoginRes;
import com.iti.tictactoeclient.responses.Response;
import javafx.application.Platform;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ServerListener extends Thread {
    private static final int PORT = 5000;
    private static final String HOST = "127.0.0.1";
    private static PrintStream printStream;
    private Socket socket;
    private BufferedReader bufferedReader;
    private Map<String, IAction> actionMap;

    public ServerListener() {

        initActions();
    }

    private void initConnection() {
        try {
            socket = new Socket(HOST, PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            System.out.println("connected");
        } catch (Exception ex) {
            System.out.println("Filed to connect");
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            initConnection();
        }
    }

    private void initActions() {
        actionMap = new HashMap<>();
    }

    @Override
    public void run() {
        while (true) {
            try {
                String sMessage = bufferedReader.readLine();
                JSONObject json = new JSONObject(sMessage);
                String serverType = (String) json.get("type");
                actionMap.get(serverType).handleAction(sMessage);
            } catch (Exception e) {
                initConnection();
            }
        }
    }

    public static void sendRequest(String json) {
        printStream.println(json);
    }



    interface IAction {
        void handleAction(String json);
    }
}
