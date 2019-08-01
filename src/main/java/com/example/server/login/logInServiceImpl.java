package com.example.server.login;

import com.example.client.LoginService;
import com.example.server.login.LogIn.LoginUser;
import com.example.shared.login.UserDetails;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.example.server.login.SignUp.SignUserUp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.sql.*;
import java.util.Random;

public class logInServiceImpl extends RemoteServiceServlet implements LoginService {

    @Override
    public boolean signUpUser(String name, String email, String password) {
        if (checkValidEmail(email)) {
            SignUserUp signUserUp = new SignUserUp();
            return signUserUp.signup(name, email, password);
        }
        return false;
    }

    @Override
    public UserDetails loginServer(String email, String password, boolean login) {
        if (checkValidEmail(email)) {
            LoginUser loginUser = new LoginUser();
            UserDetails user = loginUser.validate(email, password);
            if (user.getName() != null) {
                user.setSessionId(secretKeyGenerator(email, password));
                user.setLoggedIn(login);
                storeUserInSession(user,login);
                return user;
            }
        }
        return null;
    }

    private boolean checkValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    @Override
    public UserDetails loginFromSessionServer(String sessionId) {
        return getUserAlreadyFromSession(sessionId);
    }

    private UserDetails getUserAlreadyFromSession(String sessionId) {
        UserDetails userDetails = new UserDetails();
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        Object userObj = session.getAttribute("user");
        if (userObj != null && userObj instanceof UserDetails) {
            userDetails = (UserDetails) userObj;
        }
        if (userDetails.getSessionId() == null) {
            //fetch from db
            String sql="select * from UserDetails where sessionId=?";
            try {
                Connection conn = connect();
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setString(1,sessionId);
                ResultSet rs=ps.executeQuery();
                assert(rs!=null);

                while (rs.next()) {
                    UserDetails user = new UserDetails();
                    work(rs, user);
                    return user;
                }
                conn.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                return null;
            }
        }
        return userDetails;
    }

    private void storeUserInSession(UserDetails user, boolean login) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession(true);
        session.setAttribute("user", user);
        //save to db
        String sql = "Update UserDetails set " +
                "sessionId=?," +
                "loggedIn=?" +
                " where email=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user.getSessionId());
            ps.setBoolean(2, login);
            ps.setString(3, user.getEmail());
            ps.executeUpdate();
            conn.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean logout(String session) {
        return logOut(session);
    }

    private boolean logOut(String sessionid) {
        HttpServletRequest httpServletRequest = this.getThreadLocalRequest();
        HttpSession session = httpServletRequest.getSession();
        session.removeAttribute("user");
        /////
        String sql = "Update UserDetails set " +
                "sessionId=?," +
                "loggedIn=?" +
                " where sessionId=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, null);
            ps.setBoolean(2, false);
            ps.setString(3, sessionid);
            ps.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private static String secretKeyGenerator(String email, String password) {
        return email.concat(password);
    }


    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/kiTabDb", "root", "root123");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public static void work(ResultSet rs, UserDetails user) {
        String sessId = null;
        String pass = null;
        String email = null;
        String name = null;
        boolean login = false;
        try {
            sessId = rs.getString("sessionId");
            pass = rs.getString("password");
            email = rs.getString("email");
            name = rs.getString("name");
            login = rs.getBoolean("loggedIn");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        user.setSessionId(sessId);
        user.setPassword(pass);
        user.setEmail(email);
        user.setName(name);
        user.setLoggedIn(login);
    }
}