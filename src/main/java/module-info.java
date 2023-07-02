module com.example.calculator {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.calculator to javafx.fxml;
    exports com.calculator;
    exports com.calculator.controllers;
    opens com.calculator.controllers to javafx.fxml;
    exports com.calculator.components;
    opens com.calculator.components to javafx.fxml;
}