package com.calculator.components;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FinancialData {
    private String netIncome = "";
    private String grossProfitMargin = "";
    private String roa = "";
    private String assetTurnover = "";
    private String interestCoverageRatio = "";
    private String quickRatio = "";
    private String currentLiquidityRatio = "";
    private String date = "";

    public FinancialData(double netIncome, double grossProfitMargin, double roa, double assetTurnover,
                         double interestCoverageRatio, double quickRatio, double currentLiquidityRatio) {
        this.netIncome = String.valueOf(netIncome);
        this.grossProfitMargin = String.valueOf(grossProfitMargin);
        this.roa = String.valueOf(roa);
        this.assetTurnover = String.valueOf(assetTurnover);
        this.interestCoverageRatio = String.valueOf(interestCoverageRatio);
        this.quickRatio = String.valueOf(quickRatio);
        this.currentLiquidityRatio = String.valueOf(currentLiquidityRatio);
    }

    public FinancialData() {

    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNetIncome() {
        return netIncome;
    }

    public void setNetIncome(String netIncome) {
        this.netIncome = netIncome;
    }

    public String getGrossProfitMargin() {
        return grossProfitMargin;
    }

    public void setGrossProfitMargin(String grossProfitMargin) {
        this.grossProfitMargin = grossProfitMargin;
    }

    public String getRoa() {
        return roa;
    }

    public void setRoa(String roa) {
        this.roa = roa;
    }

    public String getAssetTurnover() {
        return assetTurnover;
    }

    public void setAssetTurnover(String assetTurnover) {
        this.assetTurnover = assetTurnover;
    }

    public String getInterestCoverageRatio() {
        return interestCoverageRatio;
    }

    public void setInterestCoverageRatio(String interestCoverageRatio) {
        this.interestCoverageRatio = interestCoverageRatio;
    }

    public String getQuickRatio() {
        return quickRatio;
    }

    public void setQuickRatio(String quickRatio) {
        this.quickRatio = quickRatio;
    }

    public String getCurrentLiquidityRatio() {
        return currentLiquidityRatio;
    }

    public void setCurrentLiquidityRatio(String currentLiquidityRatio) {
        this.currentLiquidityRatio = currentLiquidityRatio;
    }

    public void saveToFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Data");
        fileChooser.setInitialFileName("financial_data.txt");

        Stage primaryStage = new Stage();
        File selectedFile = fileChooser.showSaveDialog(primaryStage);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath() + ".txt";

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
                writer.write("Date: " + date);
                writer.newLine();
                writer.write("Net Income: " + netIncome);
                writer.newLine();
                writer.write("Gross Profit Margin: " + grossProfitMargin);
                writer.newLine();
                writer.write("ROA: " + roa);
                writer.newLine();
                writer.write("Asset Turnover: " + assetTurnover);
                writer.newLine();
                writer.write("Interest Coverage Ratio: " + interestCoverageRatio);
                writer.newLine();
                writer.write("Quick Ratio: " + quickRatio);
                writer.newLine();
                writer.write("Current Liquidity Ratio: " + currentLiquidityRatio);
                writer.newLine();

                System.out.println("Data saved to file: " + filePath);
            } catch (IOException e) {
                System.out.println("An error occurred while saving the data to file: " + e.getMessage());
            }
        }
    }
}
