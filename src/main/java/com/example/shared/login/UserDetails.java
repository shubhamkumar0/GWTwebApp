package com.example.shared.login;

import com.example.shared.book.BookDetails;
import com.google.gwt.user.client.rpc.IsSerializable;

public class UserDetails implements IsSerializable {
    private  String name;
    private  String email;
    private  String password;
    private  String SessionId;
    private boolean loggedIn;

    public  String getSessionId() {
        return SessionId;
    }

    public  void setSessionId(String sessionId) {
        SessionId = sessionId;
    }

    public  String getName() {
        return name;
    }

    public  void setName(String name) {
        this.name = name;
    }

    public  String getEmail() {
        return email;
    }

    public  void setEmail(String email) {
        this.email = email;
    }

    public  String getPassword() {
        return password;
    }

    public  void setPassword(String password) {
        this.password = password;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }
    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
