package com.example.server.login.Tables;

public class SessionTable {
    private static String email;
    private static String cookieHash;

    public static String getEmail() {
        return email;
    }

    public static void setEmail(String email) {
        SessionTable.email = email;
    }

    public static String getCookieHash() {
        return cookieHash;
    }

    public static void setCookieHash(String cookieHash) {
        SessionTable.cookieHash = cookieHash;
    }
}
