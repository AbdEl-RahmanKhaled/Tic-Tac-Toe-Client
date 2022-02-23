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
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

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
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<> ("m_date"));
        player1Column.setCellValueFactory(new PropertyValueFactory<> ("player1_Name"));
        player2Column.setCellValueFactory(new PropertyValueFactory<> ("player2_Name"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<> ("status"));
        winnerColumn.setCellValueFactory(new PropertyValueFactory<> ("winner"));

        MatchTable.setRowFactory(tv -> {
            TableRow<MatchTable> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty()) && (Objects.equals(MatchTable.getSelectionModel().getSelectedItem().getStatus(), com.iti.tictactoeclient.models.MatchTable.STATUS_PAUSED)) ) {
                    MatchTable rowData = row.getItem();
                    selectMatchToResume();
                }
            });
            return row ;
        });

    }

    private void selectMatchToResume(){
        if (Objects.equals(MatchTable.getSelectionModel().getSelectedItem().getStatus(), com.iti.tictactoeclient.models.MatchTable.STATUS_PAUSED)) {
            boolean answer=TicTacToeClient.showConfirmation("Resume game", "Send Resume Request?", "Ok", "Cancel");
            System.out.println(answer);
            if(answer){
                Player player=new Player(TicTacToeClient.homeController.getMyPlayerFullInfo().getDb_id(),TicTacToeClient.homeController.getMyPlayerFullInfo().getS_id());
                Match match=new Match();
                //match.setM_id();
                AskToResumeReq askToResumeReq=new AskToResumeReq();
                askToResumeReq.setPlayer(player);
                try {
                    String jRequest=TicTacToeClient.mapper.writeValueAsString(askToResumeReq);
                    ServerListener.sendRequest(jRequest);
                } catch (JsonProcessingException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    //    public void fromLogin(PlayerFullInfo myPlayerFullInfo, Map<Integer, PlayerFullInfo> playersFullInfo) {
//        this.myPlayerFullInfo = myPlayerFullInfo;
//        this.playersFullInfo = playersFullInfo;
//        playersFullInfo.remove(myPlayerFullInfo.getDb_id());
//        System.out.println(playersFullInfo);
//        fillView();
//    }
//
//    private void fillView() {
//        cPlayerName.setCellValueFactory(new PropertyValueFactory<>("name"));
//        cStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
//        tPlayers.getItems().setAll(playersFullInfo.values());
//        lblName.setText(myPlayerFullInfo.getName());
//        lblScore.setText(String.valueOf(myPlayerFullInfo.getPoints()));
//    }
    public void handleResponse(List<MatchTable> matchList) {
        MatchTable.getItems().clear();
        MatchTable.getItems().setAll((com.iti.tictactoeclient.models.MatchTable) matchList);

    }

}
