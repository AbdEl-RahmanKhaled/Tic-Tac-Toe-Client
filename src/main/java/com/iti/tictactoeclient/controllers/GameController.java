package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Position;
import com.iti.tictactoeclient.notification.FinishGameNotification;
import com.iti.tictactoeclient.requests.AskToPauseReq;
import com.iti.tictactoeclient.requests.RejectToPauseReq;
import com.iti.tictactoeclient.requests.SaveMatchReq;
import com.iti.tictactoeclient.requests.UpdateInGameStatusReq;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.List;

public class GameController implements Initializable {

    private boolean sent, viewMode;
    private Match match;
    private List<Position> positions;

    @FXML
    private ImageView backgroundimg;

    @FXML
    private TextArea ChatArea;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @FXML
    public int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;
    @FXML
    public Image img;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showAnimation() {
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

    public void startMatch(Match match) {
        sent = viewMode = false;
        positions = new ArrayList<>();
        this.match = match;
    }

    @FXML
    protected void button9() {
        b9.setGraphic(new ImageView(img));
    }

    public void notifyAskToPause() {
        String msg = "Your competitor would like to pause this game now.";
        String title = "Ask To Pause";
        TicTacToeClient.showSystemNotification(title, msg, TrayIcon.MessageType.INFO);
        if (TicTacToeClient.showConfirmation(title, msg)) {
            match.setStatus(Match.STATUS_PAUSED);
            saveMatch();
            backToHome();
        } else {
            RejectToPauseReq rejectToPauseReq = new RejectToPauseReq();
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(rejectToPauseReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
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
        TicTacToeClient.confirmationBtn1Txt = "Continue Game";
        TicTacToeClient.confirmationBtn2Txt = "Finish Game";
        if (!TicTacToeClient.showConfirmation(title, msg)) {
            finishMatch();
        }
        TicTacToeClient.confirmationBtn1Txt = "Accept";
        TicTacToeClient.confirmationBtn2Txt = "Reject";
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
        TicTacToeClient.confirmationBtn1Txt = "Save Game";
        TicTacToeClient.confirmationBtn2Txt = "End Game";
        if (TicTacToeClient.showConfirmation(title, msg)) {
            match.setStatus(Match.STATUS_PAUSED);
            saveMatch();
        } else {
            sendUpdateInGameStatus(false);
        }
        TicTacToeClient.confirmationBtn1Txt = "Accept";
        TicTacToeClient.confirmationBtn2Txt = "Reject";
        backToHome();
    }

    public void sendUpdateInGameStatus(boolean isInGame) {
        UpdateInGameStatusReq updateInGameStatusReq = new UpdateInGameStatusReq(isInGame);
        try {
            String jRequest = TicTacToeClient.mapper.writeValueAsString(updateInGameStatusReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void handleFinishGame(FinishGameNotification finishGameNotification) {
        showMatchResult(finishGameNotification.getWinner());
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
