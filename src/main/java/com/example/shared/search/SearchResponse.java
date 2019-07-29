package com.example.shared.search;

import com.example.shared.book.BookDetails;
import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchResponse implements IsSerializable {
    private boolean isavailable;
    private BookDetails bookDetails;

    public boolean getIsavailable() {
        return isavailable;
    }

    public void setIsavailable(boolean isavailable) {
        this.isavailable = isavailable;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }
}
