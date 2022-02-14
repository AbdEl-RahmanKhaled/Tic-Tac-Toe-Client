package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.TicTacToeClient;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

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
    protected void onActionRegister() {
        TicTacToeClient.openloginView();
    }

    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);

    }
}
