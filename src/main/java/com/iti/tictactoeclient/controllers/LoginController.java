package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Credentials;
import com.iti.tictactoeclient.requests.LoginReq;
import com.iti.tictactoeclient.responses.LoginRes;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import com.iti.tictactoeclient.TicTacToeClient;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginController implements Initializable {
    @FXML
    private TextField UserNameTxt;
    @FXML
    private PasswordField PasswordTxt;

    @FXML
    private Label invaliduserTxt;
    @FXML
    private ImageView backgroundimg;


    // to load img
    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(backgroundimg);
        transition.setCycleCount(2);
        transition.setByX(200);
        transition.setAutoReverse(true);
        transition.setDuration(Duration.millis(1000));
        transition.play();
    }

    public void showAnimation() {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(backgroundimg);
        transition.setCycleCount(2);
        transition.setByX(200);
        transition.setAutoReverse(true);
        transition.setDuration(Duration.millis(1000));
        transition.play();
    }

    @FXML
    public void onLoginButton() {
        invaliduserTxt.setText("");
        if (isValidInput()) {
            try {
                LoginReq loginReq = new LoginReq();
                Credentials credentials = new Credentials(UserNameTxt.getText(), PasswordTxt.getText());
                loginReq.setCredentials(credentials);
                String jRequest = TicTacToeClient.mapper.writeValueAsString(loginReq);
                ServerListener.sendRequest(jRequest);
                UserNameTxt.clear();
                PasswordTxt.clear();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else {
            UserNameTxt.setText("");
            PasswordTxt.setText("");
            UserNameTxt.requestFocus();
        }
    }

    private boolean isValidInput() {
        String regex = "^[a-z]+([_.][a-z0-9]+)*${4,}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher1 = pattern.matcher(UserNameTxt.getText().trim());
        if (UserNameTxt.getText().trim().equals("") || !matcher1.matches()) {
            invaliduserTxt.setText("Invalid username!");
            return false;
        }

        if (PasswordTxt.getText().equals("") || PasswordTxt.getText().length() < 3) {
            invaliduserTxt.setText("Invalid Password!");
            return false;
        }
        return true;
    }

    @FXML
    public void onRegisterButtonClick() {
        TicTacToeClient.openRegisterView();
    }

    @FXML
    protected void onActionGuest() {
        TicTacToeClient.homeController.offline(true);
        TicTacToeClient.homeController.showHideLoginBtn(false);
        TicTacToeClient.openHomeView();
    }

    public void handleResponse(LoginRes loginRes) {
        if (Objects.equals(loginRes.getStatus(), Response.STATUS_OK)) {
            TicTacToeClient.homeController.fromLogin(loginRes.getPlayerFullInfo(), loginRes.getPlayerFullInfoMap());
            if (TicTacToeClient.openedScene != TicTacToeClient.scenes.homeS && TicTacToeClient.openedScene != TicTacToeClient.scenes.vsComputerS) {
                TicTacToeClient.openHomeView();
            } else {
                TicTacToeClient.showSystemNotification("Back Online", "You are now online", TrayIcon.MessageType.INFO);
            }
        } else {
            invaliduserTxt.setText(loginRes.getMessage());
        }
    }


}
