package com.iti.tictactoeclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.iti.tictactoeclient.Main;
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

    }

    @FXML
    public void onLoginButton() {
        // use invalidtxt to show a msg in case of invalid data
        invaliduserTxt.setText("");
        // if true
        Main.openHomeView();
    }
    @FXML
    public void onRegisterButtonClick() throws IOException {
        Main.openRegisterView();
    }

}
