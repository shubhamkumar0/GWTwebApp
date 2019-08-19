package com.example.client;

import com.example.shared.book.*;
//import com.example.shared.book.BookDetailsTable;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

@RemoteServiceRelativePath("BookService")
public interface BookService extends RemoteService {
    public static class Util
    {
        private static BookServiceAsync instance;

        public static BookServiceAsync getInstance()
        {
            if (instance == null)
            {
                instance = GWT.create(BookService.class);
            }
            return instance;
        }
    }

    List<BookDetails> getAllBooks();
    List<String> getAllBooksName();
    Boolean addBook(AddBookRequest addBookRequest);
    Boolean updateBook(String orig_id, UpdateBookRequest updateBookRequest);
    Boolean deleteBook(DeleteBookRequest deleteBookRequest);
    List<BookDetails> getBookDetailsByBookName(String bookId);
}
