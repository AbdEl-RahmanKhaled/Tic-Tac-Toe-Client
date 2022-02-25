package com.iti.tictactoeclient.helpers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.notification.*;
import com.iti.tictactoeclient.requests.AcceptToResumeReq;
import com.iti.tictactoeclient.requests.BackFromOfflineReq;
import com.iti.tictactoeclient.responses.*;
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
    private Map<String, IType> types;
    private boolean running;

    public ServerListener() {
        running = true;
        initTypes();
        //initConnection();
    }

    private void initConnection() {
        try {
            socket = new Socket(HOST, PORT);
            bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            printStream = new PrintStream(socket.getOutputStream());
            System.out.println("connected");
            backFromOffline();
        } catch (Exception ex) {
            System.out.println("Failed to connect");
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
        while (running) {
            try {
                String sMessage = bufferedReader.readLine();
                System.out.println(sMessage);
                JSONObject json = new JSONObject(sMessage);
                String serverType = (String) json.get("type");
                if (types.get(serverType) != null)
                    types.get(serverType).handleAction(sMessage);
            } catch (Exception e) {
                if (running) {
                    initConnection();
                }
            }
        }
    }

    private void initTypes() {
        types = new HashMap<>();
        types.put(Response.RESPONSE_LOGIN, this::Login);
        types.put(Response.RESPONSE_INVITE_TO_GAME, this::inviteToGameResponse);
        types.put(Response.RESPONSE_SIGN_UP, this::signUpRes);
        types.put(Response.RESPONSE_GET_MATCH_HISTORY, this::getMatchHistory);
        types.put(Response.RESPONSE_ASK_TO_PAUSE, this::askToPauseResponse);

        types.put(Notification.NOTIFICATION_UPDATE_STATUS, this::updateStatus);
        types.put(Notification.NOTIFICATION_GAME_INVITATION, this::gameInvitation);
        types.put(Notification.NOTIFICATION_START_GAME, this::startGame);
        types.put(Notification.NOTIFICATION_ASK_TO_PAUSE, this::askToPauseNotification);
        types.put(Notification.NOTIFICATION_MESSAGE, this::sendMessageRes);
        types.put(Notification.NOTIFICATION_ASK_TO_RESUME, this::askToResume);
        types.put(Notification.NOTIFICATION_RESUME_GAME, this::resumeGame);
        types.put(Notification.NOTIFICATION_FINISH_GAME, this::finishGameNotification);
        types.put(Notification.NOTIFICATION_PAUSE_GAME, this::pauseGameNotification);
        types.put(Notification.NOTIFICATION_COMPETITOR_CONNECTION_ISSUE, this::competitorConnectionIssueNotification);
    }

    private void resumeGame(String json){
        try {
            ResumeGameNotification resumeGameNotification= TicTacToeClient.mapper.readValue(json,ResumeGameNotification.class);
            Platform.runLater(()->
                    TicTacToeClient.gameController.confirmResume(resumeGameNotification));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    private void competitorConnectionIssueNotification(String json) {
        Platform.runLater(() -> TicTacToeClient.gameController.competitorConnectionIssue());
    }

    private void askToPauseResponse(String json) {
        Platform.runLater(() -> TicTacToeClient.gameController.handleAskToPauseResponse());
    }

    private void pauseGameNotification(String json) {
        Platform.runLater(() -> TicTacToeClient.gameController.handlePauseGame());
    }

    private void finishGameNotification(String json) {
        try {
            FinishGameNotification finishGameNotification = TicTacToeClient.mapper.readValue(json, FinishGameNotification.class);
            Platform.runLater(() -> TicTacToeClient.gameController.handleFinishGame(finishGameNotification));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void askToResume(String json){
        try {
            AskToResumeNotification askToResumeNotification=TicTacToeClient.mapper.readValue(json, AskToResumeNotification.class);
            Platform.runLater(() -> TicTacToeClient.homeController.addResumeReq(askToResumeNotification));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
    private void startGame(String json) {
        try {
            StartGameNotification startGameNotification = TicTacToeClient.mapper.readValue(json, StartGameNotification.class);
            Platform.runLater(() -> TicTacToeClient.homeController.startGame(startGameNotification.getMatch()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void inviteToGameResponse(String json) {
        try {
            InviteToGameRes inviteToGameRes = TicTacToeClient.mapper.readValue(json, InviteToGameRes.class);
            Platform.runLater(() -> TicTacToeClient.homeController.inviteToGameResponse(inviteToGameRes));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void gameInvitation(String json) {
        try {
            GameInvitationNotification gameInvitationNotification = TicTacToeClient.mapper.readValue(json, GameInvitationNotification.class);
            Platform.runLater(() -> TicTacToeClient.homeController.notifyGameInvitation(gameInvitationNotification.getPlayer()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void updateStatus(String json) {
        try {
            UpdateStatusNotification updateStatusNotification = TicTacToeClient.mapper.readValue(json, UpdateStatusNotification.class);
            Platform.runLater(() -> TicTacToeClient.homeController.updateStatus(updateStatusNotification.getPlayerFullInfo()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    public static void sendRequest(String json) {
        try {
            System.out.println(json);
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
            Platform.runLater(() -> TicTacToeClient.registerController.handleResponse(signUpRes));
            System.out.println("Failed to connect1");
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void askToPauseNotification(String json) {
        Platform.runLater(() -> TicTacToeClient.gameController.notifyAskToPause());
    }
    private void sendMessageRes(String json) {
        try {
            System.out.println("1");
            MessageNotification messageNotification = TicTacToeClient.mapper.readValue(json, MessageNotification.class);
            Platform.runLater(() ->TicTacToeClient.gameController.handleMessageNotification(messageNotification));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void getMatchHistory(String json) {
        try {
            GetMatchHistoryRes getMatchHistoryRes = TicTacToeClient.mapper.readValue(json, GetMatchHistoryRes.class);
            TicTacToeClient.matchController.handleResponse(getMatchHistoryRes.getMatches());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void backFromOffline() {
        // if was logged in
        if (TicTacToeClient.homeController.getMyPlayerFullInfo() != null) {
            BackFromOfflineReq backFromOfflineReq = new BackFromOfflineReq(
                    new Player(TicTacToeClient.homeController.getMyPlayerFullInfo()));
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(backFromOfflineReq);
                sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void interrupt() {
        super.interrupt();
        try {
            running = false;
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    interface IType {
        void handleAction(String json);
    }
}