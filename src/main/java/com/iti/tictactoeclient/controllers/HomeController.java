package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Invitation;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.requests.AcceptInvitationReq;
import com.iti.tictactoeclient.requests.InviteToGameReq;
import com.iti.tictactoeclient.requests.RejectInvitationReq;
import com.iti.tictactoeclient.responses.InviteToGameRes;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.awt.TrayIcon.MessageType;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

public class HomeController implements Initializable {

    private Map<Integer, PlayerFullInfo> playersFullInfo;
    private PlayerFullInfo myPlayerFullInfo;
    private Map<Integer, Invitation> invitations;
    private Map<Integer, Player> sent;
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
    private TableView<Invitation> tInvitation;

    @FXML
    private TableColumn<Invitation, String> cFrom;

    @FXML
    private TableColumn<Invitation, String> cNotif;

    @FXML
    private Label lblScore;

    @FXML
    private Label lblName;

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        invitations = new HashMap<>();
        sent = new HashMap<>();
        cPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));
        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        cIsInGame.setCellValueFactory(new PropertyValueFactory<>("inGame"));
        cStatus.setComparator(cStatus.getComparator().reversed());

        cFrom.setCellValueFactory(new PropertyValueFactory<>("name"));
        cNotif.setCellValueFactory(new PropertyValueFactory<>("type"));

        tInvitation.setRowFactory(tv -> {
            TableRow<Invitation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    showInvitationConfirmation();
                }
            });
            return row;
        });
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
        if (isValidSelection(playerFullInfo) && sent.get(playerFullInfo.getDb_id()) == null) {
            InviteToGameReq inviteToGameReq = new InviteToGameReq(new Player(playerFullInfo));
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(inviteToGameReq);
                System.out.println(jRequest);
                ServerListener.sendRequest(jRequest);
                sent.put(playerFullInfo.getDb_id(), playerFullInfo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    private void showInvitationConfirmation() {
        Invitation invitation = tInvitation.getSelectionModel().getSelectedItem();
        if (invitation.getType().equals(Invitation.GAME_INVITATION)) {
            confirmGameInvitation(invitation);
        }
    }

    private void confirmGameInvitation(Invitation invitation) {
        if (TicTacToeClient.showConfirmation(invitation.getType(), invitation.getName() + " invite you to a game.")) {
            // accept the invitation
            AcceptInvitationReq acceptInvitationReq = new AcceptInvitationReq(invitation.getPlayer());
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(acceptInvitationReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            // Reject the invitation
            RejectInvitationReq rejectInvitationReq = new RejectInvitationReq(invitation.getPlayer());
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(rejectInvitationReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        invitations.remove(invitation.getPlayer().getDb_id());
        fillInvitationsTable();
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

        TicTacToeClient.showAlert("sdv", "dvsdvd", Alert.AlertType.ERROR);
        System.out.println(TicTacToeClient.showConfirmation("tessst", "message"));
    }

    @FXML
    public void LogoutButton() {
//    TicTacToeClient.openLoginView();
        TicTacToeClient.showSystemNotification("Tic Tac Toe", "test notification", MessageType.INFO);
    }


    public void notifyGameInvitation(Player player) {
        if (invitations.get(player.getDb_id()) == null) {
            Invitation invitation = new Invitation();
            invitation.setType(Invitation.GAME_INVITATION);
            invitation.setPlayer(player);
            invitation.setName(playersFullInfo.get(player.getDb_id()).getName());
            invitations.put(invitation.getPlayer().getDb_id(), invitation);
            fillInvitationsTable();
            TicTacToeClient.showSystemNotification("Game Invitation",
                    playersFullInfo.get(player.getDb_id()).getName() + " sent you game invitation.",
                    MessageType.INFO);
        }
    }

    public void inviteToGameResponse(InviteToGameRes inviteToGameRes) {
        if (inviteToGameRes.getStatus().equals(Response.STATUS_ERROR)) {
            TicTacToeClient.showSystemNotification("Invite To game", inviteToGameRes.getMessage(), MessageType.WARNING);
        }
        sent.remove(inviteToGameRes.getPlayer().getDb_id());
    }

    public void startGame(Match match) {
        sent.clear();
        TicTacToeClient.openGameView();
    }

    public void fromLogin(PlayerFullInfo myPlayerFullInfo, Map<Integer, PlayerFullInfo> playersFullInfo) {
        this.myPlayerFullInfo = myPlayerFullInfo;
        this.playersFullInfo = playersFullInfo;
        playersFullInfo.remove(myPlayerFullInfo.getDb_id());
        fillView();
    }

    private void fillView() {
        fillPlayersTable();
        lblName.setText(myPlayerFullInfo.getName());
        lblScore.setText(String.valueOf(myPlayerFullInfo.getPoints()));
    }

    private void fillPlayersTable() {
        tPlayers.getItems().clear();
        tPlayers.getItems().setAll(playersFullInfo.values());
        tPlayers.getSortOrder().add(cStatus);
    }

    private void fillInvitationsTable() {
        tInvitation.getItems().clear();
        tInvitation.getItems().setAll(invitations.values());
    }

    public void updateStatus(PlayerFullInfo playerFullInfo) {
        if (!playerFullInfo.getStatus().equals(playersFullInfo.get(playerFullInfo.getDb_id()).getStatus()) && playerFullInfo.getStatus().equals(PlayerFullInfo.ONLINE)) {
            TicTacToeClient.showSystemNotification("Status Updated", "Player " + playerFullInfo.getName() + " now is online.", MessageType.INFO);
        }
        playersFullInfo.put(playerFullInfo.getDb_id(), playerFullInfo);
        fillPlayersTable();
    }

    public PlayerFullInfo getMyPlayerFullInfo() {
        return myPlayerFullInfo;
    }

    public PlayerFullInfo getPlayerFullInfo(int id) {
        return playersFullInfo.get(id);
    }
}
