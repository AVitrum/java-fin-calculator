package com.calculator;

import com.calculator.components.FinancialData;
import com.calculator.components.UserContext;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DBUtils {
    private static final String DB_URL = null;
    private static final String DB_USERNAME = null;
    private static final String DB_PASSWORD = null;

    private DBUtils() {

    }

    public static void changeScene(ActionEvent event, String fxmlFile, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(DBUtils.class.getResource(fxmlFile));
            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setTitle(title);
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void signUpUser(ActionEvent event, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement psCheckUserExists = connection.prepareStatement("SELECT * FROM users WHERE username = ?");
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {

            psCheckUserExists.setString(1, username);
            ResultSet resultSet = psCheckUserExists.executeQuery();

            if (resultSet.isBeforeFirst()) {
                System.out.println("User already exists!");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("You can't use this username!");
                alert.show();
            } else {
                psInsert.setString(1, username);
                psInsert.setString(2, password);
                psInsert.executeUpdate();

                changeScene(event, "sign-in.fxml", "Login");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void loginUser(ActionEvent event, String username, String password) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT user_id, password FROM users WHERE username = ?")) {

            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                System.out.println("User not found in the db");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Provided credentials are incorrect!");
                alert.show();
            } else {
                while (resultSet.next()) {
                    int userId = resultSet.getInt("user_id");
                    String retrievedPassword = resultSet.getString("password");
                    if (retrievedPassword.equals(password)) {
                        UserContext.setUsername(username);
                        UserContext.setUserId(userId);
                        changeScene(event, "home.fxml", "Home");
                    } else {
                        System.out.println("Password did not match!");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("The provided credentials are incorrect!");
                        alert.show();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void saveDataAboutCompany(FinancialData financialData) {
        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement psInsert = connection.prepareStatement("INSERT INTO financial_data (user_id, net_income, gross_profit_margin, roa, asset_turnover, interest_coverage_ratio, quick_ratio, current_liquidity_ratio, createdAt) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)")) {

            int userId = UserContext.getUserId();
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            String currentTime = currentDateTime.format(formatter);
            System.out.println(currentTime);

            psInsert.setInt(1, userId);
            psInsert.setString(2, financialData.getNetIncome());
            psInsert.setString(3, financialData.getGrossProfitMargin());
            psInsert.setString(4, financialData.getRoa());
            psInsert.setString(5, financialData.getAssetTurnover());
            psInsert.setString(6, financialData.getInterestCoverageRatio());
            psInsert.setString(7, financialData.getQuickRatio());
            psInsert.setString(8, financialData.getCurrentLiquidityRatio());
            psInsert.setString(9, currentTime);
            psInsert.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Data is saved");
            alert.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<String> getFinancialDataDatesByUser(int userId) {
        List<String> dates = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT createdAt FROM financial_data WHERE user_id = ?")) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String createdAt = resultSet.getString("createdAt");
                dates.add(createdAt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return dates;
    }

    private static FinancialData createFinancialDataObject(ResultSet resultSet) throws SQLException {
        double netIncome = Double.parseDouble(resultSet.getString("net_income"));
        double grossProfitMargin = Double.parseDouble(resultSet.getString("gross_profit_margin"));
        double roa = Double.parseDouble(resultSet.getString("roa"));
        double assetTurnover = Double.parseDouble(resultSet.getString("asset_turnover"));
        double interestCoverageRatio = Double.parseDouble(resultSet.getString("interest_coverage_ratio"));
        double quickRatio = Double.parseDouble(resultSet.getString("quick_ratio"));
        double currentLiquidityRatio = Double.parseDouble(resultSet.getString("current_liquidity_ratio"));

        return new FinancialData(netIncome, grossProfitMargin, roa, assetTurnover,
                interestCoverageRatio, quickRatio, currentLiquidityRatio);
    }

    public static FinancialData getFinancialDataByDateAndUser(String date, int userId) {
        FinancialData financialData = null;

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM financial_data WHERE createdAt = ? AND user_id = ?")) {

            preparedStatement.setString(1, date);
            preparedStatement.setInt(2, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                financialData = createFinancialDataObject(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return financialData;
    }

    public static List<FinancialData> getAllFinancialDataByUser(int userId) {
        List<FinancialData> financialDataList = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM financial_data WHERE user_id = ?")) {

            preparedStatement.setInt(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                FinancialData financialData = createFinancialDataObject(resultSet);
                String createdAt = resultSet.getString("createdAt");
                financialData.setDate(createdAt);
                financialDataList.add(financialData);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return financialDataList;
    }

    public static FinancialData getFinancialDataByFile() {
        boolean checkedAlert = false;
        FinancialData financialData = new FinancialData();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select File to Load Data");

        Stage primaryStage = new Stage();
        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            String filePath = selectedFile.getAbsolutePath();
            try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(": ");
                    if (parts.length == 2) {
                        String fieldName = parts[0];
                        String fieldValue = parts[1];

                        switch (fieldName) {
                            case "Date" -> financialData.setDate(fieldValue);
                            case "Net Income" -> financialData.setNetIncome(fieldValue);
                            case "Gross Profit Margin" -> financialData.setGrossProfitMargin(fieldValue);
                            case "ROA" -> financialData.setRoa(fieldValue);
                            case "Asset Turnover" -> financialData.setAssetTurnover(fieldValue);
                            case "Interest Coverage Ratio" -> financialData.setInterestCoverageRatio(fieldValue);
                            case "Quick Ratio" -> financialData.setQuickRatio(fieldValue);
                            case "Current Liquidity Ratio" -> financialData.setCurrentLiquidityRatio(fieldValue);
                            default -> {
                                if (!checkedAlert) {
                                    Alert alert = new Alert(Alert.AlertType.ERROR);
                                    alert.setContentText("The file must be formatted as files are formatted when saved");
                                    alert.show();
                                    checkedAlert = true;
                                }
                            }
                        }
                    }
                }

                System.out.println("Data loaded from file: " + filePath);
            } catch (IOException e) {
                System.out.println("An error occurred while loading data from file: " + e.getMessage());
            }
        }
        return financialData;
    }

}
