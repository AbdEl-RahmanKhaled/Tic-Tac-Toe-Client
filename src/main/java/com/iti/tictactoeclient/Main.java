package com.iti.tictactoeclient;

import com.iti.tictactoeclient.controllers.GameController;
import com.iti.tictactoeclient.controllers.HomeController;
import com.iti.tictactoeclient.controllers.LoginController;
import com.iti.tictactoeclient.controllers.RegisterController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    private static Stage mainStage;
    public static RegisterController registerController;
    public static HomeController homeController;
    public static GameController gameController;
    public static LoginController loginController;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        mainStage = stage;
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void openRegisterView() {
        try {
            FXMLLoader fxmlLoaderrigister = new FXMLLoader(Main.class.getResource("Register.fxml"));
            Scene sceneRegist = new Scene(fxmlLoaderrigister.load());
            registerController = fxmlLoaderrigister.getController();
            mainStage.hide();
            mainStage.setScene(sceneRegist);
            mainStage.setTitle("Register");
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openHomeView() {
        try {
            FXMLLoader fxmlLoaderHome = new FXMLLoader(Main.class.getResource("Home.fxml"));
            Scene sceneHome = new Scene(fxmlLoaderHome.load());
            homeController = fxmlLoaderHome.getController();
            mainStage.hide();
            mainStage.setScene(sceneHome);
            mainStage.setTitle("Home");
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openGameView() {
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(Main.class.getResource("Game.fxml"));
            Scene sceneHome = new Scene(fxmlLoaderGame.load());
            gameController = fxmlLoaderGame.getController();
            mainStage.hide();
            mainStage.setScene(sceneHome);
            mainStage.setTitle("TicTacToe");
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void openloginView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            loginController = fxmlLoader.getController();
            mainStage.hide();
            mainStage.setScene(scene);
            mainStage.setTitle("login");
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}