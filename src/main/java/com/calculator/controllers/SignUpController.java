package com.calculator.controllers;

import com.calculator.DBUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SignUpController implements Initializable {

    @FXML
    private PasswordField passwordPasswordField;
    @FXML
    private TextField usernameTextField;
    @FXML
    private Button signUpButton;
    @FXML
    private Button loginButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        signUpButton.setOnAction(event -> {
            if (!usernameTextField.getText().trim().isEmpty() && !passwordPasswordField.getText().isEmpty()) {
                DBUtils.signUpUser(event, usernameTextField.getText(), passwordPasswordField.getText());
            } else {
                System.out.println("Please fill in all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information for sign up!");
                alert.show();
            }
        });

        loginButton.setOnAction(event -> DBUtils.changeScene(event, "sign-in.fxml", "Log in"));
    }
}
