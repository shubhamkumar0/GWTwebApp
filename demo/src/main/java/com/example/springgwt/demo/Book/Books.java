package com.example.springgwt.demo.Book;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT, use = JsonTypeInfo.Id.NAME)
public class Books implements Serializable {

    private static final long serialVersionUID = 1L;

    private List<BookDetails> bookDetailsList = new ArrayList<>();

    public List<BookDetails> getBookDetailsList() {
        return bookDetailsList;
    }

    public void setBookDetailsList(List<BookDetails> bookDetailsList) {
        this.bookDetailsList = bookDetailsList;
    }
}
