package com.example.server.book;

import com.example.shared.book.BookDetails;

import java.awt.print.Book;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GetAllBooksFromDataStore {

    public List<BookDetails> getBooksDetails() {
        List<BookDetails> a = getBooksFromDb();
        return a;
    }

    public List<String> getBookNames() {
        return bookNamesFromDb();
    }

    public List<BookDetails> getBookDetailsByBookName(String bookName) {
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
            while(rs.next()) {
                String BName = rs.getString("bookName");
                bookNames.add(BName);
            }
            conn.close();
            return bookNames;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private List<BookDetails> getBookDetailsByBookNameFromDb(String bookName) {
        List<BookDetails> books = new ArrayList<>();

        String sql = "select * from BookDetailsTable where bookName=?";
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,bookName);
            ResultSet rs=ps.executeQuery();
            assert (rs != null);
            while (rs.next()) {
               work(books, rs);
            }
            conn.close();
            return books;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private List<BookDetails> getBooksFromDb() {
        List<BookDetails> books = new ArrayList<>();
        try {
            Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM BookDetailsTable LIMIT 0 , 10; ");
            assert(rs!=null);
            while (rs.next()) {
                work(books,rs);
            }
            conn.close();
           return books;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private void work(List<BookDetails> books, ResultSet rs) {
        BookDetails book = new BookDetails();
        String BId = null;
        String BName = null;
        String AName = null;
        Float ratings = null;
        boolean isAvail = false;
        try {
            BId = rs.getString("bookId");
            BName = rs.getString("bookName");
            AName = rs.getString("authorName");
            ratings = rs.getFloat("ratings");
            isAvail = rs.getBoolean("isAvailable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        book.setIsAvailable(isAvail);
        book.setRatings(ratings);
        book.setAuthorName(AName);
        book.setBookName(BName);
        book.setBookId(BId);
        books.add(book);
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
