package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private ImageView backgroundimg;

    @FXML
    private TextArea ChatArea;

    @FXML
    private Button PauseButton;

    @FXML
    private Button SendButton;

    @FXML
    private TextField TextField;

    @FXML
    private Button b1;

    @FXML
    private Button b2;

    @FXML
    private Button b3;

    @FXML
    private Button b5;

    @FXML
    private Button b6;

    @FXML
    private Button b7;

    @FXML
    private Button b8;

    @FXML
    private Button b9;

    @FXML
    protected void onActionPause() {

    }
    @FXML
    protected void onActionChatsender() {

    }
    @FXML
    protected void onActionExite() {

    }
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);

    }


}
