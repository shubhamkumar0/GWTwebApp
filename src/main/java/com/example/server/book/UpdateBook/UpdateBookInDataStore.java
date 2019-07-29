//package com.example.server.book.UpdateBook;
//
//import com.example.server.book.DeleteBook.DeleteBookFromDataStore;
//import com.example.shared.book.UpdateBookRequest;
//import com.example.shared.book.UpdateBookResponse;
//
//public class UpdateBookInDataStore {
//    public Boolean updateBookInDb(UpdateBookRequest updateBookRequest) {
//        return update(updateBookRequest);
//    }
//
//    private Boolean update(UpdateBookRequest updateBookRequest) {
//        DeleteBookFromDataStore deleteBookFromDataStore = new DeleteBookFromDataStore();
//
////        UpdateBookResponse updateBookResponse = new UpdateBookResponse();
////        EntityManager em = getEntityManagerFactory().createEntityManager();
////        BookDetailsTable bookDetailsTable = em.find(BookDetailsTable.class,updateBookRequest.getBookDetails().getBookId());
////        em.getTransaction().begin();
////        bookDetailsTable.setBookId(updateBookRequest.getBookDetails().getBookId());
////        bookDetailsTable.setBookName(updateBookRequest.getBookDetails().getBookName());
////        bookDetailsTable.setAuthorName(updateBookRequest.getBookDetails().getAuthorName());
////        bookDetailsTable.setRatings(updateBookRequest.getBookDetails().getRatings());
////        bookDetailsTable.setBookId(updateBookRequest.getBookDetails().getBookId());
////        em.getTransaction().commit();
////        updateBookResponse.setUpdated(true);
////        updateBookResponse.setBookId(updateBookRequest.getBookDetails().getBookId());
//        return null;
//    }
//}
