package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.Invitation;
import com.iti.tictactoeclient.models.Match;
import com.iti.tictactoeclient.models.Player;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.notification.AskToResumeNotification;
import com.iti.tictactoeclient.requests.*;
import com.iti.tictactoeclient.responses.AskToResumeRes;
import com.iti.tictactoeclient.responses.InviteToGameRes;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.TrayIcon.MessageType;

import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    private Map<Integer, PlayerFullInfo> playersFullInfo;
    private PlayerFullInfo myPlayerFullInfo;
    private Map<Integer, Invitation> invitations;
    private Map<Integer, Player> sent;
    @FXML
    private ImageView imgLogo;
    @FXML
    private ImageView computer;
    @FXML
    private ImageView userimg;

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
    private Button btnMatches, btnInvite, btnLogin;

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
        // set double click action on invitations table
        tInvitation.setRowFactory(tv -> {
            TableRow<Invitation> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    if(tInvitation.getSelectionModel().getSelectedItem().getType() == Invitation.GAME_INVITATION)
                        showInvitationConfirmation();
                    else
                        respondToResumeReq();
                }
            });
            return row;
        });

    }

    public void addResumeReq(AskToResumeNotification askToResumeNotification){
        if (invitations.get(askToResumeNotification.getPlayer().getDb_id()) == null) {
            Invitation invitation = new Invitation(Invitation.RESUME_INVITATION, askToResumeNotification.getPlayer(), askToResumeNotification.getMatch());
            invitation.setName(playersFullInfo.get(askToResumeNotification.getPlayer().getDb_id()).getName());
            invitations.put(askToResumeNotification.getPlayer().getDb_id(), invitation);
            fillInvitationsTable();
            TicTacToeClient.showSystemNotification("Game Invitation",
                    playersFullInfo.get(askToResumeNotification.getPlayer().getDb_id()).getName() + "jimmy sent you game invitation.",
                    MessageType.INFO);
        }
    }
     public void respondToResumeReq()
     {
         Player player = tInvitation.getSelectionModel().getSelectedItem().getPlayer();
         Match match = tInvitation.getSelectionModel().getSelectedItem().getMatch();
         if(TicTacToeClient.showConfirmation("ShowNotification","Do you want to resume game ?","Accept","Reject"))
         {
             AcceptToResumeReq acceptToResumeReq = new AcceptToResumeReq(player, match);
             //Platform.runLater(()-> TicTacToeClient.openGameView());
             try {
                 String jRequest = TicTacToeClient.mapper.writeValueAsString(acceptToResumeReq);
                ServerListener.sendRequest(jRequest);
             } catch (JsonProcessingException e) {
                 e.printStackTrace();
             }
         }
         else{
             RejectToResumeReq rejectToResumeReq = new RejectToResumeReq(player);
             try {
                 String jRequest = TicTacToeClient.mapper.writeValueAsString(rejectToResumeReq);
                 ServerListener.sendRequest(jRequest);
             } catch (JsonProcessingException e) {
                 e.printStackTrace();
             }
         }
         invitations.remove(player.getDb_id());
         fillInvitationsTable();
     }

    // to show animation when view loaded
    public void showAnimation() {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        imgLogo.setImage(background);

        File cumputer = new File("images/computer.png");
        Image cumputerim = new Image(cumputer.toURI().toString());
        computer.setImage(cumputerim);

        File user = new File("images/player.png");
        Image userim = new Image(user.toURI().toString());
        userimg.setImage(userim);
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
        // get selected object
        PlayerFullInfo playerFullInfo = tPlayers.getSelectionModel().getSelectedItem();
        // check if the selected object is valid and not sent him invite before
        if (isValidSelection(playerFullInfo) && sent.get(playerFullInfo.getDb_id()) == null) {
            // create invite to a game request
            InviteToGameReq inviteToGameReq = new InviteToGameReq(new Player(playerFullInfo));
            try {
                // convert the request to string
                String jRequest = TicTacToeClient.mapper.writeValueAsString(inviteToGameReq);
                // send the request
                ServerListener.sendRequest(jRequest);
                // add request to sent
                sent.put(playerFullInfo.getDb_id(), playerFullInfo);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public void declineResume(AskToResumeRes askToResumeRes){
        TicTacToeClient.showSystemNotification("Resume Game, Declined!", getPlayerFullInfo(askToResumeRes.getPlayer().getDb_id()).getName()+" cannot resume game right now.", MessageType.INFO);
    }

    // to confirm user
    private void showInvitationConfirmation() {
        Invitation invitation = tInvitation.getSelectionModel().getSelectedItem();
        if (invitation.getType().equals(Invitation.GAME_INVITATION)) {
            confirmGameInvitation(invitation);
        }
    }

    private void confirmGameInvitation(Invitation invitation) {
        if (TicTacToeClient.showConfirmation(invitation.getType(), invitation.getName() + " invite you to a game.", "Accept", "Decline")) {
            // accept the invitation
            AcceptInvitationReq acceptInvitationReq = new AcceptInvitationReq(new Player(playersFullInfo.get(invitation.getPlayer().getDb_id())));
            try {
                // create the json
                String jRequest = TicTacToeClient.mapper.writeValueAsString(acceptInvitationReq);
                ServerListener.sendRequest(jRequest);
                //TicTacToeClient.gameController.setCompetitor(invitation.getPlayer());
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        } else {
            // Reject the invitation
            RejectInvitationReq rejectInvitationReq = new RejectInvitationReq(new Player(playersFullInfo.get(invitation.getPlayer().getDb_id())));
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(rejectInvitationReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        // remove the invitation from the map
        invitations.remove(invitation.getPlayer().getDb_id());
        // update the table
        fillInvitationsTable();
    }

    // to check the selected  player is valid
    private boolean isValidSelection(PlayerFullInfo playerFullInfo) {
        boolean valid = false;
        // if one selected from table
        if (playerFullInfo == null) {
            TicTacToeClient.showAlert("Error", "You have to select a player first", Alert.AlertType.ERROR);
            // if selected player is in game
        } else if (playerFullInfo.isInGame()) {
            TicTacToeClient.showAlert("Error", "You have to select a player which is not in game", Alert.AlertType.ERROR);
            // if selected player is offline
        } else if (playerFullInfo.getStatus().equals(PlayerFullInfo.OFFLINE)) {
            TicTacToeClient.showAlert("Error", "You have to select an online player", Alert.AlertType.ERROR);
        } else {
            valid = true;
        }
        return valid;
    }


    @FXML
    public void ComputerButton() {
        if (TicTacToeClient.showConfirmation("Level", "Please Select a level", "Easy", "Hard")) {
            TicTacToeClient.gameVsComputerController.startGame(true);
        } else {
            TicTacToeClient.gameVsComputerController.startGame(false);
        }



        TicTacToeClient.openGameVsComputerView();
        TicTacToeClient.gameVsComputerController.showAnimation();
    }

    @FXML
    protected void onActionGoLogin() {
        TicTacToeClient.openLoginView();
    }

    public void offline(boolean isOffline) {
        tInvitation.setDisable(isOffline);
        tPlayers.setDisable(isOffline);
        btnMatches.setDisable(isOffline);
        btnInvite.setDisable(isOffline);
        showHideLoginBtn(isOffline);
    }

    public void showHideLoginBtn(boolean isOffline){
        btnLogin.setVisible(myPlayerFullInfo == null && !isOffline);
    }

    public void notifyGameInvitation(Player player) {
        // check if received this notification before
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
        TicTacToeClient.gameController.startMatch(match);
        TicTacToeClient.openGameView();
    }

    public void onMatchButton() {
        try {
            GetMatchHistoryReq getMatchHistoryReq = new GetMatchHistoryReq();
            String jRequest = TicTacToeClient.mapper.writeValueAsString(getMatchHistoryReq);
            ServerListener.sendRequest(jRequest);
            TicTacToeClient.openMatchView();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }


    public void fromLogin(PlayerFullInfo myPlayerFullInfo, Map<Integer, PlayerFullInfo> playersFullInfo) {
        this.myPlayerFullInfo = myPlayerFullInfo;
        this.playersFullInfo = playersFullInfo;
        playersFullInfo.remove(myPlayerFullInfo.getDb_id());
        fillView();
        sent.clear();
        offline(false);
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
        if (playersFullInfo != null) {
            if (playerFullInfo.getDb_id() == myPlayerFullInfo.getDb_id()) {

                lblScore.setText(String.valueOf(playerFullInfo.getPoints()));
            } else {
                if (!playerFullInfo.getStatus().equals(playersFullInfo.get(playerFullInfo.getDb_id()).getStatus())
                        && playerFullInfo.getStatus().equals(PlayerFullInfo.ONLINE)) {
                    TicTacToeClient.showSystemNotification("Status Updated", "Player " + playerFullInfo.getName() + " now is online.", MessageType.INFO);
                }

                playersFullInfo.put(playerFullInfo.getDb_id(), playerFullInfo);
                fillPlayersTable();
            }
        }
    }

    public PlayerFullInfo getMyPlayerFullInfo() {
        return myPlayerFullInfo;
    }

    public PlayerFullInfo getPlayerFullInfo(int id) {
        if (myPlayerFullInfo.getDb_id() == id) {
            return myPlayerFullInfo;
        }
        return playersFullInfo.get(id);
    }
}
