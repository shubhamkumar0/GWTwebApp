package com.example.server.book.DeleteBook;


import com.example.shared.book.DeleteBookRequest;
import com.example.shared.book.DeleteBookResponse;

import java.sql.*;

public class DeleteBookFromDataStore {
    public Boolean deleteBookFromDb(DeleteBookRequest deleteBookRequest) {
        return delete(deleteBookRequest);
    }

    private Boolean delete(DeleteBookRequest deleteBookRequest) {
        String sql="Delete from BookDetailsTable where bookId=?";

        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, deleteBookRequest.getBookId());
            ps.executeUpdate();
            conn.close();
            return true;
            } catch (SQLException ex) {
            ex.printStackTrace();
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
