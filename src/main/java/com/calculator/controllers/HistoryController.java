package com.calculator.controllers;

import com.calculator.DBUtils;
import com.calculator.components.FinancialData;
import com.calculator.components.UserContext;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryController implements Initializable {

    @FXML
    private Button saveToFileButton;
    @FXML
    private ComboBox<String> dateComboBox;
    @FXML
    private TableView<FinancialData> table;
    @FXML
    private TableColumn<FinancialData, String> dateColumn;
    @FXML
    private TableColumn<FinancialData, String> netIncomeColumn;
    @FXML
    private TableColumn<FinancialData, String> grossProfitMarginColumn;
    @FXML
    private TableColumn<FinancialData, String> roaColumn;
    @FXML
    private TableColumn<FinancialData, String> assetTurnoverColumn;
    @FXML
    private TableColumn<FinancialData, String> interestCoverageRatioColumn;
    @FXML
    private TableColumn<FinancialData, String> quickRatioColumn;
    @FXML
    private TableColumn<FinancialData, String> currentLiquidityRatioColumn;

    private FinancialData financialData;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        netIncomeColumn.setCellValueFactory(new PropertyValueFactory<>("netIncome"));
        grossProfitMarginColumn.setCellValueFactory(new PropertyValueFactory<>("grossProfitMargin"));
        roaColumn.setCellValueFactory(new PropertyValueFactory<>("roa"));
        assetTurnoverColumn.setCellValueFactory(new PropertyValueFactory<>("assetTurnover"));
        interestCoverageRatioColumn.setCellValueFactory(new PropertyValueFactory<>("interestCoverageRatio"));
        quickRatioColumn.setCellValueFactory(new PropertyValueFactory<>("quickRatio"));
        currentLiquidityRatioColumn.setCellValueFactory(new PropertyValueFactory<>("currentLiquidityRatio"));

        int userId = UserContext.getUserId();
        List<FinancialData> financialDataList = DBUtils.getAllFinancialDataByUser(userId);
        List<String> dates = DBUtils.getFinancialDataDatesByUser(userId);
        dateComboBox.getItems().addAll(dates);
        table.getItems().addAll(financialDataList);

        saveToFileButton.setOnAction(event -> {
            String selectedDate = dateComboBox.getValue();
            if (selectedDate != null) {
                financialData = DBUtils.getFinancialDataByDateAndUser(selectedDate, userId);
                financialData.setDate(selectedDate);
                financialData.saveToFile();
            }
        });
    }

}
