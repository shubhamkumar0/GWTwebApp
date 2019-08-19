package com.example.client;

import com.example.shared.book.*;
//import com.example.shared.book.BookDetailsTable;
import com.google.gwt.user.client.rpc.AsyncCallback;

import java.util.List;

public interface BookServiceAsync {
    void getAllBooks(com.google.gwt.user.client.rpc.AsyncCallback<List<BookDetails>> arg4);

    void getAllBooksName(com.google.gwt.user.client.rpc.AsyncCallback<List<String>> arg4);

    void addBook(AddBookRequest addBookRequest, com.google.gwt.user.client.rpc.AsyncCallback<Boolean> arg4);

    void updateBook(String orig_id, UpdateBookRequest updateBookRequest, com.google.gwt.user.client.rpc.AsyncCallback<Boolean> arg4);

    void deleteBook(DeleteBookRequest deleteBookRequest, com.google.gwt.user.client.rpc.AsyncCallback<Boolean> arg4);

    void getBookDetailsByBookName(String bookId, com.google.gwt.user.client.rpc.AsyncCallback<List<BookDetails>> arg4);
}
