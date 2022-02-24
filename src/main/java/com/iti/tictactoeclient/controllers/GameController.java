package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.models.Position;
import com.iti.tictactoeclient.notification.FinishGameNotification;
import com.iti.tictactoeclient.requests.AskToPauseReq;
import com.iti.tictactoeclient.requests.RejectToPauseReq;
import com.iti.tictactoeclient.requests.SaveMatchReq;
import com.iti.tictactoeclient.requests.UpdateInGameStatusReq;
import com.iti.tictactoeclient.models.Message;
import com.iti.tictactoeclient.notification.MessageNotification;
import com.iti.tictactoeclient.requests.SendMessageReq;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.util.List;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

import static com.iti.tictactoeclient.TicTacToeClient.mapper;

public class GameController implements Initializable {

    private boolean sent, viewMode;
    private Match match;
    private List<Position> positions;

    @FXML
    private ImageView backgroundimg;


    @FXML
    private TextField TextField;

    @FXML
    private TextArea ChatArea;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @FXML
    public int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;
    @FXML
    public Image img;

    @FXML
    protected void onActionExite() {
//     TicTacToeClient.openHomeView();
    }
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
    }

    public void showAnimation(){
        File backFile = new File("images/7.png");
        Image background = new Image(backFile.toURI().toString());
        backgroundimg.setImage(background);
        ChatArea.setEditable(false);

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(backgroundimg);
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(2);
        scale.setByX(0.4);
        scale.setAutoReverse(true);
        scale.play();
    }

    @FXML
    protected void onActionAskToPause() {
        if (!sent && !viewMode) {
            try {
                AskToPauseReq askToPauseReq = new AskToPauseReq();
                String jRequest = TicTacToeClient.mapper.writeValueAsString(askToPauseReq);
                ServerListener.sendRequest(jRequest);
                sent = true;
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    protected void onActionChatsender() {
        if (IsValidateMessage()) {
            Message message = new Message();
            //setting message and message sender
            message.setMessage(TextField.getText().trim());
            message.setFrom(TicTacToeClient.homeController.getMyPlayerFullInfo().getName());
            //message appearing on chatarea
            ChatArea.appendText(TicTacToeClient.homeController.getMyPlayerFullInfo().getName() + " : " + TextField.getText().trim() + "\n");
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setMessage(message);
            try {
                System.out.println("2");
                String jRequest = mapper.writeValueAsString(sendMessageReq);
                ServerListener.sendRequest(jRequest);
                TextField.clear();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("not a valid msg");
        }
    }

    @FXML
    protected void onActionFinish() {
        TicTacToeClient.openHomeView();
    }


    @FXML
    protected void button1() {
        b1.setGraphic(new ImageView(img));

    }

    @FXML
    protected void button2() {
        b2.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button3() {
        b3.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button4() {
        b4.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button5() {
        b5.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button6() {
        b6.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button7() {
        b7.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button8() {
        b8.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button9() {
        b9.setGraphic(new ImageView(img));
    }

    public void showPauseNotification(PlayerFullInfo playerFullInfo){
        TicTacToeClient.showSystemNotification("Pause","wants to pause game", TrayIcon.MessageType.INFO);
    }

    public void confirmResume(ResumeGameNotification resumeGameNotification){
        TicTacToeClient.openGameView();
        List<Position>positions =resumeGameNotification.getPositions();
        Match match =resumeGameNotification.getMatch();

    }

    public void acceptResumeGame(Player player, Match match){
        AcceptToResumeReq acceptToResumeReq=new AcceptToResumeReq(player,match);
        try {
            String jRequest = TicTacToeClient.mapper.writeValueAsString(acceptToResumeReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    private void saveMatch() {
        SaveMatchReq saveMatchReq = new SaveMatchReq(match, positions);
        try {
            String jRequest = TicTacToeClient.mapper.writeValueAsString(saveMatchReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void handleAskToPauseResponse() {
        String title = "Rejected Pause Request";
        String msg = "It seems your competitor can not pause the match";
        if (!TicTacToeClient.showConfirmation(title, msg, "Continue Game", "Finish Game")) {
            finishMatch();
        }
    }

    private void finishMatch() {
        match.setStatus(Match.STATUS_FINISHED);
        if (match.getPlayer1_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id()) {
            match.setWinner(match.getPlayer2_id());
        } else {
            match.setWinner(match.getPlayer1_id());
        }
        saveMatch();
        backToHome();
    }

    public void handlePauseGame() {
        String title = "Game Paused";
        String msg = "Your competitor accept to pause the game.";
        TicTacToeClient.showSystemNotification(title, msg, TrayIcon.MessageType.INFO);
        TicTacToeClient.showAlert(title, msg, Alert.AlertType.INFORMATION);
        backToHome();
    }

    public void competitorConnectionIssue() {
        String title = "Competitor Connection Issue";
        String msg = "It seems your competitor disconnected from the server.";
        TicTacToeClient.showSystemNotification(title, msg, TrayIcon.MessageType.WARNING);
        if (TicTacToeClient.showConfirmation(title, msg, "Save Game", "End Game")) {
            match.setStatus(Match.STATUS_PAUSED);
            saveMatch();
        } else {
            TicTacToeClient.sendUpdateInGameStatus(false);
        }
        backToHome();
    }



    public void handleFinishGame(FinishGameNotification finishGameNotification) {
        showMatchResult(finishGameNotification.getWinner());
    }

    private boolean IsValidateMessage() {
        //to validate if text in fieldtext is empty
        if (TextField.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    public void handleMessageNotification(MessageNotification messageNotification) {
        //sending notification of message
        TicTacToeClient.showSystemNotification("Message Notification",
                messageNotification.getMessage().getFrom()
                        + " sent you a message : " +
                        messageNotification.getMessage().getMessage(),
                TrayIcon.MessageType.INFO);
        //message appearing on chatarea
        ChatArea.appendText(messageNotification.getMessage().getFrom()
                + " : " + messageNotification.getMessage().getMessage() + "\n");

    }

    private void showMatchResult(int winner) {
        String title = "Match Result";
        String msg;
        if (winner == -1) {
            msg = "Game Over.";
        } else if (winner == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id()) {
            msg = "Victory";
        } else {
            msg = "You lost the match, good luck next time.";
        }
        TicTacToeClient.showSystemNotification(title, msg, TrayIcon.MessageType.INFO);
        TicTacToeClient.showAlert(title, msg, Alert.AlertType.INFORMATION);
        backToHome();
    }


    private void backToHome() {
        match = null;
        positions.clear();
        TicTacToeClient.openHomeView();
    }
}
