package com.example.server.book;

import com.example.client.BookService;
import com.example.server.book.UpdateBook.UpdateBookInDataStore;
import com.example.shared.book.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.example.server.book.AddBook.AddBookToDataStore;
import com.example.server.book.DeleteBook.DeleteBookFromDataStore;


import java.awt.print.Book;
import java.util.ArrayList;
import java.util.List;

public class bookServiceImpl extends RemoteServiceServlet implements BookService {

    //get_all_books
    @Override
    public List<BookDetails> getAllBooks() {
        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
        //String test = getAllBooksFromDataStore.getBooksDetails().get(0).getBookName();
        return getAllBooksFromDataStore.getBooksDetails();
    }

    //get_book_names
    @Override
    public List<String> getAllBooksName() {
        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
        return getAllBooksFromDataStore.getBookNames();
    }
    @Override
    public List<BookDetails> getBookDetailsByBookName(String bookName) {
        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
        return getAllBooksFromDataStore.getBookDetailsByBookName(bookName);
    }
    //add_book
    @Override
    public Boolean addBook(AddBookRequest addBookRequest) {
        AddBookToDataStore addBookToDataStore = new AddBookToDataStore();
        return addBookToDataStore.addBookToDb(addBookRequest);
    }

    //update_book
    @Override
    public Boolean updateBook(String orig_id, UpdateBookRequest updateBookRequest) {
        UpdateBookInDataStore updateBookInDataStore = new UpdateBookInDataStore();
        return updateBookInDataStore.update(orig_id, updateBookRequest);
//        Boolean added = false;
//        DeleteBookFromDataStore deleteBookFromDataStore = new DeleteBookFromDataStore();
//        DeleteBookRequest deleteBookRequest = new DeleteBookRequest();
//        deleteBookRequest.setBookId(updateBookRequest.getBookDetails().getBookId());
//        Boolean deleted = deleteBookFromDataStore.deleteBookFromDb(deleteBookRequest);
//        if(deleted) {
//            AddBookRequest addBookRequest = new AddBookRequest();
//            addBookRequest.setBookDetails(updateBookRequest.getBookDetails());
//            AddBookToDataStore addBookToDataStore = new AddBookToDataStore();
//            added = addBookToDataStore.addBookToDb(addBookRequest);
//        } else{
//            return false;
//        }
//        return added;
    }

    //delete_book
    @Override
    public Boolean deleteBook(DeleteBookRequest deleteBookRequest) {
        DeleteBookFromDataStore deleteBookFromDataStore = new DeleteBookFromDataStore();
        return deleteBookFromDataStore.deleteBookFromDb(deleteBookRequest);
    }
}
