package com.calculator.controllers;

import com.calculator.DBUtils;
import com.calculator.components.UserContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML
    private Button comparisonButton;

    @FXML
    private Button historyButton;

    @FXML
    private Button calculatorButton;

    @FXML
    private Button homeButton;

    @FXML
    private Button logout;

    @FXML
    private Label usernameField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setUserInformation();
        initializeButtons();
    }

    private void initializeButtons() {
        logout.setOnAction(event -> {
            UserContext.clearUser();
            DBUtils.changeScene(event, "sign-in.fxml", "Sign in");
        });
        homeButton.setOnAction(event -> DBUtils.changeScene(event, "home.fxml", "Home"));
        calculatorButton.setOnAction(event -> DBUtils.changeScene(event, "calculator.fxml", "Calculator"));
        historyButton.setOnAction(event -> DBUtils.changeScene(event, "history.fxml", "History"));
        comparisonButton.setOnAction(event -> DBUtils.changeScene(event, "comparison.fxml", "Comparison"));
    }

    public void setUserInformation() {
        String username = UserContext.getUsername();
        usernameField.setText("Username: " + username);
    }
}
