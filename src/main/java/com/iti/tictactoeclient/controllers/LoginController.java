package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Credentials;
import com.iti.tictactoeclient.requests.LoginReq;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.TransferMode;
import javafx.stage.Stage;
import com.iti.tictactoeclient.TicTacToeClient;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    private Stage stage;

    @FXML
    private Button LoginButton;
    @FXML
    private Button RegisterButton;
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

    @FXML
    public void onLoginButton() {
        // use invalidtxt to show a msg in case of invalid data
        Credentials credentials = new Credentials();
        credentials.setUserName(UserNameTxt.getText());
        credentials.setPassword(PasswordTxt.getText());
        LoginReq loginReq = new LoginReq(credentials);
        try {
            String jRequest = TicTacToeClient.mapper.writeValueAsString(loginReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        // if true
        TicTacToeClient.openHomeView();
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        TicTacToeClient.openRegisterView();
    }

}
