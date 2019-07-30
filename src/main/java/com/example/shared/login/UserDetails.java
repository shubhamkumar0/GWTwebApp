package com.example.shared.login;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDetails implements IsSerializable {
    private static String name;
    private static String email;
    private static String password;
    private static String SessionId;

    public static String getSessionId() {
        return SessionId;
    }

    public static void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

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
