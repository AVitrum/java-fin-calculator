package com.calculator.components;

public class UserContext {

    private static String username;
    private static int userId;

    private UserContext() {

    }

    public static void setUsername(String userInfo) {
        username = userInfo;
    }

    public static String getUsername() {
        return username;
    }

    public static void setUserId(int id) {
        userId = id;
    }

    public static int getUserId() {
        return userId;
    }

    public static void clearUser() {
        username = null;
        userId = 0;
    }
}
