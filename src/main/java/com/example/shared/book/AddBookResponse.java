package com.example.shared.book;

import com.google.gwt.user.client.rpc.IsSerializable;

public class AddBookResponse implements IsSerializable {

    private boolean addBookStatus;
    BookDetails bookDetails = new BookDetails();

    public boolean getAddBookStatus() {
        return addBookStatus;
    }

    public void setAddBookStatus(boolean addBookStatus) {
        this.addBookStatus = addBookStatus;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }
}
