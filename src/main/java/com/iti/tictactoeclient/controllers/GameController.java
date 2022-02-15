package com.iti.tictactoeclient.controllers;

import com.iti.tictactoeclient.TicTacToeClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    public static GameController gameController;
    @FXML
    private Label Player1vsplayer2label;

    @FXML
    private ImageView backgroundimg;

    @FXML
    private TextArea ChatArea;


    @FXML
    private Button PauseButton;

    @FXML
    private Button SendButton;

    @FXML
    private TextField TextField;

    @FXML
    private Button b1 ,b2 ,b3 , b4 ,b5, b6 , b7 ,b8 ,b9;

    @FXML
    public int flag1=0,flag2=0,flag3=0,flag4=0,flag5=0,flag6=0,flag7=0,flag8=0,flag9=0;
    @FXML
    public Image img;
    @FXML
    protected void onActionPause() {

    }
    @FXML
    protected void onActionChatsender() {

    }
    @FXML
    protected void onActionExite() {
     TicTacToeClient.openHomeView();
    }
    @FXML
    public void initialize (URL url, ResourceBundle resourceBundle){
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);
        ChatArea.setEditable(false);
    }

    @FXML
    protected void button1() {
         b1.setGraphic(new ImageView(img));

        }
    @FXML
    protected void button2() {
        b2.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button3() {
        b3.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button4() {
        b4.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button5() {
        b5.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button6() {
        b6.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button7() {
        b7.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button8() {
        b8.setGraphic(new ImageView(img));

    }
    @FXML
    protected void button9() {
        b9.setGraphic(new ImageView(img));
    }




}
