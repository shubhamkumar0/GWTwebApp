package com.example.springgwt.demo.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public Books getAllBooks() {
        Books books = new Books();
        List<BookDetails> booksL = new ArrayList<>();
        bookRepository.findAll()
                .forEach(booksL::add);
        books.setBookDetailsList(booksL);
        return books;
    }

    public void addBook(BookDetails bookDetails) {
        bookRepository.save(bookDetails);
    }

    public void updateBook(String orig_id, BookDetails bookDetails) {
        if(orig_id != bookDetails.getBookId()) {
            deleteBook(orig_id);
        }
        bookRepository.save(bookDetails);
    }

    public void deleteBook(String id) {
        bookRepository.delete(getBookById(id));
    }

    public Books getBookbyName(String name) {
        Books books = new Books();
        books.setBookDetailsList(bookRepository.findBybookName(name));
        return books;
    }

    public BookDetails getBookById(String id) {
        BookDetails bookDetails = bookRepository.findById(id).get();
        return bookDetails;
    }
}
