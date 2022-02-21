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

public class HomeController implements Initializable {

    private Map<Integer, PlayerFullInfo> playersFullInfo;
    private PlayerFullInfo myPlayerFullInfo;

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
    private Label lblScore;

    @FXML
    private Label lblName;


    public void showAnimation() {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        imgLogo.setImage(background);

        File cumputer = new File("images/computer.png");
        Image cumputerim = new Image(cumputer.toURI().toString());
        computer.setImage(cumputerim);

        File user = new File("images/player.png");
        Image userim= new Image(user.toURI().toString());
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
        TicTacToeClient.openGameView();

    }

    @FXML
    public void MatchButton() {
        TicTacToeClient.openMatchView();

    }

     public  void ChangeCell(String item, boolean empty)
     {
         System.out.println( tPlayers.getSelectionModel().getSelectedItems().get(1));
     }

    @FXML
    public void ComputerButton() {



        TicTacToeClient.openGameView();

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
