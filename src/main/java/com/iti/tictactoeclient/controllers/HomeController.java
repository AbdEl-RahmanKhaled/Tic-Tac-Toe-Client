package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.TicTacToeClient;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

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
    private Label playernamelabel;
    @FXML
    private Label ScoreLabel;
    @FXML
    public void InviteButton() {
        TicTacToeClient.openGameView();

    }
    @FXML
    public void ComputerButton() {
        TicTacToeClient.openGameView();

    }
    @FXML
    public void LogoutButton() {
    TicTacToeClient.openloginView();

    }
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);

        FadeTransition fade = new FadeTransition();
        fade.setNode(backgroundimg);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(2);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setAutoReverse(true);
        fade.play();

    }

}
