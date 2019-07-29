package com.example.shared.search;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SearchRequest implements IsSerializable {
    private String bookName;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }
}
