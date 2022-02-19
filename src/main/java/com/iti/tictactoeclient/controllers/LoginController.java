package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Credentials;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.requests.LoginReq;
import com.iti.tictactoeclient.responses.LoginRes;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.iti.tictactoeclient.TicTacToeClient;
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class LoginController implements Initializable {
    private static final ObjectMapper mapper = new ObjectMapper();
    private Stage stage;

    @FXML
    private Button LoginButton;
    @FXML
    private Button RegisterButton;
    @FXML
    private TextField UserNameTxt;
    @FXML
    private PasswordField PasswordTxt;

    // if the user data is invalied
    // if the user data is invalid
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

        try {
            LoginReq loginReq = new LoginReq();
            Credentials credentials = new Credentials(UserNameTxt.getText(), PasswordTxt.getText());
            loginReq.setCredentials(credentials);
            String jRequest = mapper.writeValueAsString(loginReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onRegisterButtonClick() throws IOException {
        TicTacToeClient.openRegisterView("");
    }

    public void handleResponse(LoginRes loginRes) {
        if (Objects.equals(loginRes.getStatus(), Response.STATUS_OK)) {
            TicTacToeClient.openHomeView();
            TicTacToeClient.homeController.fromLogin(loginRes.getPlayerFullInfo(), loginRes.getPlayerFullInfoMap());
        } else {
            invaliduserTxt.setText(loginRes.getMessage());
        }
    }

}
