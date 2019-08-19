package com.example.springgwt.demo.Book;


import com.fasterxml.jackson.annotation.JsonTypeName;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="BookDetailsTable")
@JsonTypeName(value = "BookDetails")
public class BookDetails implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name="bookId")
    private String bookId;
    @Column(name="bookName")
    private String bookName;
    @Column(name="authorName")
    private String authorName;
    @Column(name="ratings")
    private Float ratings;
    @Column(name="isAvailable")
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
}
