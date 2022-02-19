package com.iti.tictactoeclient.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.TicTacToeClient;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.models.User;
import com.iti.tictactoeclient.requests.SignUpReq;
import com.iti.tictactoeclient.responses.Response;
import javafx.animation.RotateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.util.regex.*;

import java.io.File;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

import static com.iti.tictactoeclient.TicTacToeClient.mapper;
import static com.iti.tictactoeclient.TicTacToeClient.registerController;

public class RegisterController implements Initializable {

    @FXML
    private ImageView backgroundimg;

    @FXML
    private TextField FirstNameTxt;

    @FXML
    private PasswordField PasswordTxt;

    @FXML
    private Button RegiterButton;

    @FXML
    private TextField UserNameTxt;

    @FXML
    private Label invalidinput;

    public void setLabel(String msg) {
        invalidinput.setText(msg);
    }

    private static final ObjectMapper mapper = new ObjectMapper();
    String regex = "^[A-Za-z]$";
    Pattern pattern = Pattern.compile(regex);




    @FXML
    protected void onActionRegister() {
        if (isValidateInput()) {
            User user = new User();
            SignUpReq signUpReq = new SignUpReq();
            signUpReq.setUser(user);
            try {
                String jRequest = mapper.writeValueAsString(signUpReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
        else
        {
            System.out.println("not valdiated !");
            invalidinput.setText("invalid input , try again!");
        }
    }

    private boolean isValidateInput() {
        Matcher matcher1 = pattern.matcher(UserNameTxt.getText());
        Matcher matcher2 = pattern.matcher(FirstNameTxt.getText());
        Matcher matcher3 = pattern.matcher(PasswordTxt.getText());
        if (UserNameTxt.getText().equals("") || !matcher1.matches()) {
            System.out.println("1");
            String s1 = UserNameTxt.getText().trim();
            invalidinput.setText("Invalid username!");
            return true;
        }
        if (FirstNameTxt.getText().equals("") || !matcher2.matches()) {
            System.out.println("2");
            String s2 = FirstNameTxt.getText().trim();
            invalidinput.setText("Invalid name!");
            return true;
        } if (PasswordTxt.getText().equals("") || !matcher3.matches()) {
            System.out.println("3");
            String s3 = PasswordTxt.getText().trim();
            invalidinput.setText("Invalid Password!");
            return true;
        }
            return false;
    }

    @FXML
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File backfile = new File("images/7.png");
        Image background = new Image(backfile.toURI().toString());
        backgroundimg.setImage(background);
        RotateTransition rotateTransition = new RotateTransition();
        rotateTransition.setNode(backgroundimg);
        rotateTransition.setDuration(Duration.millis(1000));
        rotateTransition.setCycleCount(2);
        rotateTransition.setByAngle(360);
        rotateTransition.setAutoReverse(true);
        rotateTransition.play();
    }

    public static void signUpValidation(Response signUpRes) {
        if (Objects.equals(signUpRes.getStatus(), Response.STATUS_OK)) {
            TicTacToeClient.openLoginView();
            System.out.println("Filed to connect4");
        } else {
            TicTacToeClient.openRegisterView(signUpRes.getMessage());
            System.out.println("Filed to connect5");
        }
    }
}