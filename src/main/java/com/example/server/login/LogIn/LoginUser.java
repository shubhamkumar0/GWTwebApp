package com.example.server.login.LogIn;

import com.example.server.login.logInServiceImpl;
import com.example.shared.login.UserDetails;

import java.sql.*;

public class LoginUser {
    public UserDetails validate(String email, String password) {
        return validateUser(email, password);
    }

    private UserDetails validateUser(String email, String password) {
        String sql = "select * from UserDetails where email=? and password=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            while(rs.next()) {
                UserDetails user = new UserDetails();
                logInServiceImpl.work(rs,user);
                return user;
            }
            conn.close();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/kiTabDb","root","root123");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
}
