package com.iti.tictactoeclient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iti.tictactoeclient.controllers.*;
import com.iti.tictactoeclient.helpers.ServerListener;
import com.iti.tictactoeclient.requests.UpdateInGameStatusReq;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.net.URL;

public class TicTacToeClient extends Application {
    private static Stage mainStage;
    private static Scene sceneRegister, sceneHome, sceneGame, sceneLogin, sceneMatch, sceneGameVsComputer;
    private URL url;
    private String css;
    public static RegisterController registerController;
    public static HomeController homeController;
    public static GameController gameController;
    public static LoginController loginController;
    public static MatchController matchController;
    public static GameVsComputerController gameVsComputerController;
    private static final ServerListener serverListener = new ServerListener();
    public static final ObjectMapper mapper = new ObjectMapper();
    private static TrayIcon trayIcon;
    private SystemTray tray;

    public enum scenes {registerS, loginS, homeS, matchS, vsPlayerS, vsComputerS}

    public static scenes openedScene;

    @Override
    public void init() throws Exception {
        super.init();
        initViews();
        initTray();
        serverListener.setDaemon(true);
        serverListener.start();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TicTacToeClient.class.getResource("Login.fxml"));
        sceneLogin = new Scene(fxmlLoader.load());
        loginController = fxmlLoader.getController();
        url = this.getClass().getResource("Style.css");
        css = url.toExternalForm();
        sceneLogin.getStylesheets().add(css);
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage = stage;
        stage.setTitle("Login");
        stage.setResizable(false);
        stage.setScene(sceneLogin);
        stage.getIcons().add(icon);
        openedScene = scenes.loginS;
        stage.show();
    }

    private void initViews() {
        try {
            // Register view
            FXMLLoader fxmlLoaderrigister = new FXMLLoader(TicTacToeClient.class.getResource("Register.fxml"));
            sceneRegister = new Scene(fxmlLoaderrigister.load());
            registerController = fxmlLoaderrigister.getController();
            url = this.getClass().getResource("Style.css");
            assert url != null;
            css = url.toExternalForm();
            sceneRegister.getStylesheets().add(css);

            // Home view
            FXMLLoader fxmlLoaderHome = new FXMLLoader(TicTacToeClient.class.getResource("Home.fxml"));
            sceneHome = new Scene(fxmlLoaderHome.load());
            homeController = fxmlLoaderHome.getController();
            sceneHome.getStylesheets().add(css);

            // Game View
            FXMLLoader fxmlLoaderGame = new FXMLLoader(TicTacToeClient.class.getResource("Game.fxml"));
            sceneGame = new Scene(fxmlLoaderGame.load());
            gameController = fxmlLoaderGame.getController();
            sceneGame.getStylesheets().add(css);

            // GameVsComputer View
            FXMLLoader fxmlLoaderGameVsComputer = new FXMLLoader(TicTacToeClient.class.getResource("GameVsComputer.fxml"));
            sceneGameVsComputer = new Scene(fxmlLoaderGameVsComputer.load());
            gameVsComputerController = fxmlLoaderGameVsComputer.getController();
            sceneGameVsComputer.getStylesheets().add(css);


            // Match View
            FXMLLoader fxmlLoaderMatch = new FXMLLoader(TicTacToeClient.class.getResource("Match.fxml"));
            sceneMatch = new Scene(fxmlLoaderMatch.load());
            matchController = fxmlLoaderMatch.getController();
            sceneMatch.getStylesheets().add(css);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initTray() {
        //Obtain only one instance of the SystemTray object
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();

            //If the icon is a file
            java.awt.Image image = Toolkit.getDefaultToolkit().getImage("images/7.png");

            trayIcon = new TrayIcon(image, "Tic Tac Toe Game");

            //Let the system resize the image if needed
            trayIcon.setImageAutoSize(true);
            //Set tooltip text for the tray icon
            trayIcon.setToolTip("Tic Tac Toe Game");
            trayIcon.addMouseListener(new MouseListener() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    Platform.runLater(() -> {
                        mainStage.requestFocus();
                        mainStage.toFront();
                    });
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
            try {
                tray.add(trayIcon);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

    public static void showSystemNotification(String title, String message, TrayIcon.MessageType messageType) {
        if (SystemTray.isSupported()) {
            trayIcon.displayMessage(title, message, messageType);
        } else {
            System.err.println("System tray not supported!");
        }
    }

    public static void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("");
        alert.showAndWait();
    }

    public static void showAlert(String title, String type) {

        FXMLLoader loader = new FXMLLoader(TicTacToeClient.class.getResource(type + ".fxml"));
        try {
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(title);
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void sendUpdateInGameStatus(boolean isInGame) {
        if (homeController.getMyPlayerFullInfo() != null) {
            UpdateInGameStatusReq updateInGameStatusReq = new UpdateInGameStatusReq(isInGame);
            try {
                String jRequest = TicTacToeClient.mapper.writeValueAsString(updateInGameStatusReq);
                ServerListener.sendRequest(jRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    public static boolean showConfirmation(String title, String message, String btn1, String btn2) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "",
                new ButtonType(btn1, ButtonBar.ButtonData.OK_DONE),
                new ButtonType(btn2, ButtonBar.ButtonData.CANCEL_CLOSE));
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.setContentText("");
        Optional<ButtonType> s = alert.showAndWait();
        final boolean[] isOk = new boolean[1];
        s.ifPresent(buttonType -> {
            isOk[0] = buttonType.getButtonData().isDefaultButton();
        });
        return isOk[0];
    }

    public static void openHomeView() {
        mainStage.hide();
        mainStage.setScene(sceneHome);
        mainStage.setTitle("Home");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        openedScene = scenes.homeS;
        mainStage.show();
        homeController.showAnimation();
    }

    public static void openMatchView() {
        mainStage.hide();
        mainStage.setScene(sceneMatch);
        mainStage.setTitle("Match");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        openedScene = scenes.matchS;
        mainStage.show();
        matchController.showAnimation();
    }

    public static void openGameView() {
        mainStage.hide();
        mainStage.setScene(sceneGame);
        mainStage.setTitle("TicTacToe");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        openedScene = scenes.vsPlayerS;
        mainStage.show();
        gameController.showAnimation();
    }

    public static void openGameVsComputerView() {
        mainStage.hide();
        mainStage.setScene(sceneGameVsComputer);
        mainStage.setTitle("TicTacToe");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        gameVsComputerController.showAnimation();
        openedScene = scenes.vsComputerS;
        mainStage.show();
    }

    public static void openLoginView() {
        mainStage.hide();
        mainStage.setScene(sceneLogin);
        mainStage.setTitle("login");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        loginController.showAnimation();
        openedScene = scenes.loginS;
        mainStage.show();
    }

    public static void openRegisterView() {
        mainStage.hide();
        mainStage.setScene(sceneRegister);
        mainStage.setTitle("Register");
        File iconfile = new File("images/7.png");
        Image icon = new Image(iconfile.toURI().toString());
        mainStage.getIcons().add(icon);
        registerController.showAnimation();
        openedScene = scenes.registerS;
        mainStage.show();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        tray.remove(trayIcon);
        serverListener.interrupt();
    }

}