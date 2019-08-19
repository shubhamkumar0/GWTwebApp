package com.example.server;


import com.example.shared.book.BookDetails;
//import com.example.shared.book.BookDetailsTable;
import com.example.shared.login.SignUpRequest;

public class FormatConverters {

//    public static BookDetails convertBookDetailsTableIntoBookDetails(BookDetailsTable bookDetailsTable) {
//        BookDetails bookDetails = new BookDetails(bookDetailsTable);
//        return bookDetails;
//    }
//
//    public static BookDetailsTable convertBookDetailsIntoBookDetailsTable(BookDetails bookDetails) {
//        BookDetailsTable bookDetailsTable = new BookDetailsTable();
//        bookDetails.setBookId(bookDetails.getBookId());
//        bookDetails.setBookName(bookDetails.getBookName());
//        bookDetails.setAuthorName(bookDetails.getAuthorName());
//        bookDetails.setRatings(bookDetails.getRatings());
//        bookDetails.setIsAvailable(bookDetails.getIsAvailable());
//        return bookDetailsTable;
//    }

    public static SignUpRequest makeSignUpRequest(String name, String email, String pass) {
        SignUpRequest signUpRequest = new SignUpRequest();
        signUpRequest.setEmail(email);
        signUpRequest.setName(name);
        signUpRequest.setPassword(pass);
        return signUpRequest;
    }
}
