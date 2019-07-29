package com.example.client;

import com.example.shared.book.AddBookRequest;
import com.example.shared.book.AddBookResponse;
import com.example.shared.book.BookDetails;
import com.example.shared.book.DeleteBookRequest;
import com.example.shared.book.DeleteBookResponse;
import com.example.shared.book.UpdateBookRequest;
import com.example.shared.book.UpdateBookResponse;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("BookService")
public interface BookService extends RemoteService {
    //this is the interface of data access layer
    List<BookDetails> getAllBooks();
    List<String> getAllBooksName();
    Boolean addBook(AddBookRequest addBookRequest);
    Boolean updateBook(UpdateBookRequest updateBookRequest);
    Boolean deleteBook(DeleteBookRequest deleteBookRequest);
    BookDetails getBookDetailsByBookName(String bookId);
}
