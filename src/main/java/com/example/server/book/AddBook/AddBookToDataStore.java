package com.example.server.book.AddBook;

import com.example.shared.book.AddBookRequest;
import com.example.shared.book.AddBookResponse;

public class AddBookToDataStore {
    public AddBookResponse addBookToDb(AddBookRequest addBookRequest) {
        return add(addBookRequest);
    }

    private AddBookResponse add(AddBookRequest addBookRequest) {
//        AddBookResponse addBookResponse = new AddBookResponse();
//        EntityManagerFactory emf = GlobalConstants.getEntityManagerFactory();
//        EntityManager em = emf.createEntityManager();
//        BookDetailsTable bookDetailsTable = new BookDetailsTable();
//        bookDetailsTable.setBookName(addBookRequest.getBookDetails().getBookName());
//        bookDetailsTable.setBookId(addBookRequest.getBookDetails().getBookId());
//        bookDetailsTable.setAuthorName(addBookRequest.getBookDetails().getAuthorName());
//        bookDetailsTable.setRatings(addBookRequest.getBookDetails().getRatings());
//        bookDetailsTable.setIsAvailable(true);
//        em.clear();
//        em.getTransaction().begin();
//        em.persist(bookDetailsTable);
//        em.getTransaction().commit();
//        em.close();
//        addBookResponse.setAddBookStatus(true);
//        addBookResponse.setBookDetails(FormatConverters.convertBookDetailsTableIntoBookDetails(bookDetailsTable));
        return null;
    }
}
