package com.iti.tictactoeclient.controllers;

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
    private TextField usernameText;
    @FXML
    private PasswordField PasswordTxt;
    // if the user data is invalied
    @FXML
    private Label invaliduserTxt;
    @FXML
    private ImageView backgroundimg;

    // to load img
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
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
        invaliduserTxt.setText("Go Away !");
        // if true
        TicTacToeClient.openHomeView();
    }
    @FXML
    public void onRegisterButtonClick() throws IOException {
        TicTacToeClient.openRegisterView();
    }

}
