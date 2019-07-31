package com.example.client;

import com.example.shared.login.UserDetails;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("LoginService")
public interface LoginService extends RemoteService {

    public static class Util
    {
        private static LoginServiceAsync instance;

        public static LoginServiceAsync getInstance()
        {
            if (instance == null)
            {
                instance = GWT.create(LoginService.class);
            }
            return instance;
        }
    }
    boolean signUpUser(String name, String email, String password);
    UserDetails loginServer(String email, String password, boolean login);
    UserDetails loginFromSessionServer(String sessionId);
    boolean logout(String session);
}
