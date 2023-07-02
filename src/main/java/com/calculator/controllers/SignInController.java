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

public class SignInController implements Initializable {

    @FXML
    private Button login_button;

    @FXML
    private Button sign_up_button;

    @FXML
    private TextField username_textField;

    @FXML
    private PasswordField password_passwordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        login_button.setOnAction(event -> {
            if (username_textField.getText().trim().isEmpty() || password_passwordField.getText().isEmpty()) {
                System.out.println("Please fill in all information");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Please fill in all information for sign up!");
                alert.show();
            } else {
                DBUtils.loginUser(event, username_textField.getText(), password_passwordField.getText());
            }
        });

        sign_up_button.setOnAction(event -> DBUtils.changeScene(event, "sign-up.fxml", "Sign up"));
    }
}
