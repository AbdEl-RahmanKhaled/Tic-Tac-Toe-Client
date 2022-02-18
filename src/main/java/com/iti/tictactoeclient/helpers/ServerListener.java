package com.iti.tictactoeclient.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.requests.SignUpReq;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.responses.Response;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ServerListener extends Thread {
    private static final ObjectMapper mapper = new ObjectMapper();
    private Socket clientSocket;
    private static PrintStream printStream;
    private BufferedReader dataInputStream;
    private Map<String, IAction> actions;

    public ServerListener() {
        try {
            dataInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            printStream = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initActions() {
        actions = new HashMap<>();
        actions.put(Response.RESPONSE_LOGIN, this::signUpRes);
    }

    @Override
    public void run() {
        while (true) {
            try {
                String jResponse = dataInputStream.readLine();
                JSONObject json = new JSONObject(jResponse);
                String serverAction = (String) json.get("action");
                actions.get(serverAction).handleAction(jResponse);
            } catch (Exception e) {
                System.out.println("Stopped");
            }
        }

    }

    private void signUpRes(String json) {
        try {
            Response signUpRes = mapper.readValue(json, Response.class);
            if (Objects.equals(signUpRes.getStatus(), Response.STATUS_OK)){
                TicTacToeClient.openLoginView("");
            }
            else
            {
                TicTacToeClient.openRegisterView(signUpRes.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    interface IAction {
        void handleAction(String json);
    }
}

