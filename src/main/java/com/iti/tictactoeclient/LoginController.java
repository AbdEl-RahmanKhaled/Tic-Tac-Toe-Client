package com.iti.tictactoeclient;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable {

    @FXML
    private Button LoginButton;
    @FXML
    private Button ResetButton;
    @FXML
    private TextField usernameText;
    @FXML
    private PasswordField PasswordTxt;
    // if the user data is invalied
    @FXML
    private Label invaliduserTxt;
    @FXML
    private ImageView backgroundimg;
    @FXML
    private ImageView loginimg;
    @FXML
    private ImageView passimg;
    @FXML
    private ImageView usrimg;

    // to load img
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        File backfile = new File("images/4.jpg");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);

        File loginfile = new File("images/1.png");
        Image login = new Image(loginfile.toURI().toString());
        loginimg.setImage(login);

        File userfile = new File("images/5.png");
        Image user = new Image(userfile.toURI().toString());
        usrimg.setImage(user);

        File passfile = new File("images/6.png");
        Image pass = new Image(passfile.toURI().toString());
        passimg.setImage(pass);
    }
 //write your function !!
    @FXML
    public void onLoginButtonClick() {
     // use invalidtxt to show a msg in case of invalid data
        invaliduserTxt.setText("");
    }
    @FXML
    public void onResetButtonClick() {

    }


}