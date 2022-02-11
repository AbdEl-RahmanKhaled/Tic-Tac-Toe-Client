package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.Main;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    @FXML
    private ImageView backgroundimg;

    @FXML
    private Button InviteButton;

    @FXML
    private Button LogoutButton;

    @FXML
    private TableView<?> PlayersTable;

    @FXML
    private Label ScoreLabl;

    @FXML
    private Label UserNameLabl;

    @FXML
    private Button VsCompButton;
    @FXML
    public void InviteButton() {
        Main.openGameView();

    }
    @FXML
    public void ComputerButton() {
        Main.openGameView();

    }
    @FXML
    public void LogoutButton() {
    Main.openloginView();

    }
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);

    }

}
