package com.example.server.login.Tables;

public class UserDetails {
    private static String name;
    private static String email;
    private static String password;

    public static String getName() {
        return name;
    }

    public static void setName(String name) {
        UserDetails.name = name;
    }

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        UserDetails.email = email;
    }

    public static String getPassword() {
        return password;
    }

    public static void setPassword(String password) {
        UserDetails.password = password;
    }
}
