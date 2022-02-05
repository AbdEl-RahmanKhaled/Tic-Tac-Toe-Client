module com.iti.tictactoeclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.iti.tictactoeclient to javafx.fxml;
    exports com.iti.tictactoeclient;
}