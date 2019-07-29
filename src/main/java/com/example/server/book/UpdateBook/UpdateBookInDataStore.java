package com.example.server.book.UpdateBook;

import com.example.shared.book.UpdateBookRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UpdateBookInDataStore {
    public Boolean update(String orig_id, UpdateBookRequest updateBookRequest) {
        return updateBookInDb(orig_id, updateBookRequest);
    }

    private boolean updateBookInDb(String orig_id, UpdateBookRequest updateBookRequest) {
        String sql = "Update BookDetailsTable set " +
                "bookId=?," +
                " bookName=?," +
                " authorName=?," +
                " ratings=?" +
                " where bookId=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, updateBookRequest.getBookDetails().getBookId());
            ps.setString(2, updateBookRequest.getBookDetails().getBookName());
            ps.setString(3, updateBookRequest.getBookDetails().getAuthorName());
            ps.setFloat(4, updateBookRequest.getBookDetails().getRatings());
            ps.setString(5,orig_id);
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
