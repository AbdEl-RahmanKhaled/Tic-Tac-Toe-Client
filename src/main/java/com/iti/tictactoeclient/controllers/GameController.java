package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Message;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.notification.MessageNotification;
import com.iti.tictactoeclient.requests.AskToResumeReq;
import com.iti.tictactoeclient.requests.Request;
import com.iti.tictactoeclient.requests.SendMessageReq;
import com.iti.tictactoeclient.requests.SignUpReq;
import com.iti.tictactoeclient.responses.AskToPauseRes;
import com.iti.tictactoeclient.responses.Response;
import com.iti.tictactoeclient.responses.SendMessageRes;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.iti.tictactoeclient.TicTacToeClient.mapper;

public class GameController implements Initializable {
    public static GameController gameController;
    public Match match;
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
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @FXML
    public int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;
    @FXML
    public Image img;

    @FXML
    protected void onActionPause() {

        try {
            Request askToPauseReq = new Request(Request.ACTION_ASK_TO_PAUSE);
            String jRequest = TicTacToeClient.mapper.writeValueAsString(askToPauseReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onActionChatsender() {
        System.out.println("70");
        if (IsValidateMessage()) {
            Message message = new Message();
            message.setMessage(TextField.getText().trim());
            message.setFrom(TicTacToeClient.homeController.getMyPlayerFullInfo().getName());
            ChatArea.appendText(  TicTacToeClient.homeController.getMyPlayerFullInfo().getName() + " : " + TextField.getText().trim() + "\n");
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
    protected void onActionExite() {
//     TicTacToeClient.openHomeView();
    }

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

    public void showPauseNotification(PlayerFullInfo playerFullInfo) {
        notification.setText(playerFullInfo.getName() + "wants to pause game");
    }

    private boolean IsValidateMessage() {
        if (TextField.getText().trim().equals("")) {
            return false;
        }
        return true;
    }

    public void handleResponse(SendMessageRes sendMessageRes) {
        System.out.println("10");
        /*TextField.addEventHandler();{
        }*/
        ChatArea.appendText( sendMessageRes.getMsg().getFrom() + " : " + sendMessageRes.getMsg().getMessage() + "\n");
    }
}
