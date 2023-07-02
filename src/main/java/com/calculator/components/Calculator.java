package com.calculator.components;

public class Calculator {

    public double calculateNetIncome(String incomeStr, String expensesStr) {
        try {
            double income = Double.parseDouble(incomeStr);
            double expenses = Double.parseDouble(expensesStr);
            return round(income - expenses);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double calculateGrossProfitMargin(double netIncome, String salesProceedsStr) {
        try {
            double salesProceeds = Double.parseDouble(salesProceedsStr);
            return round(salesProceeds != 0 ? netIncome / salesProceeds : 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double calculateROA(double netIncome, String assetsStr) {
        try {
            double assets = Double.parseDouble(assetsStr);
            return round(assets != 0 ? netIncome / assets : 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double calculateAssetTurnover(String assetsStr, String salesProceedsStr) {
        try {
            double salesProceeds = Double.parseDouble(salesProceedsStr);
            double assets = Double.parseDouble(assetsStr);
            return round(assets != 0 ? salesProceeds / assets : 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double calculateInterestCoverageRatio(double netIncome, String interestExpensesStr) {
        try {
            double interestExpenses = Double.parseDouble(interestExpensesStr);
            return round(interestExpenses != 0 ? netIncome / interestExpenses : 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double calculateQuickRation(String currentAssetsStr, String stocksStr, String currentLiabilitiesStr) {
        try {
            double currentAssets = Double.parseDouble(currentAssetsStr);
            double stock = Double.parseDouble(stocksStr);
            double currentLiabilities = Double.parseDouble(currentLiabilitiesStr);
            return round(currentLiabilities != 0 ? (currentAssets - stock) / currentLiabilities : 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public double calculateCurrentLiquidityRatio(String currentAssetsStr, String currentLiabilitiesStr) {
        try {
            double currentAssets = Double.parseDouble(currentAssetsStr);
            double currentLiabilities = Double.parseDouble(currentLiabilitiesStr);
            return round(currentLiabilities != 0 ? currentAssets / currentLiabilities : 0);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public String calculateComparison(String currentStr, String pastStr) {
        try {
            double current = Double.parseDouble(currentStr);
            double past = Double.parseDouble(pastStr);
            return String.valueOf(round(current != 0 ? ((current - past) / current) * 100 : 0));
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public String calculateDifference(String currentStr, String pastStr) {
        try {
            double current = Double.parseDouble(currentStr);
            double past = Double.parseDouble(pastStr);
            return String.valueOf(round(current - past));
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    private double round(double value) {
        return Math.round(value * 100.0) / 100.0;
    }

}
