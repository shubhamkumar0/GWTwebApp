package com.example.server.book;

import com.example.server.TableSetup.TableSetup;
import com.example.shared.book.AddBookRequest;
import com.example.shared.book.BookDetails;
//import com.example.shared.book.BookDetailsTable;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.unit.DataSize;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class bookServiceImplTest {

    @Autowired
    private static final bookServiceImpl bookService = new bookServiceImpl();

    @BeforeAll
    public static void init() {
        System.out.println("Testing impl");
    }

    @Test
    public void addBookTest() {
        AddBookRequest addBookRequest = new AddBookRequest();
        BookDetails  bookDetails = new BookDetails();
        bookDetails.setBookId("1");
        bookDetails.setBookName("name_1");
        bookDetails.setRatings((float)2.0);
        bookDetails.setIsAvailable(true);
        bookDetails.setAuthorName("author_1");
        addBookRequest.setBookDetails(bookDetails);
        boolean ans = bookService.addBook(addBookRequest);
        assert(ans==true);
    }

    @Test
    public void TableInitialized() {
        List<BookDetails> newBooks= new ArrayList<>();
        newBooks = bookService.getAllBooks();
        assert(newBooks.size()==2);
    }
}
