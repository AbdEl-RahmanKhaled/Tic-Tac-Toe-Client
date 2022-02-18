package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.models.User;
import com.iti.tictactoeclient.requests.SignUpReq;
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

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    private static final ObjectMapper mapper = new ObjectMapper();
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

    @FXML
    protected void onActionRegister() {
        User user = new User();
        user.setName(FirstNameTxt.getText());
        user.setUserName(UserNameTxt.getText());
        user.setPassword(PasswordTxt.getText());
        SignUpReq signUpReq = new SignUpReq();
        signUpReq.setUser(user);
        try {
            String jRequest = mapper.writeValueAsString(signUpReq);

        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
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

    public void setLabel(String msg){
        invalidinput.setText(msg);
    }
}
