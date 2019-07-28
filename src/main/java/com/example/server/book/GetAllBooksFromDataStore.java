package com.example.server.book;

import com.example.shared.book.BookDetails;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllBooksFromDataStore {

    public List<BookDetails> getBooksDetails() {
        List<BookDetails> a = getBooksFromDb();
//        String b = a.get(0).getBookName();
        return a;
    }

    public List<String> getBookNames() {
        return bookNamesFromDb();
    }

    public BookDetails getBookDetailsByBookName(String bookName) {
        return getBookDetailsByBookNameFromDb(bookName);
    }

    private List<String> bookNamesFromDb(){
        //List<BookDetails> books = new ArrayList<>();
        List<String> bookNames = new ArrayList<>();
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT bookName FROM BookDetailsTable LIMIT 0 , 10; ");
            assert(rs!=null);
            while ( rs.next() ) {
//                BookDetails bookDetails = new BookDetails();
//                String BId = rs.getString("bookId");
                String BName = rs.getString("bookName");
//                String AName = rs.getString("authorName");
//                Float ratings = rs.getFloat("ratings");
//                boolean isAvail = rs.getBoolean("isAvailable");
//                bookDetails.setBookId(BId);
//                bookDetails.setBookName(BName);
//                bookDetails.setAuthorName(AName);
//                bookDetails.setRatings(ratings);
//                bookDetails.setIsAvailable(isAvail);
//                book.add(bookDetails);
                bookNames.add(BName);
            }
            conn.close();
            return bookNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private BookDetails getBookDetailsByBookNameFromDb(String bookName) {
        BookDetails book = new BookDetails();
        String sql = "select * from BookDetailsTable where bookName=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,bookName);
            ResultSet rs=ps.executeQuery();
            assert (rs != null);
            while (rs.next()) {
                String BId = rs.getString("bookId");
                String BName = rs.getString("bookName");
                String AName = rs.getString("authorName");
                Float ratings = rs.getFloat("ratings");
                boolean isAvail = rs.getBoolean("isAvailable");
                book.setIsAvailable(isAvail);
                book.setRatings(ratings);
                book.setAuthorName(AName);
                book.setBookName(BName);
                book.setBookId(BId);
            }
            conn.close();
            return book;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<BookDetails> getBooksFromDb() {
        List<BookDetails> books = new ArrayList<>();
//        List<String> bookNames = new ArrayList<>();
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BookDetailsTable LIMIT 0 , 10; ");
            assert(rs!=null);
            while ( rs.next() ) {
                BookDetails bookDetails = new BookDetails();
                String BId = rs.getString("bookId");
                String BName = rs.getString("bookName");
                String AName = rs.getString("authorName");
                Float ratings = rs.getFloat("ratings");
                boolean isAvail = rs.getBoolean("isAvailable");
                bookDetails.setBookId(BId);
                bookDetails.setBookName(BName);
                bookDetails.setAuthorName(AName);
                bookDetails.setRatings(ratings);
                bookDetails.setIsAvailable(isAvail);
                books.add(bookDetails);
//                bookNames.add(BName);
            }
            conn.close();
           return books;
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
