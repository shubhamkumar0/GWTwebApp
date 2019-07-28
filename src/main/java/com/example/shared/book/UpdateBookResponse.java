package com.example.shared.book;

import com.google.gwt.user.client.rpc.IsSerializable;

public class UpdateBookResponse implements IsSerializable {
    private boolean updated;
    private String bookId;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public boolean getUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }
}
