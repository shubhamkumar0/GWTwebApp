package com.example.client;

import com.example.shared.login.UserDetails;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

    void signUpUser(String name, String email, String password, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg4);

    void loginServer(String email, String password, boolean login, com.google.gwt.user.client.rpc.AsyncCallback<UserDetails> arg4);

    void loginFromSessionServer(String sessionId, com.google.gwt.user.client.rpc.AsyncCallback<UserDetails> arg4);

    void logout(String session, com.google.gwt.user.client.rpc.AsyncCallback<Boolean> arg4);
}