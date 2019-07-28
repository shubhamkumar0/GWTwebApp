package com.example.server.book.UpdateBook;

import com.example.shared.book.UpdateBookRequest;
import com.example.shared.book.UpdateBookResponse;

public class UpdateBookInDataStore {
    public UpdateBookResponse updateBookInDb(UpdateBookRequest updateBookRequest) {
        return update(updateBookRequest);
    }

    private UpdateBookResponse update(UpdateBookRequest updateBookRequest) {
//        UpdateBookResponse updateBookResponse = new UpdateBookResponse();
//        EntityManager em = getEntityManagerFactory().createEntityManager();
//        BookDetailsTable bookDetailsTable = em.find(BookDetailsTable.class,updateBookRequest.getBookDetails().getBookId());
//        em.getTransaction().begin();
//        bookDetailsTable.setBookId(updateBookRequest.getBookDetails().getBookId());
//        bookDetailsTable.setBookName(updateBookRequest.getBookDetails().getBookName());
//        bookDetailsTable.setAuthorName(updateBookRequest.getBookDetails().getAuthorName());
//        bookDetailsTable.setRatings(updateBookRequest.getBookDetails().getRatings());
//        bookDetailsTable.setBookId(updateBookRequest.getBookDetails().getBookId());
//        em.getTransaction().commit();
//        updateBookResponse.setUpdated(true);
//        updateBookResponse.setBookId(updateBookRequest.getBookDetails().getBookId());
        return null;
    }
}
