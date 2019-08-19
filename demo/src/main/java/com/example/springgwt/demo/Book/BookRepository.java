package com.example.springgwt.demo.Book;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface BookRepository extends CrudRepository<BookDetails,String> {

    List<BookDetails> findBybookName(String book_name);
}
