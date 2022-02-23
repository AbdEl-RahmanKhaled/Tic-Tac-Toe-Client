package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.User;
import com.iti.tictactoeclient.requests.SignUpReq;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
    private TextField UserNameTxt;

    @FXML
    private Label invalidinput;

    String regex = "^[a-z]+([_.][a-z0-9]+)*${4,}";
    Pattern pattern = Pattern.compile(regex);

    @FXML
    protected void onActionRegister() {
        invalidinput.setText("");
        if (isValidInput()) {
            User user = new User();
            user.setName(FirstNameTxt.getText().trim());
            user.setUserName(UserNameTxt.getText().trim());
            user.setPassword(PasswordTxt.getText());
            SignUpReq signUpReq = new SignUpReq();
            signUpReq.setUser(user);
            try {
                String jRequest = mapper.writeValueAsString(signUpReq);
                ServerListener.sendRequest(jRequest);
                UserNameTxt.clear();
                PasswordTxt.clear();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("not validated !");
        }
    }

    @FXML
    protected void onActionBack() {
        TicTacToeClient.openLoginView();
    }

    private boolean isValidInput() {
        Matcher matcher1 = pattern.matcher(UserNameTxt.getText().trim());
        if (UserNameTxt.getText().trim().equals("") || !matcher1.matches()) {
            invalidinput.setText("Invalid username!");
            return false;
        }
        if (FirstNameTxt.getText().trim().equals("")) {
            invalidinput.setText("Invalid name!");
            return false;
        }
        if (PasswordTxt.getText().equals("") || PasswordTxt.getText().length() < 6) {
            invalidinput.setText("Invalid Password!");
            return false;
        }
        return true;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showAnimation() {
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
    public void handleResponse(Response signUpRes) {
        if (Objects.equals(signUpRes.getStatus(), Response.STATUS_OK)) {
            TicTacToeClient.openLoginView();
        } else {
            TicTacToeClient.openRegisterView();
        }
    }
}