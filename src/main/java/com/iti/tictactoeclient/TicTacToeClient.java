package com.iti.tictactoeclient;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private static Scene sceneRegister, sceneHome, sceneGame, sceneLogin;
    public static RegisterController registerController;
    public static HomeController homeController;
    public static GameController gameController;
    public static LoginController loginController;
    private static final ServerListener serverListener = new ServerListener();
    public static final ObjectMapper mapper = new ObjectMapper();


    @Override
    public void init() throws Exception {
        super.init();
        initViews();
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

    private void initViews() {
        try {
            // Register view
            FXMLLoader fxmlLoaderrigister = new FXMLLoader(TicTacToeClient.class.getResource("Register.fxml"));
            sceneRegister = new Scene(fxmlLoaderrigister.load());
            registerController = fxmlLoaderrigister.getController();

            // Home view
            FXMLLoader fxmlLoaderHome = new FXMLLoader(TicTacToeClient.class.getResource("Home.fxml"));
            sceneHome = new Scene(fxmlLoaderHome.load());
            homeController = fxmlLoaderHome.getController();

            // Game View
            FXMLLoader fxmlLoaderGame = new FXMLLoader(TicTacToeClient.class.getResource("Game.fxml"));
            sceneGame = new Scene(fxmlLoaderGame.load());
            gameController = fxmlLoaderGame.getController();

            // Login View
            FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeClient.class.getResource("Login.fxml"));
            sceneLogin = new Scene(fxmlLoader.load());
            loginController = fxmlLoader.getController();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void openHomeView() {
        mainStage.hide();
        mainStage.setScene(sceneHome);
        mainStage.setTitle("Home");
        File iconfile = new File("images/88.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        mainStage.show();
        homeController.showAnimation();
    }

    public static void openGameView() {
        mainStage.hide();
        mainStage.setScene(sceneGame);
        mainStage.setTitle("TicTacToe");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        mainStage.show();
    }

    public static void openLoginView() {
        mainStage.hide();
        mainStage.setScene(sceneLogin);
        mainStage.setTitle("login");
        File iconfile = new File("images/88.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        mainStage.show();
    }

    public static void openRegisterView() {
        mainStage.hide();
        mainStage.setScene(sceneRegister);
        mainStage.setTitle("Register");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        mainStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}