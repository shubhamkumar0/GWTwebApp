package com.example.server.search;

import com.example.shared.book.BookDetails;
import com.example.shared.search.SearchRequest;
import com.example.shared.search.SearchResponse;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SearchByName {
    public SearchResponse search(SearchRequest searchRequest) {
        return searchDb(searchRequest);
    }

    private SearchResponse searchDb(SearchRequest searchRequest) {
        assert(searchRequest.getBookName() != null);
        String searchThis = searchRequest.getBookName()+"%";
        String sql = "Select * from BookDetailstable where bookName like ?";
        SearchResponse searchResponse = new SearchResponse();
        try {
            Connection conn = connect();
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, searchThis);
            ResultSet rs=ps.executeQuery();
            List<BookDetails> books = new ArrayList<>();
            assert(rs!=null);
            while(rs.next()) {
                BookDetails temp = new BookDetails();
                temp.setBookId(rs.getString("bookId"));
                temp.setBookName(rs.getString("bookName"));
                temp.setAuthorName(rs.getString("authorName"));
                temp.setRatings(rs.getFloat("ratings"));
                temp.setIsAvailable(rs.getBoolean("isAvailable"));
                searchResponse.setIsavailable(true);
                books.add(temp);
            }
            searchResponse.setBookDetails(books);
        } catch (SQLException e) {
            e.printStackTrace();
            searchResponse.setIsavailable(false);
        }
        return searchResponse;
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
