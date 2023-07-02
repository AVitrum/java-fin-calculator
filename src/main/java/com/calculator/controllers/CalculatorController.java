package com.calculator.controllers;

import com.calculator.DBUtils;
import com.calculator.components.Calculator;
import com.calculator.components.FinancialData;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class CalculatorController implements Initializable {

    @FXML
    private Label currentLiquidityRatioLabel;

    @FXML
    private Button saveButton;

    @FXML
    private TextField currentLiabilitiesField;

    @FXML
    private TextField currentAssetsField;

    @FXML
    private TextField stocksField;

    @FXML
    private Label quickRatioLabel;

    @FXML
    private TextField interestExpensesField;

    @FXML
    private Label interestCoverageRatioLabel;

    @FXML
    private Label assetTurnoverLabel;

    @FXML
    private Button calculateButton;

    @FXML
    private Label grossProfitMarginLabel;

    @FXML
    private Label roaLabel;

    @FXML
    private Label netIncomeLabel;

    @FXML
    private TextField salesProceedsField;

    @FXML
    private TextField assetsField;

    @FXML
    private TextField incomeField;

    @FXML
    private TextField expensesField;

    private boolean isCalculated = false;

    private Calculator calculator;
    private FinancialData financialData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        calculator = new Calculator();

        calculateButton.setOnAction(event -> {
            double netIncome = calculator.calculateNetIncome(incomeField.getText(), expensesField.getText());
            double grossProfitMargin = calculator.calculateGrossProfitMargin(netIncome, salesProceedsField.getText());
            double roa = calculator.calculateROA(netIncome, assetsField.getText());
            double assetTurnover = calculator.calculateAssetTurnover(assetsField.getText(),
                    salesProceedsField.getText());
            double interestCoverageRatio = calculator.calculateInterestCoverageRatio(netIncome,
                    interestExpensesField.getText());
            double quickRatio = calculator.calculateQuickRation(currentAssetsField.getText(), stocksField.getText(),
                    currentLiabilitiesField.getText());
            double currentLiquidityRatio = calculator.calculateCurrentLiquidityRatio(currentAssetsField.getText(),
                    currentLiabilitiesField.getText());
            financialData = new FinancialData(netIncome, grossProfitMargin, roa, assetTurnover, interestCoverageRatio, quickRatio, currentLiquidityRatio);

            netIncomeLabel.setText(formatValue(netIncome));
            grossProfitMarginLabel.setText(formatValue(grossProfitMargin));
            roaLabel.setText(formatValue(roa));
            assetTurnoverLabel.setText(formatValue(assetTurnover));
            interestCoverageRatioLabel.setText(formatValue(interestCoverageRatio));
            quickRatioLabel.setText(formatValue(quickRatio));
            currentLiquidityRatioLabel.setText(formatValue(currentLiquidityRatio));


            isCalculated = true;
        });

        saveButton.setOnAction(event -> {
            if (isCalculated) {
                isCalculated = false;
                DBUtils.saveDataAboutCompany(financialData);
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Data must be calculated before saving, or data calculated twice in a row " +
                        "cannot be saved. If you still want to do it, click calculate again.");
                alert.show();
            }
        });
    }

    private String formatValue(double value) {
        return value != 0 ? String.valueOf(value) : "Invalid input";
    }
}

