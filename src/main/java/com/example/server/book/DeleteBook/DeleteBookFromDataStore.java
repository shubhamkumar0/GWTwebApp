package com.example.server.book.DeleteBook;


import com.example.shared.book.DeleteBookRequest;
import com.example.shared.book.DeleteBookResponse;

public class DeleteBookFromDataStore {
    public DeleteBookResponse deleteBookFromDb(DeleteBookRequest deleteBookRequest) {
        return delete(deleteBookRequest);
    }

    private DeleteBookResponse delete(DeleteBookRequest deleteBookRequest) {
//        DeleteBookResponse deleteBookResponse = new DeleteBookResponse();
//        EntityManager em = getEntityManagerFactory().createEntityManager();
//        BookDetailsTable bookDetailsTable = em.find(BookDetailsTable.class,deleteBookRequest.getBookId());
//        em.getTransaction().begin();
//        em.remove(bookDetailsTable);
//        em.getTransaction().commit();
//        deleteBookResponse.setDeleted(true);
//        deleteBookResponse.setBookId(deleteBookRequest.getBookId());
        return null;
    }
}
