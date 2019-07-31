package com.example.server.login;

import com.example.client.LoginService;
import com.example.server.login.LogIn.LoginUser;
import com.example.shared.login.UserDetails;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.example.server.login.SignUp.SignUserUp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Random;

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
    public UserDetails loginServer(String email, String password) {
        boolean ans = false;
        if(checkValidEmail(email)) {
            LoginUser loginUser = new LoginUser();
            ans = loginUser.validate(email, password);
            if(ans) {
                UserDetails userDetails = new UserDetails();
                userDetails.setEmail(email);
                userDetails.setPassword(password);
                userDetails.setSessionId(secretKeyGenerator(email, password));
                storeUserInSession(userDetails);
                return userDetails;
            }
        }
        return null;
    }

    private boolean checkValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public UserDetails loginFromSessionServer() {
        return getUserAlreadyFromSession();
    }

    private UserDetails getUserAlreadyFromSession() {
        UserDetails userDetails = new UserDetails();
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof UserDetails)
        {
            userDetails = (UserDetails) userObj;
        }
        return userDetails;
    }

    private void storeUserInSession(UserDetails user) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
    }

    private static String secretKeyGenerator (String email, String password) {
        return email.concat(password);
    }
}
