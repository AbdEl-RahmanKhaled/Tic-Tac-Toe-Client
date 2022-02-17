module com.iti.tictactoeclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;


    opens com.iti.tictactoeclient to javafx.fxml;
    exports com.iti.tictactoeclient;
    exports com.iti.tictactoeclient.controllers;
    opens com.iti.tictactoeclient.controllers to javafx.fxml;
}