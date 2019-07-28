package com.example.server.book;

import com.example.client.BookService;
import com.example.shared.book.*;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.example.server.book.AddBook.AddBookToDataStore;
import com.example.server.book.DeleteBook.DeleteBookFromDataStore;
import com.example.server.book.UpdateBook.UpdateBookInDataStore;

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
    public BookDetails getBookDetailsByBookName(String bookName) {
        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
        return getAllBooksFromDataStore.getBookDetailsByBookName(bookName);
    }
    //add_book
    @Override
    public AddBookResponse addBook(AddBookRequest addBookRequest) {
        AddBookToDataStore addBookToDataStore = new AddBookToDataStore();
        return addBookToDataStore.addBookToDb(addBookRequest);
    }

    //update_book
    @Override
    public UpdateBookResponse updateBook(UpdateBookRequest updateBookRequest) {
        UpdateBookInDataStore updateBookInDataStore = new UpdateBookInDataStore();
        return updateBookInDataStore.updateBookInDb(updateBookRequest);
    }

    //delete_book
    @Override
    public DeleteBookResponse deleteBook(DeleteBookRequest deleteBookRequest) {
        DeleteBookFromDataStore deleteBookFromDataStore = new DeleteBookFromDataStore();
        return deleteBookFromDataStore.deleteBookFromDb(deleteBookRequest);
    }
}
