package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

public class MatchController implements Initializable {

    private Map<Integer, PlayerFullInfo> playersFullInfo;
    private PlayerFullInfo myPlayerFullInfo;

    @FXML
    private ImageView backgroundimg;

    @FXML
    private TableView<PlayerFullInfo> tPlayers;
    @FXML
    private TableColumn<PlayerFullInfo, String> cPlayerName;
    @FXML
    private TableColumn<PlayerFullInfo, String> cStatus;

    @FXML
    private Label lblScore;

    @FXML
    private Label lblName;


    public void showAnimation() {
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

    @FXML
    public void InviteButton() {
        TicTacToeClient.openGameView();

    }

    @FXML
    public void ComputerButton() {


    }

    @FXML
    public void LogoutButton() {
//    TicTacToeClient.openLoginView();

    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void fromLogin(PlayerFullInfo myPlayerFullInfo, Map<Integer, PlayerFullInfo> playersFullInfo) {
        this.myPlayerFullInfo = myPlayerFullInfo;
        this.playersFullInfo = playersFullInfo;
        playersFullInfo.remove(myPlayerFullInfo.getDb_id());
        System.out.println(playersFullInfo);
        fillView();
    }

    private void fillView() {
        cPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        tPlayers.getItems().setAll(playersFullInfo.values());
        lblName.setText(myPlayerFullInfo.getName());
        lblScore.setText(String.valueOf(myPlayerFullInfo.getPoints()));
    }

}
