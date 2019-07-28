package com.example.shared.book;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddBookRequest implements IsSerializable {

    BookDetails bookDetails = new BookDetails();

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }
}
