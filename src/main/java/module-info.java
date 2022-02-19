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
    requires org.json;
    requires com.fasterxml.jackson.databind;


    opens com.iti.tictactoeclient to javafx.fxml;
    opens com.iti.tictactoeclient.models to javafx.base;
    exports com.iti.tictactoeclient;
    exports com.iti.tictactoeclient.controllers;
    opens com.iti.tictactoeclient.controllers to javafx.fxml;
    exports com.iti.tictactoeclient.requests to com.fasterxml.jackson.databind;
    exports com.iti.tictactoeclient.models to com.fasterxml.jackson.databind;
    exports com.iti.tictactoeclient.responses to com.fasterxml.jackson.databind;
    exports com.iti.tictactoeclient.notification to com.fasterxml.jackson.databind;
}

