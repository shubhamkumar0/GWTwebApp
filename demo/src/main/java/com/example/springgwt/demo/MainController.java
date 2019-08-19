package com.example.springgwt.demo;

import com.example.springgwt.demo.Book.BookDetails;
import com.example.springgwt.demo.Book.BookService;
import com.example.springgwt.demo.Book.Books;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/book")
public class MainController {

    @Autowired
    private BookService bookService;

    @RequestMapping("/getAll")
    public Books getAllBooks() {
        return bookService.getAllBooks();
    }

    @RequestMapping("/get/{name}")
    public Books getBookbyName(@PathVariable String name) {
        return bookService.getBookbyName(name);
    }

    @RequestMapping(method=RequestMethod.POST, value="/add")
    public void addNewBook(@RequestBody BookDetails bookDetails) {
        bookService.addBook(bookDetails);
    }

    @RequestMapping(method=RequestMethod.PUT, value="/update/{id}")
    public void updateBook(@RequestBody BookDetails bookDetails, @PathVariable String id) {
        bookService.updateBook(id,bookDetails);
    }

    @RequestMapping(method=RequestMethod.DELETE, value="/delete/{id}")
    public void deleteBook( @PathVariable String id) {
        bookService.deleteBook(id);
    }
}
