package com.iti.tictactoeclient.controllers;

import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class GameVsComputerController implements Initializable{
    @FXML
    private ImageView computer;
    @FXML
    private ImageView userimg;
    @FXML
    private ImageView xImg;
    @FXML
    private ImageView oImg;

    @FXML
        private Button ExirButton;

        @FXML
        private Button ExirButton1;

        @FXML
        private Button b1;

        @FXML
        private Button b2;

        @FXML
        private Button b3;

        @FXML
        private Button b4;

        @FXML
        private Button b5;

        @FXML
        private Button b6;

        @FXML
        private Button b7;

        @FXML
        private Button b8;

        @FXML
        private Button b9;

        @FXML
        private ImageView backgroundimg;

        @FXML
        void button1(ActionEvent event) {

        }

        @FXML
        void button2(ActionEvent event) {

        }

        @FXML
        void button3(ActionEvent event) {

        }

        @FXML
        void button4(ActionEvent event) {

        }

        @FXML
        void button5(ActionEvent event) {

        }

        @FXML
        void button6(ActionEvent event) {

        }

        @FXML
        void button7(ActionEvent event) {

        }

        @FXML
        void button8(ActionEvent event) {

        }

        @FXML
        void button9(ActionEvent event) {

        }

        @FXML
        void onActionExite(ActionEvent event) {

        }
    public void showAnimation(){
        File backFile = new File("images/7.png");
        Image background = new Image(backFile.toURI().toString());
        backgroundimg.setImage(background);
        File cumputer = new File("images/computer.png");
        Image cumputerim = new Image(cumputer.toURI().toString());
        computer.setImage(cumputerim);

        File user = new File("images/player.png");
        Image userim= new Image(user.toURI().toString());
        userimg.setImage(userim);
        File x = new File("images/x.png");
        Image ximage = new Image(x.toURI().toString());
        xImg.setImage(ximage);

        File o = new File("images/o.png");
        Image oimage= new Image(o.toURI().toString());
        oImg.setImage(oimage);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(backgroundimg);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setCycleCount(2);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    }
