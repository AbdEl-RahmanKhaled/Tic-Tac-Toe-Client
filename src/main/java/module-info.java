module com.iti.tictactoeclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.iti.tictactoeclient to javafx.fxml;
    exports com.iti.tictactoeclient;
    exports com.iti.tictactoeclient.controllers;
    opens com.iti.tictactoeclient.controllers to javafx.fxml;
}