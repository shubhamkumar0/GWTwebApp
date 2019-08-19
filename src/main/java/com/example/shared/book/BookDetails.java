package com.example.shared.book;

import com.google.gwt.user.client.rpc.IsSerializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "BookDetailsTable")
public class BookDetails implements IsSerializable{
    @Id
    private String bookId;
    private String bookName;
    private String authorName;
    private Float ratings;
    private boolean isAvailable;

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Float getRatings() {
        return ratings;
    }

    public void setRatings(Float ratings) {
        this.ratings = ratings;
    }

    public boolean getIsAvailable() {
        return isAvailable;
    }

    public void setIsAvailable(boolean isAvailable) {
        this.isAvailable = isAvailable;
    }

//    public BookDetails(com.example.shared.book.BookDetailsTable bookDetailsTable) {
//        bookId = bookDetailsTable.getBookId();
//        bookName = bookDetailsTable.getBookName();
//        authorName = bookDetailsTable.getAuthorName();
//        ratings = bookDetailsTable.getRatings();
//        isAvailable = bookDetailsTable.getIsAvailable();
//    }

}
