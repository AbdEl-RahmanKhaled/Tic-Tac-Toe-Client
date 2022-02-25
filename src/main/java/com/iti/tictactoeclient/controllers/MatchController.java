package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.*;
import com.iti.tictactoeclient.requests.AskToResumeReq;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.sql.Timestamp;
import java.util.*;

public class MatchController implements Initializable {
    @FXML
    private ImageView backgroundimg;

    @FXML
    private TableView<MatchTable> MatchTable;
    @FXML
    private TableColumn<Match, String> dateColumn;
    @FXML
    private TableColumn<Match, String> player1Column;
    @FXML
    private TableColumn<Match, String> player2Column;
    @FXML
    private TableColumn<Match, String> statusColumn;
    @FXML
    private TableColumn<Match, String> winnerColumn;


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
    protected void onActionBack() {
        TicTacToeClient.openHomeView();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("m_date"));
        player1Column.setCellValueFactory(new PropertyValueFactory<>("player1_Name"));
        player2Column.setCellValueFactory(new PropertyValueFactory<>("player2_Name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        winnerColumn.setCellValueFactory(new PropertyValueFactory<>("winner"));

        MatchTable.setRowFactory(tv -> {
            TableRow<MatchTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (Objects.equals(MatchTable.getSelectionModel().getSelectedItem().getStatus().toLowerCase(Locale.ROOT), com.iti.tictactoeclient.models.MatchTable.STATUS_PAUSED))) {
                    selectMatchToResume();
                }
            });
            return row;
        });

    }

    private void selectMatchToResume() {
        int db_id;
        MatchTable matchTable = MatchTable.getSelectionModel().getSelectedItem();
        if (Objects.equals(matchTable.getStatus().toLowerCase(Locale.ROOT), com.iti.tictactoeclient.models.MatchTable.STATUS_PAUSED)) {
            if (matchTable.getPlayer1_id() == TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id()) {
                db_id = matchTable.getPlayer2_id();
            } else {
                db_id = matchTable.getPlayer1_id();
            }
            long s_id = TicTacToeClient.homeController.getPlayerFullInfo(db_id).getS_id();
            boolean answer = TicTacToeClient.showConfirmation("Resume game", "Send Resume Request?", "Ok", "Cancel");
            System.out.println(answer);
            if (answer) {
                Player player = new Player(db_id, s_id);
                Match match = new Match();
                match.setM_id(matchTable.getM_id());
                AskToResumeReq askToResumeReq = new AskToResumeReq();
                askToResumeReq.setPlayer(player);
                askToResumeReq.setMatch(match);
                try {
                    String jRequest = TicTacToeClient.mapper.writeValueAsString(askToResumeReq);
                    ServerListener.sendRequest(jRequest);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void handleResponse(List<MatchTable> matchList) {
        MatchTable.getItems().clear();
        MatchTable.getItems().setAll(matchList);

    }

}
