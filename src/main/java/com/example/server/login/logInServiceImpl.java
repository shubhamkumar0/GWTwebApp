package com.example.server.login;

import com.example.client.LoginService;
import com.example.server.login.LogIn.LoginUser;
import com.example.shared.login.UserDetails;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.example.server.login.SignUp.SignUserUp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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
        boolean ans = false;
        if(checkValidEmail(email)) {
            LoginUser loginUser = new LoginUser();
            ans = loginUser.validate(email, password);
//            if(ans) {
//                UserDetails userDetails = new UserDetails();
//                userDetails.setEmail(email);
//                userDetails.setPassword(password);
//                storeUserInSession(userDetails);
//            }
        }
        return ans;
    }

    private boolean checkValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

//    @Override
//    public UserDetails loginFromSessionServer()
//    {
//        return getUserAlreadyFromSession();
//    }

//    private UserDetails getUserAlreadyFromSession()
//    {
//        UserDetails userDetails = new UserDetails();
//        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
//        HttpSession session = httpServletRequest.getSession();
//        Object userObj = session.getAttribute("user");
//        if (userObj != null && userObj instanceof UserDetails)
//        {
//            userDetails = (UserDetails) userObj;
//        }
//        return userDetails;
//    }

//    private void storeUserInSession(UserDetails user)
//    {
//        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
//        HttpSession session = httpServletRequest.getSession(true);
//        session.setAttribute("user", user);
//    }



}
