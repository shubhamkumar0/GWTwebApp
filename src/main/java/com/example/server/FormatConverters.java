package com.example.server;


import com.example.shared.book.BookDetails;
import com.example.server.book.Tables.BookDetailsTable;
import com.example.shared.login.SignUpRequest;

public class FormatConverters {

    public static BookDetails convertBookDetailsTableIntoBookDetails(BookDetailsTable bookDetailsTable) {
        BookDetails bookDetails = new BookDetails();
        bookDetails.setBookId(bookDetailsTable.getBookId());
        bookDetails.setBookName(bookDetailsTable.getBookName());
        bookDetails.setAuthorName(bookDetailsTable.getAuthorName());
        bookDetails.setRatings(bookDetailsTable.getRatings());
        bookDetails.setIsAvailable(bookDetailsTable.getIsAvailable());
        return bookDetails;
    }

    public static SignUpRequest makeSignUpRequest(String name, String email, String pass) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(email);
        signUpRequest.setName(name);
        signUpRequest.setPassword(pass);
        return signUpRequest;
    }
}
