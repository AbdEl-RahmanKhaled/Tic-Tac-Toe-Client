package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.requests.GetMatchHistoryReq;
import com.iti.tictactoeclient.requests.InviteToGameReq;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

import java.io.File;
import java.net.URL;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Map<Integer, PlayerFullInfo> playersFullInfo;

    private PlayerFullInfo myPlayerFullInfo;
    @FXML
    private ImageView imgLogo;

    @FXML
    private TableView<PlayerFullInfo> tPlayers;
    @FXML
    private TableColumn<PlayerFullInfo, String> cPlayerName;
    @FXML
    private TableColumn<PlayerFullInfo, String> cStatus;
    @FXML
    private TableColumn<PlayerFullInfo, Boolean> cIsInGame;

    @FXML
    private Label lblScore;

    @FXML
    private Label lblName;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        cIsInGame.setCellValueFactory(new PropertyValueFactory<>("inGame"));
        cStatus.setComparator(cStatus.getComparator().reversed());
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
    }

    @FXML
    public void InviteButton() {
        PlayerFullInfo playerFullInfo = tPlayers.getSelectionModel().getSelectedItem();
        if (isValidSelection(playerFullInfo)) {
            InviteToGameReq inviteToGameReq = new InviteToGameReq(new Player(playerFullInfo));
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(inviteToGameReq);
                System.out.println(jRequest);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isValidSelection(PlayerFullInfo playerFullInfo) {
        boolean valid = false;

        if (playerFullInfo == null) {
            TicTacToeClient.showAlert("Error", "You have to select a player first", Alert.AlertType.ERROR);
        } else if (playerFullInfo.isInGame()) {
            TicTacToeClient.showAlert("Error", "You have to select a player which is not in game", Alert.AlertType.ERROR);
        } else if (playerFullInfo.getStatus().equals(PlayerFullInfo.OFFLINE)) {
            TicTacToeClient.showAlert("Error", "You have to select an online player", Alert.AlertType.ERROR);
        } else {
            valid = true;
        }
        return valid;
    }

    @FXML
    public void ComputerButton() {
        TicTacToeClient.openGameView();

    }

    @FXML
    public void LogoutButton() {
//    TicTacToeClient.openLoginView();
        TicTacToeClient.showSystemNotification("Tic Tac Toe", "test notification", MessageType.INFO);
    }


    public void notifyGameInvitation(Player player) {


    }

    public void onMatchButton() {
        try {
            GetMatchHistoryReq getMatchHistoryReq = new GetMatchHistoryReq();
            String jRequest = TicTacToeClient.mapper.writeValueAsString(getMatchHistoryReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public void fromLogin(PlayerFullInfo myPlayerFullInfo, Map<Integer, PlayerFullInfo> playersFullInfo) {
        this.myPlayerFullInfo = myPlayerFullInfo;
        this.playersFullInfo = playersFullInfo;
        playersFullInfo.remove(myPlayerFullInfo.getDb_id());
        System.out.println(playersFullInfo);
        fillView();
    }

    private void fillView() {
        fillTable();
        lblName.setText(myPlayerFullInfo.getName());
        lblScore.setText(String.valueOf(myPlayerFullInfo.getPoints()));
    }

    private void fillTable() {
        tPlayers.getItems().clear();
        tPlayers.getItems().setAll(playersFullInfo.values());
        tPlayers.getSortOrder().add(cStatus);
    }

    public void updateStatus(PlayerFullInfo playerFullInfo) {
        if (!playerFullInfo.getStatus().equals(playersFullInfo.get(playerFullInfo.getDb_id()).getStatus()) && playerFullInfo.getStatus().equals(PlayerFullInfo.ONLINE)) {
            TicTacToeClient.showSystemNotification("Status Updated", "Player " + playerFullInfo.getName() + " now is online.", MessageType.INFO);
        }
        playersFullInfo.put(playerFullInfo.getDb_id(), playerFullInfo);
        fillTable();
    }

    public PlayerFullInfo getMyPlayerFullInfo() {
        return myPlayerFullInfo;
    }


}