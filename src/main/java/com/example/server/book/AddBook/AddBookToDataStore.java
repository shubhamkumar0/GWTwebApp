package com.example.server.book.AddBook;

import com.example.shared.book.AddBookRequest;
import com.example.shared.book.AddBookResponse;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddBookToDataStore {
    public Boolean addBookToDb(AddBookRequest addBookRequest) {
        return add(addBookRequest);
    }

    private Boolean add(AddBookRequest addBookRequest) {
        AddBookResponse addBookResponse = new AddBookResponse();
        String sql = "INSERT INTO BookDetailsTable(bookId,bookName,authorName,ratings,isAvailable) VALUES(?,?,?,?,?)";
        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, addBookRequest.getBookDetails().getBookId());
            pstmt.setString(2, addBookRequest.getBookDetails().getBookName());
            pstmt.setString(3, addBookRequest.getBookDetails().getAuthorName());
            pstmt.setFloat(4, addBookRequest.getBookDetails().getRatings());
            pstmt.setBoolean(5, addBookRequest.getBookDetails().getIsAvailable());
            pstmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    private Connection connect() {
        // SQLite connection string
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
