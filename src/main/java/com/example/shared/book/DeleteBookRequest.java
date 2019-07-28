package com.example.shared.book;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DeleteBookRequest implements IsSerializable {

    private String bookId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
