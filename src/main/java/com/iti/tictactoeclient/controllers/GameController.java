package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.helpers.game.GameEngine;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.Position;
import com.iti.tictactoeclient.notification.FinishGameNotification;
import com.iti.tictactoeclient.notification.ResumeGameNotification;
import com.iti.tictactoeclient.requests.AskToPauseReq;
import com.iti.tictactoeclient.requests.RejectToPauseReq;
import com.iti.tictactoeclient.requests.SaveMatchReq;
import com.iti.tictactoeclient.requests.*;
import com.iti.tictactoeclient.models.Message;
import com.iti.tictactoeclient.notification.MessageNotification;
import com.iti.tictactoeclient.requests.SendMessageReq;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.TrayIcon;
import java.util.*;
import java.io.File;
import java.net.URL;
import java.util.List;

public class GameController implements Initializable {

    private boolean sent;
    private Match match;
    private List<Position> positions;
    private Map<String, Button> buttons;
    private Image imgChoice;
    private char txtChoice;
    private boolean myTurn = false;
    private final Image imgX = new Image(new File("images/x.png").toURI().toString());
    private final Image imgO = new Image(new File("images/o.png").toURI().toString());
    private final GameEngine gameEngine = new GameEngine();

    @FXML
    private ImageView backgroundimg;

    @FXML
    protected Label lblXPlayer, lblOPlayer, lblYourTurn;

    @FXML
    private TextField TextField;

    @FXML
    private TextArea ChatArea;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initButtons();

    }

    public void showAnimation() {
        File backFile = new File("images/7.png");
        Image background = new Image(backFile.toURI().toString());
        backgroundimg.setImage(background);
        ChatArea.setEditable(false);

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(backgroundimg);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setCycleCount(2);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
    }

    private void initButtons() {
        buttons = new HashMap<>();
        buttons.put("b1", b1);
        buttons.put("b2", b2);
        buttons.put("b3", b3);
        buttons.put("b4", b4);
        buttons.put("b5", b5);
        buttons.put("b6", b6);
        buttons.put("b7", b7);
        buttons.put("b8", b8);
        buttons.put("b9", b9);
    }

    @FXML
    protected void onActionAskToPause() {
        if (!sent) {
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
            //message appearing on chat area
            ChatArea.appendText(TicTacToeClient.homeController.getMyPlayerFullInfo().getName() + " : " + TextField.getText().trim() + "\n");
            //creating request for the server
            SendMessageReq sendMessageReq = new SendMessageReq();
            sendMessageReq.setMessage(message);
            try {
                //transforming from object to string json
                String jRequest = TicTacToeClient.mapper.writeValueAsString(sendMessageReq);
                //sending the request
                ServerListener.sendRequest(jRequest);
                //clearing text field
                TextField.clear();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            //sending error message notification
            TicTacToeClient.showSystemNotification("Message Error",
                    " You can't enter an empty message ",
                    TrayIcon.MessageType.ERROR);
            System.out.println("not a valid msg");
        }
    }


    private void turn() {
        if (txtChoice == Match.CHOICE_X) {
            txtChoice = Match.CHOICE_O;
            imgChoice = imgO;
        } else {
            txtChoice = Match.CHOICE_X;
            imgChoice = imgX;
        }
    }

    @FXML
    protected void button1() {
        placeMove("b1");
    }

    @FXML
    protected void button2() {
        placeMove("b2");
    }

    @FXML
    protected void button3() {
        placeMove("b3");
    }

    @FXML
    protected void button4() {
        placeMove("b4");
    }

    @FXML
    protected void button5() {
        placeMove("b5");

    }

    @FXML
    protected void button6() {
        placeMove("b6");

    }

    @FXML
    protected void button7() {
        placeMove("b7");

    }

    @FXML
    protected void button8() {
        placeMove("b8");

    }

    @FXML
    protected void button9() {
        placeMove("b9");
    }

    private void placeMove(String btnId) {
        if (myTurn && buttons.get(btnId).getText().equals("")) {
            myTurn = !myTurn;
            Position position = new Position();
            position.setM_id(match.getM_id());
            position.setPlayer_id(TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id());
            position.setPosition(btnId);
            UpdateBoardReq updateBoardReq = new UpdateBoardReq(position);
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(updateBoardReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void startMatch(Match match) {
        this.match = match;
        init();
        firstTurn();
        setData();
    }

    public void confirmResume(ResumeGameNotification resumeGameNotification) {
        init();
        TicTacToeClient.openGameView();
        positions = resumeGameNotification.getPositions();
        match = resumeGameNotification.getMatch();
        fillGrid();
        turnAfterResume();
    }

    private void init() {
        sent = false;
        positions = new ArrayList<>();

    }

    private void setData() {
        String playerX;
        String playerO;
        if (match.getP1_choice().equals(String.valueOf(Match.CHOICE_X))) {
            playerX = TicTacToeClient.homeController.getPlayerFullInfo(match.getPlayer1_id()).getName();
            playerO = TicTacToeClient.homeController.getPlayerFullInfo(match.getPlayer2_id()).getName();
        } else {
            playerX = TicTacToeClient.homeController.getPlayerFullInfo(match.getPlayer2_id()).getName();
            playerO = TicTacToeClient.homeController.getPlayerFullInfo(match.getPlayer1_id()).getName();
        }
        lblXPlayer.setText(playerX);
        lblOPlayer.setText(playerO);
    }

    private void firstTurn() {
        myTurn = (match.getPlayer1_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id() && match.getP1_choice().equals(String.valueOf(Match.CHOICE_X)))
                || (match.getPlayer2_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id() && match.getP2_choice().equals(String.valueOf(Match.CHOICE_X)));

        txtChoice = Match.CHOICE_X;
        imgChoice = imgX;
        setTurnLabel();
    }

    private void setTurnLabel() {
        if (myTurn)
            lblYourTurn.setText("Your Turn");
        else
            lblYourTurn.setText("Competitor Turn");
    }

    private void turnAfterResume() {
        if (positions.size() % 2 == 0) {
            firstTurn();
        } else {
            myTurn = (match.getPlayer1_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id() && match.getP1_choice().equals(String.valueOf(Match.CHOICE_O)))
                    || (match.getPlayer2_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id() && match.getP2_choice().equals(String.valueOf(Match.CHOICE_O)));

            txtChoice = Match.CHOICE_O;
            imgChoice = imgO;
        }
        setTurnLabel();
    }

    public void acceptResumeGame(Player player, Match match) {
        AcceptToResumeReq acceptToResumeReq = new AcceptToResumeReq(player, match);
        try {
            String jRequest = TicTacToeClient.mapper.writeValueAsString(acceptToResumeReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void notifyAskToPause() {
        String msg = "Your competitor would like to pause this game now.";
        String title = "Ask To Pause";
        TicTacToeClient.showSystemNotification(title, msg, TrayIcon.MessageType.INFO);
        if (TicTacToeClient.showConfirmation(title, msg, "Accept", "Reject")) {
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
        if (!TicTacToeClient.showConfirmation(title, msg, "Continue Game", "Finish Game")) {
            finishMatch();
        }
    }

    @FXML
    protected void onActionFinish() {
        if (TicTacToeClient.showConfirmation("Finish Match", "Are you sure you want to finish this match? \n" +
                "HINT: you will lose the match.", "Yes", "No")) {
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

    public void handleUpdateBoard(Position position) {
        buttons.get(position.getPosition()).setGraphic(new ImageView(imgChoice));
        buttons.get(position.getPosition()).setText(String.valueOf(txtChoice));
        positions.add(position);
        checkMatchResult(position.getPlayer_id());
        turn();

        if (!(position.getPlayer_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id())) {
            myTurn = !myTurn;
        }

        setTurnLabel();
    }

    private void checkMatchResult(int playerId) {
        if (gameEngine.checkWinner(String.valueOf(txtChoice), buttons) && playerId == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id()) {
            match.setWinner(TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id());
            match.setStatus(Match.STATUS_FINISHED);
            saveMatch();
            showMatchResult(playerId);
            backToHome();
        } else if (positions.size() == 9 && playerId == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id()) {
            match.setStatus(Match.STATUS_FINISHED);
            saveMatch();
            showMatchResult(-1);
            backToHome();
        }
    }

    private boolean IsValidateMessage() {
        //to validate if text in field text is empty
        return !TextField.getText().trim().equals("");
    }

    public void handleMessageNotification(MessageNotification messageNotification) {
        //sending notification of message
        TicTacToeClient.showSystemNotification("Message Notification",
                messageNotification.getMessage().getFrom()
                        + " sent you a message : " +
                        messageNotification.getMessage().getMessage(),
                TrayIcon.MessageType.INFO);
        //message appearing on chat area
        ChatArea.appendText(messageNotification.getMessage().getFrom()
                + " : " + messageNotification.getMessage().getMessage() + "\n");

    }

    private void showMatchResult(int winner) {
        String title;
        String img;
        String msg;
        if (winner == -1) {
            title = "Game Over";
            msg = "Game Over";
            img = "gameOver";
        } else if (winner == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id()) {
            title = "You Won";
            msg = "WINNER";
            img = "winner";
        } else {
            title = "Good luck next time";
            msg = title;
            img = "loser";
        }
        TicTacToeClient.showSystemNotification(title, msg, TrayIcon.MessageType.INFO);
        TicTacToeClient.showAlert(title, img);
        backToHome();
    }


    private void backToHome() {
        match = null;
        positions.clear();
        TicTacToeClient.openHomeView();
        reset();
    }

    protected void reset() {
        for (Button b : buttons.values()) {
            b.setText("");
            b.setGraphic(new ImageView());
        }
        myTurn = false;
        lblYourTurn.setText("");
        lblXPlayer.setText("");
        lblOPlayer.setText("");
    }

    private void fillGrid() {
        String txtChoice;
        for (Position position : positions) {
            Image imgChoice = imgO;
            if (position.getPlayer_id() == match.getPlayer1_id())
                txtChoice = match.getP1_choice();
            else
                txtChoice = match.getP2_choice();

            if (txtChoice.equals(String.valueOf(Match.CHOICE_X)))
                imgChoice = imgX;

            buttons.get(position.getPosition()).setText(txtChoice);
            buttons.get(position.getPosition()).setGraphic(new ImageView(imgChoice));
        }
    }
}
