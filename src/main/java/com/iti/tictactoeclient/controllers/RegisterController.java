package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.User;
import com.iti.tictactoeclient.requests.SignUpReq;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.regex.*;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.iti.tictactoeclient.TicTacToeClient.mapper;

public class RegisterController implements Initializable {

    @FXML
    private ImageView backgroundimg;

    @FXML
    private TextField FirstNameTxt;

    @FXML
    private PasswordField PasswordTxt;

    @FXML
    private Button RegiterButton;

    @FXML
    private TextField UserNameTxt;

    @FXML
    private Label invalidinput;

    public void setLabel(String msg) {
        invalidinput.setText(msg);
    }

    private static final ObjectMapper mapper = new ObjectMapper();

    @FXML
    protected void onActionRegister() {
        User user = new User();
        private int flag = 0;
        user.setName(FirstNameTxt.getText());
        if (user.getName().matches(String.valueOf(-9))) {
            invalidinput.setText("invalid name");
            flag = 1;
        }
        user.setUserName(UserNameTxt.getText());
        if (user.getUserName().matches(String.valueOf(-9))) {
            invalidinput.setText("invalid username");
            flag = 1;
        }
        user.setPassword(PasswordTxt.getText());
        if (flag == 0) {
            SignUpReq signUpReq = new SignUpReq();
            signUpReq.setUser(user);
            try {
                String jRequest = mapper.writeValueAsString(signUpReq);
                ServerListener.sendRequest(jRequest);
                System.out.println("Filed to connect2");
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(backgroundimg);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setCycleCount(2);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
    }

    public static void signUpValidation(Response signUpRes) {
        if (Objects.equals(signUpRes.getStatus(), Response.STATUS_OK)) {
            TicTacToeClient.openLoginView("");
            System.out.println("Filed to connect4");
        } else {
            TicTacToeClient.openRegisterView(signUpRes.getMessage());
            System.out.println("Filed to connect5");
        }
    }
}