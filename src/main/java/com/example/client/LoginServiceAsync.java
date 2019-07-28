package com.example.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

    void signUpUser(String name, String email, String password, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg4);

    void validateUser(String email, String password, com.google.gwt.user.client.rpc.AsyncCallback<java.lang.Boolean> arg4);
}