package com.iti.tictactoeclient.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoserController implements Initializable {

    @FXML
    private ImageView img;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        img.setImage(new Image(new File("images/loser.gif").toURI().toString()));
    }
}