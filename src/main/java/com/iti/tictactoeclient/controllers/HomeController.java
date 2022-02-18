package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private static Map<Integer, PlayerFullInfo> playersFullInfo;
    private PlayerFullInfo myPlayerFullInfo;

    @FXML
    private ImageView imgLogo;

    @FXML
    private TableView<PlayerFullInfo> tPlayers;

    @FXML
    private Label lblScore;

    @FXML
    private Label lblName;


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
//    TicTacToeClient.openLoginView();

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        showAnimation();
    }

    public void showAnimation() {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        imgLogo.setImage(background);

        FadeTransition fade = new FadeTransition();
        fade.setNode(imgLogo);
        fade.setDuration(Duration.millis(1000));
        fade.setCycleCount(2);
        fade.setFromValue(1);
        fade.setToValue(0);
        fade.setAutoReverse(true);
        fade.play();
        System.out.println("here");
    }

}
