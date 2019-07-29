package com.example.server.login.LogIn;

import java.sql.*;

public class LoginUser {
    public boolean validate(String email, String password) {
        return validateUser(email, password);
    }

    private boolean validateUser(String email, String password) {
        String sql = "select * from UserDetails where email=? and password=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);
            ResultSet rs=ps.executeQuery();
            if(!rs.next()) {
                conn.close();
                return false;
            } else {
                conn.close();
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
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
