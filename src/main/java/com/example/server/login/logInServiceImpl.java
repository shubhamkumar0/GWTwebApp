package com.example.server.login;

import com.example.client.LoginService;
import com.example.server.login.LogIn.LoginUser;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.example.server.login.SignUp.SignUserUp;

public class logInServiceImpl extends RemoteServiceServlet implements LoginService {

    @Override
    public boolean signUpUser(String name, String email, String password) {
        if(checkValidEmail(email)) {
            SignUserUp signUserUp = new SignUserUp();
            return signUserUp.signup(name, email, password);
        }
        return false;
    }

    @Override
    public boolean validateUser(String email, String password) {
        if(checkValidEmail(email)) {
            LoginUser loginUser = new LoginUser();
            return loginUser.validate(email, password);
        }
        return false;
    }

    private boolean checkValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }
    //check valid email both with signup and login
    //add user to userDetails with signup only
    //check if users cookie is present i.e. logged in . This is not complete thinking.
    //logout user

}
