//module com.iti.tictactoeclient {
//    requires javafx.controls;
//    requires javafx.fxml;
//
//
//    opens com.iti.tictactoeclient to javafx.fxml;
//    exports com.iti.tictactoeclient;
//    //exports com.iti.tictactoeclient.Controller;
//    opens com.iti.tictactoeclient.Controller to javafx.fxml;
//}

module com.iti.tictactoeclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.fasterxml.jackson.annotation;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires org.json;


    opens com.iti.tictactoeclient;
    exports com.iti.tictactoeclient;
    exports com.iti.tictactoeclient.controllers;
    opens com.iti.tictactoeclient.controllers to javafx.fxml;
    opens com.iti.tictactoeclient.models;
    exports com.iti.tictactoeclient.requests;
    exports com.iti.tictactoeclient.responses;
    exports com.iti.tictactoeclient.models;
    exports com.iti.tictactoeclient.notification;
}

