package com.example.shared.search;

import com.example.shared.book.BookDetails;
import com.google.gwt.user.client.rpc.IsSerializable;

import java.util.List;

public class SearchResponse implements IsSerializable {
    private boolean isavailable;
    private List<BookDetails> bookDetails;

    public boolean getIsavailable() {
        return isavailable;
    }

    public void setIsavailable(boolean isavailable) {
        this.isavailable = isavailable;
    }

    public List<BookDetails> getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(List<BookDetails> bookDetails) {
        this.bookDetails = bookDetails;
    }
}
