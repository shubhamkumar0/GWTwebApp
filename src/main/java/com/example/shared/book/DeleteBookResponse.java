package com.example.shared.book;

import com.google.gwt.user.client.rpc.IsSerializable;

public class DeleteBookResponse implements IsSerializable {

    private String bookId;
    private boolean deleted;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
