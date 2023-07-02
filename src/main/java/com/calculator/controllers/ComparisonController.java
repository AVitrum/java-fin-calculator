package com.calculator.controllers;

import com.calculator.DBUtils;
import com.calculator.components.Calculator;
import com.calculator.components.FinancialData;
import com.calculator.components.UserContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ComparisonController implements Initializable {

    @FXML
    private Label netIncomeDifferenceLabel;
    @FXML
    private Label roaDifferenceLabel;
    @FXML
    private Label grossProfitMarginDifferenceLabel;
    @FXML
    private Label assetTurnoverDifferenceLabel;
    @FXML
    private Label interestCoverageRatioDifferenceLabel;
    @FXML
    private Label quickRatioDifferenceLabel;
    @FXML
    private Label currentLiquidityDifferenceLabel;
    @FXML
    private Label netIncomeResultLabel;
    @FXML
    private Label roaResultLabel;
    @FXML
    private Label grossProfitMarginResultLabel;
    @FXML
    private Label assetTurnoverResultLabel;
    @FXML
    private Label quickRatioResultLabel;
    @FXML
    private Label interestCoverageRatioResultLabel;
    @FXML
    private Label currentLiquidityResultRatio;
    @FXML
    private ComboBox<String> dateComboBox;
    @FXML
    private ComboBox<String> dateComboBox2;
    @FXML
    private Label netIncomeLabel;
    @FXML
    private Label roaLabel;
    @FXML
    private Label grossProfitMarginLabel;
    @FXML
    private Label assetTurnoverLabel;
    @FXML
    private Label interestCoverageRatioLabel;
    @FXML
    private Label quickRatioLabel;
    @FXML
    private Label netIncomeLabel2;
    @FXML
    private Label grossProfitMarginLabel2;
    @FXML
    private Label roaLabel2;
    @FXML
    private Label interestCoverageRatioLabel2;
    @FXML
    private Label currentLiquidityRatioLabel;
    @FXML
    private Label quickRatioLabel2;
    @FXML
    private Label assetTurnoverLabel2;
    @FXML
    private Label currentLiquidityRatioLabel2;
    @FXML
    private Label[] netIncomeLabels;
    @FXML
    private Label[] roaLabels;
    @FXML
    private Label[] grossProfitMarginLabels;
    @FXML
    private Label[] assetTurnoverLabels;
    @FXML
    private Label[] quickRatioLabels;
    @FXML
    private Label[] interestCoverageRatioLabels;
    @FXML
    private Label[] currentLiquidityRatioLabels;
    @FXML
    private Label[] resultLabels;
    @FXML Label[] differenceLabels;

    private Calculator calculator;
    private FinancialData financialData1;
    private FinancialData financialData2;
    private boolean fileGiven = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        calculator = new Calculator();
        setupComboBoxes();

        netIncomeLabels = new Label[]{netIncomeLabel, netIncomeLabel2};
        roaLabels = new Label[]{roaLabel, roaLabel2};
        grossProfitMarginLabels = new Label[]{grossProfitMarginLabel, grossProfitMarginLabel2};
        assetTurnoverLabels = new Label[]{assetTurnoverLabel, assetTurnoverLabel2};
        quickRatioLabels = new Label[]{quickRatioLabel, quickRatioLabel2};
        interestCoverageRatioLabels = new Label[]{interestCoverageRatioLabel, interestCoverageRatioLabel2};
        currentLiquidityRatioLabels = new Label[]{currentLiquidityRatioLabel, currentLiquidityRatioLabel2};

        resultLabels = new Label[]{netIncomeResultLabel, roaResultLabel, grossProfitMarginResultLabel,
                assetTurnoverResultLabel, quickRatioResultLabel, interestCoverageRatioResultLabel,
                currentLiquidityResultRatio};
        differenceLabels = new Label[] {netIncomeDifferenceLabel, roaDifferenceLabel, grossProfitMarginDifferenceLabel,
        assetTurnoverDifferenceLabel, quickRatioDifferenceLabel, interestCoverageRatioDifferenceLabel,
        currentLiquidityDifferenceLabel};

    }

    private void setupComboBoxes() {
        List<String> dates = DBUtils.getFinancialDataDatesByUser(UserContext.getUserId());
        dateComboBox.getItems().addAll(dates);
        dateComboBox2.getItems().add("File");
        dateComboBox2.getItems().addAll(dates);

        dateComboBox.setOnAction(event -> updateLabels());
        dateComboBox2.setOnAction(event -> updateLabels());
    }

    private void updateLabels() {
        String selectedDate1 = dateComboBox.getValue();
        String selectedDate2 = dateComboBox2.getValue();
        int userId = UserContext.getUserId();

        if (selectedDate1 != null) {
            financialData1 = DBUtils.getFinancialDataByDateAndUser(selectedDate1, userId);
            setFinancialDataLabels();
        }
        if (selectedDate2 != null && !selectedDate2.equals("File")) {
            financialData2 = DBUtils.getFinancialDataByDateAndUser(selectedDate2, userId);
            fileGiven = false;
            setFinancialDataLabels();
        } else if (selectedDate2 != null && !fileGiven) {
            financialData2 = DBUtils.getFinancialDataByFile();
            fileGiven = true;
            setFinancialDataLabels();
        }
        if (selectedDate1 != null && selectedDate2 != null) {
            setComparisonResultLabels();
            setComparisonDifferenceLabels();
        }
    }

    private void setFinancialDataLabels() {
        if (dateComboBox.getValue() != null) {
            setFinancialDataLabels(financialData1, netIncomeLabels[0], roaLabels[0], grossProfitMarginLabels[0],
                    assetTurnoverLabels[0], quickRatioLabels[0], interestCoverageRatioLabels[0],
                    currentLiquidityRatioLabels[0]);
        }

        if (dateComboBox2.getValue() != null) {
            setFinancialDataLabels(financialData2, netIncomeLabels[1], roaLabels[1], grossProfitMarginLabels[1],
                    assetTurnoverLabels[1], quickRatioLabels[1], interestCoverageRatioLabels[1],
                    currentLiquidityRatioLabels[1]);
        }
    }

    private void setFinancialDataLabels(FinancialData financialData, Label netIncomeLabel, Label roaLabel,
                                        Label grossProfitMarginLabel, Label assetTurnoverLabel, Label quickRatioLabel,
                                        Label interestCoverageRatioLabel, Label currentLiquidityRatioLabel) {
        if (financialData != null) {
            setLabelValue(netIncomeLabel, financialData.getNetIncome());
            setLabelValue(roaLabel, financialData.getRoa());
            setLabelValue(grossProfitMarginLabel, financialData.getGrossProfitMargin());
            setLabelValue(assetTurnoverLabel, financialData.getAssetTurnover());
            setLabelValue(quickRatioLabel, financialData.getQuickRatio());
            setLabelValue(interestCoverageRatioLabel, financialData.getInterestCoverageRatio());
            setLabelValue(currentLiquidityRatioLabel, financialData.getCurrentLiquidityRatio());
        } else {
            clearLabelValues(netIncomeLabel, roaLabel, grossProfitMarginLabel, assetTurnoverLabel,
                    quickRatioLabel, interestCoverageRatioLabel, currentLiquidityRatioLabel);
        }
    }

    private void setComparisonResultLabels() {
        for (int i = 0; i < resultLabels.length; i++) {
            setLabelValue(resultLabels[i], calculator.calculateComparison(
                    getFieldByIndex(financialData1, i), getFieldByIndex(financialData2, i)));
        }
    }

    private void setComparisonDifferenceLabels() {
        for (int i = 0; i < differenceLabels.length; i++) {
            setLabelValue(differenceLabels[i], calculator.calculateDifference(getFieldByIndex(financialData1, i),
                    getFieldByIndex(financialData2, i)));
        }
    }

    private String getFieldByIndex(FinancialData financialData, int index) {
        return switch (index) {
            case 0 -> financialData.getNetIncome();
            case 1 -> financialData.getRoa();
            case 2 -> financialData.getGrossProfitMargin();
            case 3 -> financialData.getAssetTurnover();
            case 4 -> financialData.getQuickRatio();
            case 5 -> financialData.getInterestCoverageRatio();
            case 6 -> financialData.getCurrentLiquidityRatio();
            default -> "";
        };
    }

    private void clearLabelValues(Label... labels) {
        for (Label label : labels) {
            setLabelValue(label, "");
        }
    }

    private void setLabelValue(Label label, String value) {
        label.setText(value);
    }
}