package com.iti.tictactoeclient.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.responses.LoginRes;
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

public class ServerListener extends Thread{
    private static final ObjectMapper mapper = new ObjectMapper();
    private static PrintStream printStream;
    private BufferedReader dataInputStream;
    private Map<String, IType> types;

    public ServerListener(Socket clientSocket){
        try {
            initTypes();
            dataInputStream = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            printStream = new PrintStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void initTypes() {
        types = new HashMap<>();
        types.put(Response.RESPONSE_LOGIN, this::Login);
        types.put(Response.RESPONSE_SIGN_UP, this::signUpRes);
    }

    @Override
    public void run(){
        while(true){
            try {
                String jResponse = dataInputStream.readLine();
                JSONObject json = new JSONObject(jResponse);
                String responseType = (String) json.get("type");
                types.get(responseType).handleAction(jResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private void Login(String json){
        try {
            LoginRes loginRes = mapper.readValue(json, LoginRes.class);
            if(Objects.equals(loginRes.getStatus(), Response.STATUS_OK)){
                TicTacToeClient.openHomeView();
            }
            else{
                TicTacToeClient.openLoginView(loginRes.getMessage());
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace();
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


    public static void fireRequest(String json){
        printStream.println(json);
    }

    interface IType {
        void handleAction(String json);
    }

}
