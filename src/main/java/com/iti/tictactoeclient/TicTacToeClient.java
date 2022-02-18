package com.iti.tictactoeclient;

import com.iti.tictactoeclient.controllers.GameController;
import com.iti.tictactoeclient.controllers.HomeController;
import com.iti.tictactoeclient.controllers.LoginController;
import com.iti.tictactoeclient.controllers.RegisterController;
import com.iti.tictactoeclient.helpers.ServerListener;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class TicTacToeClient extends Application {
    private static Stage mainStage;
    public static RegisterController registerController;
    public static HomeController homeController;
    public static GameController gameController;
    public static LoginController loginController;
    private static final ServerListener serverListener = new ServerListener();

    @Override
    public void init() throws Exception {
        super.init();
        serverListener.setDaemon(true);
        serverListener.start();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeClient.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage = stage;
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void openRegisterView() {
        try {
            FXMLLoader fxmlLoaderrigister = new FXMLLoader(TicTacToeClient.class.getResource("Register.fxml"));
            Scene sceneRegist = new Scene(fxmlLoaderrigister.load());
            registerController = fxmlLoaderrigister.getController();
            mainStage.hide();
            mainStage.setScene(sceneRegist);
            mainStage.setTitle("Register");
            File iconfile = new File("images/7.png");
            Image icon = new Image(iconfile.toURI().toString());
            mainStage.getIcons().add(icon);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openHomeView() {
        try {
            FXMLLoader fxmlLoaderHome = new FXMLLoader(TicTacToeClient.class.getResource("Home.fxml"));
            Scene sceneHome = new Scene(fxmlLoaderHome.load());
            homeController = fxmlLoaderHome.getController();
            mainStage.hide();
            mainStage.setScene(sceneHome);
            mainStage.setTitle("Home");
            File iconfile = new File("images/88.png");
            Image icon = new Image(iconfile.toURI().toString());
            mainStage.getIcons().add(icon);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void openGameView() {
        try {
            FXMLLoader fxmlLoaderGame = new FXMLLoader(TicTacToeClient.class.getResource("Game.fxml"));
            Scene sceneHome = new Scene(fxmlLoaderGame.load());
            gameController = fxmlLoaderGame.getController();
            mainStage.hide();
            mainStage.setScene(sceneHome);
            mainStage.setTitle("TicTacToe");
            File iconfile = new File("images/7.png");
            Image icon = new Image(iconfile.toURI().toString());
            mainStage.getIcons().add(icon);
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public static void openLoginView() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeClient.class.getResource("Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            loginController = fxmlLoader.getController();
            mainStage.hide();
            mainStage.setScene(scene);
            mainStage.setTitle("login");
            File iconfile = new File("images/88.png");
            Image icon = new Image(iconfile.toURI().toString());
            mainStage.getIcons().add(icon);
            mainStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        launch();
    }
}