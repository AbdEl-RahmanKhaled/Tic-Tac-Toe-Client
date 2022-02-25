package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.PlayerFullInfo;
import com.iti.tictactoeclient.requests.AskToResumeReq;
import com.iti.tictactoeclient.requests.Request;
import com.iti.tictactoeclient.responses.AskToPauseRes;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
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
import javafx.util.Duration;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    static String[] board;
    static String turn;
    @FXML
    private Label Player1vsplayer2label;
    @FXML
    private ImageView backgroundimg;

    @FXML
    private TextArea ChatArea;

    @FXML
    private Button b1, b2, b3, b4, b5, b6, b7, b8, b9;

    @FXML
    public int flag1 = 0, flag2 = 0, flag3 = 0, flag4 = 0, flag5 = 0, flag6 = 0, flag7 = 0, flag8 = 0, flag9 = 0;
    @FXML
    public Image img;

    @FXML
    protected void onActionPause() {
        try {
            Request askToPauseReq = new Request(Request.ACTION_ASK_TO_PAUSE);
            String jRequest = TicTacToeClient.mapper.writeValueAsString(askToPauseReq);
            ServerListener.sendRequest(jRequest);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @FXML
    protected void onActionChatsender() {

    }

    @FXML
    protected void onActionFinish() {
        TicTacToeClient.openHomeView();
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void showAnimation() {
        File backFile = new File("images/7.png");
        Image background = new Image(backFile.toURI().toString());
        backgroundimg.setImage(background);
        ChatArea.setEditable(false);

        ScaleTransition scale = new ScaleTransition();
        scale.setNode(backgroundimg);
        scale.setDuration(Duration.millis(1000));
        scale.setCycleCount(2);
        scale.setByX(0.4);
        scale.setAutoReverse(true);
        scale.play();
    }
    /////////////////////////////////////////////////////////////
    String btn1 ,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9;
   public static boolean playerTurn  = false;
    public static boolean computerWon  = false;
    public static boolean playerWon  = false;
    public static boolean playerAgain  = false;



    //      int counter = 0 ;
    @FXML
    protected void button1() {
        b1.setGraphic(new ImageView(img) );

        if(b1.getText().equals("") && playerTurn == false){
        b1.setText("X");
        playerTurn = true;
        btn1 = b1.getText();
        System.out.println(btn1);
        }else if(!b1.getText().equals("X")){
            b1.setText("O");
            playerTurn = false;
        }
        checkButton();
    }

    @FXML
    protected void button2() {
        b2.setGraphic(new ImageView(img));

        if(b2.getText().equals("") && playerTurn == false) {
            b2.setText("X");
            playerTurn = true;
            btn2 = b2.getText();
            System.out.println(btn2);
        }else if(!b2.getText().equals("X")){
            b2.setText("O");
            playerTurn = false;
        }
        checkButton();
    }

    @FXML
    protected void button3() {
        b3.setGraphic(new ImageView(img));

        if(b3.getText().equals("") && playerTurn == false) {

            b3.setText("X");
            playerTurn = true;
            btn3 = b3.getText();
            System.out.println(btn3);
        }else if(!b3.getText().equals("X")){
            b3.setText("O");
            playerTurn = false;
        }
        checkButton();
    }

    @FXML
    protected void button4() {
        b4.setGraphic(new ImageView(img));

        if(b4.getText().equals("") && playerTurn == false) {
            b4.setText("X");
            playerTurn = true;
            btn4 = b4.getText();
            System.out.println(btn4);
        }else if(!b4.getText().equals("X")){
            b4.setText("O");
            playerTurn = false;
        }
        checkButton();
    }

    @FXML
    protected void button5() {
        b5.setGraphic(new ImageView(img));

        if(b5.getText().equals("") && playerTurn == false) {

            b5.setText("X");
            playerTurn = true;
            btn5 = b5.getText();
            System.out.println(btn5);
        }else if(!b5.getText().equals("X")){
            b5.setText("O");
            playerTurn = false;
        }
        checkButton();
    }
    @FXML
    protected void button6() {
        b6.setGraphic(new ImageView(img));

        if(b6.getText().equals("") && playerTurn == false) {
            b6.setText("X");
            playerTurn = true;
            btn6 = b6.getText();
            System.out.println(btn6);
        }else if(!b6.getText().equals("X")){
            b6.setText("O");
            playerTurn = false;
        }
        checkButton();
    }


    @FXML
        protected void button7(){
            b7.setGraphic(new ImageView(img));

            if(b7.getText().equals("") && playerTurn == false) {
                b7.setText("X");
                playerTurn = true;
                btn7 = b7.getText();
                System.out.println(btn7);
            }else if(!b7.getText().equals("X")){
                b7.setText("O");
                playerTurn = false;
            }
            checkButton();
        }

    @FXML
    protected void button8() {
        b8.setGraphic(new ImageView(img));
        if(b8.getText().equals("") && playerTurn == false) {
            b8.setText("X");
            playerTurn = true;
            btn8 = b8.getText();
            System.out.println(btn8);
        }else if(!b8.getText().equals("X")){
            b8.setText("O");
            playerTurn = false;
        }
        checkButton();

    }

    @FXML
    protected void button9() {
        b9.setGraphic(new ImageView(img));
        if(b9.getText().equals("") && playerTurn == false ) {
            b9.setText("X");
            playerTurn = true;
            btn9 = b9.getText();
            System.out.println(btn9);
        }else if(!b9.getText().equals("X")){
            b9.setText("O");
            playerTurn = false;
        }
        checkButton();
    }

    public void showPauseNotification(PlayerFullInfo playerFullInfo) {
    }
    Random random = new Random();
    int computer = random.nextInt(9) + 1;



    public String checkWinner() {
        for (int a = 0; a < 8; a++) {
            String line = null;
            switch (a) {
                case 0:
                    line = b1.getText() +b2.getText() + b3.getText();
                    break;
                case 1:
                    line = b4.getText() + b5.getText() + b6.getText();
                    break;
                case 2:
                    line = b7.getText() + b8.getText() + b9.getText();
                    break;
                case 3:
                    line = b1.getText() + b4.getText() + b7.getText();
                    break;
                case 4:
                    line = b2.getText() + b5.getText() + b8.getText();
                    break;
                case 5:
                    line = b3.getText() +b6.getText() + b9.getText();
                    break;
                case 6:
                    line = b1.getText() + b5.getText() + b9.getText();
                    break;
                case 7:
                    line = b3.getText() + b5.getText() + b7.getText();
                    break;
            }
            if (line.equals("XXX")) {
                return "X";
            } else if (line.equals("OOO")) {
                return "O";
            }
        }

        final var done = "done";
        return done;
}

    public void playNewGame(){
            if(computerWon == true || playerWon == true){
                System.out.println("do you want to play again");
                if(playerAgain == true) {
                    b1.setText("");
                    b2.setText("");
                    b3.setText("");
                    b4.setText("");
                    b5.setText("");
                    b6.setText("");
                    b7.setText("");
                    b8.setText("");
                    b9.setText("");
                }
            }
        }
    protected void checkButton(){
        if(checkWinner() == "X"){
            computerWon =true;
            playerWon =true;
            playerAgain = true;
            playNewGame();
            System.out.println("X is winner");
        }else if (checkWinner() == "O"){
            computerWon =true;
            playerWon =true;
            playerAgain = true;
            playNewGame();
            System.out.println("O is winner");
        }
    }
}
