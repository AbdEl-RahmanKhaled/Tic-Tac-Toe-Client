package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.helpers.game.AIGameEngine;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Position;
import com.iti.tictactoeclient.requests.UpdateBoardReq;
import javafx.animation.RotateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

public class GameVsComputerController implements Initializable {

    private Map<String, Button> buttons;
    private static final AIGameEngine aiGameEngine = new AIGameEngine();
    private Image imgX, imgO;
    private boolean myTurn;

    @FXML
    private ImageView computer, userimg, xImg, oImg, backgroundimg;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @FXML
    protected void button1() {
        placeMove(b1, "b1");
    }

    @FXML
    protected void button2() {
        placeMove(b2, "b2");
    }

    @FXML
    protected void button3() {
        placeMove(b3, "b3");
    }

    @FXML
    protected void button4() {
        placeMove(b4, "b4");
    }

    @FXML
    protected void button5() {
        placeMove(b5, "b5");

    }

    @FXML
    protected void button6() {
        placeMove(b6, "b6");

    }

    @FXML
    protected void button7() {
        placeMove(b7, "b7");

    }

    @FXML
    protected void button8() {
        placeMove(b8, "b8");

    }

    @FXML
    protected void button9() {
        placeMove(b9, "b9");
    }

    private void placeMove(Button b, String btnId) {
        if (myTurn && b.getText().equals("")) {
            buttons.get(btnId).setText(String.valueOf(Match.CHOICE_X));
            buttons.get(btnId).setGraphic(new ImageView(imgX));
            aiGameEngine.setBoard(buttons);
            if (aiGameEngine.checkWinner(String.valueOf(Match.CHOICE_X), buttons)) {
                TicTacToeClient.showAlert("Winner", "Winner", Alert.AlertType.INFORMATION);
            } else if (aiGameEngine.getAvailableCells().isEmpty()) {
                TicTacToeClient.showAlert("Game Over", "Game Over", Alert.AlertType.INFORMATION);
            } else {
                aiTurn();
            }
        }
    }

    @FXML
    void onActionExit() {
        onActionReset();
        TicTacToeClient.openHomeView();
    }

    @FXML
    protected void onActionReset() {
        for (Button b : buttons.values()) {
            b.setText("");
            b.setGraphic(new ImageView());
        }
        myTurn = true;
    }

    private void aiTurn() {
        myTurn = false;
        aiGameEngine.minMax(0, Match.CHOICE_O);
        buttons.get(aiGameEngine.computerMove).setText(String.valueOf(Match.CHOICE_O));
        buttons.get(aiGameEngine.computerMove).setGraphic(new ImageView(imgO));
        if (aiGameEngine.checkWinner(String.valueOf(Match.CHOICE_O), buttons)) {
            TicTacToeClient.showAlert("Loser", "Loser", Alert.AlertType.INFORMATION);
        } else if (aiGameEngine.getAvailableCells().isEmpty()) {
            TicTacToeClient.showAlert("Game Over", "Game Over", Alert.AlertType.INFORMATION);
        } else {
            myTurn = true;
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initButtons();
        imgX = new Image(new File("images/x.png").toURI().toString());
        imgO = new Image(new File("images/o.png").toURI().toString());
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


    public void startGame() {
        myTurn = true;
    }

    public void showAnimation() {
        File backFile = new File("images/7.png");
        Image background = new Image(backFile.toURI().toString());
        backgroundimg.setImage(background);
        File cumputer = new File("images/computer.png");
        Image cumputerim = new Image(cumputer.toURI().toString());
        computer.setImage(cumputerim);

        File user = new File("images/player.png");
        Image userim = new Image(user.toURI().toString());
        userimg.setImage(userim);
        File x = new File("images/x.png");
        Image ximage = new Image(x.toURI().toString());
        xImg.setImage(ximage);

        File o = new File("images/o.png");
        Image oimage = new Image(o.toURI().toString());
        oImg.setImage(oimage);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(backgroundimg);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setCycleCount(2);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
    }

}