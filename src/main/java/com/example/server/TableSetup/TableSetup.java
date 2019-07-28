package com.example.server.TableSetup;

import java.sql.*;
import java.util.Random;

public class TableSetup {

    //populate tables
    public Connection init() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/kiTabDb","root","root123");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    public boolean areTablesAlreadyInitialized() throws ClassNotFoundException, SQLException {
        // HACK: We don't want to do the initialization of tables multiple times if they have been
        // initialized already.
        // As a quick hack, we are just checking whether the CAR_ID_1 exists in CarStatus table
        // and if so, not proceed ahead with initializing the tables.
        Connection conn = this.init();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) from BookDetailsTable");
        System.out.println(rs);
        if(rs != null){
            return true;
        } return false;
    }

    public void initBookDetailsTable() throws ClassNotFoundException {
        Random generator = new Random(1);
        String sql = "INSERT INTO BookDetailsTable(bookId,bookName,authorName,ratings,isAvailable) VALUES(?,?,?,?,?)";
        for (int i = 0; i < 1000; i++) {
//            BookDetailsTable bookDetailsTable = new BookDetailsTable();
//            bookDetailsTable.setBookId("BOOK_ID_" + i);
//            bookDetailsTable.setAuthorName("AUTHOR_NAME_" + i);
//            bookDetailsTable.setBookName("BOOK_NAME_" + i);
//            bookDetailsTable.setRatings((float) 4.0);
//            bookDetailsTable.setIsAvailable(true);
            try (Connection conn = this.init();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, "NEW_BOOK_ID_" + i);
                pstmt.setString(2, "BOOK_NAME_" + i);
                pstmt.setString(3, "AUTHOR_NAME_" + i);
                pstmt.setFloat(4, (float) 4.0);
                pstmt.setBoolean(5, true);
                pstmt.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}
