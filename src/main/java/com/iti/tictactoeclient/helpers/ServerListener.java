package com.iti.tictactoeclient.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.controllers.LoginController;
import com.iti.tictactoeclient.responses.LoginRes;
import com.iti.tictactoeclient.responses.Response;
import javafx.application.Platform;
import org.json.JSONObject;

import java.io.BufferedReader;
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
    private Map<String, IType> types;

    public ServerListener() {

        initTypes();
        initConnection();
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

    @Override
    public void run() {
        while (true) {
            try {
                String sMessage = bufferedReader.readLine();
                System.out.println(sMessage);
                JSONObject json = new JSONObject(sMessage);
                String serverType = (String) json.get("type");
                types.get(serverType).handleAction(sMessage);
            } catch (Exception e) {
                initConnection();
            }
        }
    }

    private void initTypes() {
        types = new HashMap<>();
        types.put(Response.RESPONSE_LOGIN, this::Login);
        types.put(Response.RESPONSE_SIGN_UP, this::signUpRes);
    }

    public static void sendRequest(String json) {
        try {
            printStream.println(json);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    private void Login(String json) {
        try {
            LoginRes loginRes = TicTacToeClient.mapper.readValue(json, LoginRes.class);
            Platform.runLater(() -> TicTacToeClient.loginController.handleResponse(loginRes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void signUpRes(String json) {
        try {
            Response signUpRes = TicTacToeClient.mapper.readValue(json, Response.class);
            Platform.runLater(() ->TicTacToeClient.registerController.handleResponse(signUpRes));
            System.out.println("Failed to connect1");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    interface IType {
        void handleAction(String json);
    }
}