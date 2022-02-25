package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.models.Position;
import com.iti.tictactoeclient.notification.AskToResumeNotification;
import com.iti.tictactoeclient.notification.ResumeGameNotification;
import com.iti.tictactoeclient.requests.AcceptToResumeReq;
import com.iti.tictactoeclient.requests.Request;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static GameController gameController;
    @FXML
    private Label Player1vsplayer2label;

    @FXML
    private Label notification;

    @FXML
    private ImageView backgroundimg;


    @FXML
    private TextArea ChatArea;


    @FXML
    private Button PauseButton;

    @FXML
    private Button SendButton;

    @FXML
    private TextField TextField;

    @FXML
    private Button b1 ,b2 ,b3 , b4 ,b5, b6 , b7 ,b8 ,b9;

    @FXML
    public int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0,flag8=0,flag9=0;
    @FXML
    public Image img;
    @FXML
    protected void onActionPause() {

        try {
            Request askToPauseReq=new Request(Request.ACTION_ASK_TO_PAUSE);
            String jRequest = TicTacToeClient.mapper.writeValueAsString(askToPauseReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }
    @FXML
    protected void onActionChatsender() {

    }
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

        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(backgroundimg);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setCycleCount(2);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
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


    public void fillGrid(){
        b1.setText("x");

    }


}
