package com.example.server.book;

import com.example.client.BookService;
import com.example.server.HibernateUtil;
import com.example.server.book.DeleteBook.DeleteBookFromDataStore;
import com.example.server.book.UpdateBook.UpdateBookInDataStore;
import com.example.shared.book.AddBookRequest;
import com.example.shared.book.BookDetails;
import com.example.shared.book.DeleteBookRequest;
import com.example.shared.book.UpdateBookRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.hibernate.Session;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static java.lang.Float.valueOf;
import static org.junit.jupiter.api.Assertions.assertEquals;

//import com.example.shared.book.BookDetailsTable;

//import static com.example.server.FormatConverters.convertBookDetailsIntoBookDetailsTable;
//import static com.example.server.FormatConverters.convertBookDetailsTableIntoBookDetails;

public class bookServiceImpl extends RemoteServiceServlet implements BookService {

//    @Autowired
//    private BookRepository bookRepository;

    //get_all_books
    @Override
    public List<BookDetails> getAllBooks() {
//        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
        //String test = getAllBooksFromDataStore.getBooksDetails().get(0).getBookName();
//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        List<BookDetails> bookDetails = new ArrayList<BookDetails>(session.createQuery(" from com.example.shared.book.BookDetails").list());
//        List<BookDetails> bookDetails = new ArrayList<BookDetails>(
//                bookDetailsTables.size());
//        for (BookDetailsTable book : bookDetailsTables) {
//            bookDetails.add(convertBookDetailsTableIntoBookDetails(book));
//        }
//        session.getTransaction().commit();
//        return bookDetails;
//        List<BookDetailsTable> books = new ArrayList<>();
//        List<BookDetails> lstbook = new ArrayList<>();
//        bookRepository.findAll()
//                .forEach(books::add);
//        for(BookDetailsTable book: books) {
//            lstbook.add(convertBookDetailsTableIntoBookDetails(book));
//        }
//        return lstbook;
        ///////////////////////////////////////////////////////////////////
        ////
        ////
        ////SPRING ENDPOINT
        ////
        ////
        ///////////////////////////////////////////////////////////////////
        RestTemplate restTemplate = new RestTemplate();
        List<BookDetails> books = new ArrayList<>();
        String url = "http://localhost:8089/app/book/getAll";
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        ResponseEntity<String> result = restTemplate.getForEntity(uri, String.class);
        JSONParser parse = new JSONParser();
        JSONObject jobj = null;
        try {
            jobj = (JSONObject)parse.parse(result.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) jobj.get("Books");
//        System.out.println(jsonObject.toString());
        JSONArray jsonArray = (JSONArray) jsonObject.get("bookDetailsList");
        for(int i = 0;i < jsonArray.size();i++) {
            JSONObject jsonObject1 = (JSONObject)jsonArray.get(i);

            BookDetails temp = new BookDetails();
            temp.setAuthorName(jsonObject1.get("authorName").toString());
            temp.setBookId(jsonObject1.get("bookId").toString());
            temp.setBookName(jsonObject1.get("bookName").toString());
            temp.setRatings(valueOf(jsonObject1.get("ratings").toString()));
            temp.setIsAvailable(Boolean.parseBoolean(jsonObject1.get("isAvailable").toString()));
            books.add(temp);
        }

        return books;
    }

    //get_book_names
    @Override
    public List<String> getAllBooksName() {
//        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
//        return getAllBooksFromDataStore.getBookNames();


        ///calling spring app
//        String[] arr= {"a","b"};
//        DemoApplication d = new DemoApplication();
//        d.main(arr);
        ////
        List<BookDetails> bookDetails = new ArrayList<>();
        List<String> bookNames = new ArrayList<>();
        bookDetails = getAllBooks();
        for(BookDetails book : bookDetails) {
            bookNames.add(book.getBookName());
        }
        return bookNames;
    }
    @Override
    public List<BookDetails> getBookDetailsByBookName(String bookName) {
//        GetAllBooksFromDataStore getAllBooksFromDataStore = new GetAllBooksFromDataStore();
//        return getAllBooksFromDataStore.getBookDetailsByBookName(bookName);

//        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//        session.beginTransaction();
//        List<BookDetails> bookDetails = new ArrayList<BookDetails>(session.createQuery(" from com.example.shared.book.BookDetails B WHERE B.bookName = :bookName").setParameter("bookName",bookName).list());
//        session.getTransaction().commit();
//        return null;


        ///////////////////////////////////////////////////////////////////
        ////
        ////
        ////SPRING ENDPOINT
        ////
        ////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////

        List<BookDetails> books = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8089/app/book/get/"+bookName;
        ResponseEntity<String> result = restTemplate.getForEntity(url, String.class);
        JSONParser parse = new JSONParser();
        JSONObject jobj = null;
        try {
            jobj = (JSONObject)parse.parse(result.getBody());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        JSONObject jsonObject = (JSONObject) jobj.get("Books");
//        System.out.println(jsonObject.toString());
        JSONArray jsonArray = (JSONArray) jsonObject.get("bookDetailsList");
        for(int i = 0;i < jsonArray.size();i++) {
            JSONObject jsonObject1 = (JSONObject) jsonArray.get(i);
            BookDetails temp = new BookDetails();
            temp.setAuthorName(jsonObject1.get("authorName").toString());
            temp.setBookId(jsonObject1.get("bookId").toString());
            temp.setBookName(jsonObject1.get("bookName").toString());
            temp.setRatings(valueOf(jsonObject1.get("ratings").toString()));
            temp.setIsAvailable(Boolean.parseBoolean(jsonObject1.get("isAvailable").toString()));
            books.add(temp);
        }
        return books;
    }
    //add_book
    @Override
//    @Transactional(propagation= Propagation.REQUIRED, rollbackFor=Exception.class)
    public Boolean addBook(AddBookRequest addBookRequest) {
//        AddBookToDataStore addBookToDataStore = new AddBookToDataStore();
//        return addBookToDataStore.addBookToDb(addBookRequest);
//        BookDetailsTable
//        bookRepository.save(convertBookDetailsIntoBookDetailsTable(addBookRequest.getBookDetails()));
        ///////////////////////////////////////////////////////////////////
        ////
        ////
        ////SPRING ENDPOINT
        ////
        ////
        ///////////////////////////////////////////////////////////////////////////////////////////////////////
        ///Posting on spring
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8089/app/book/add";
        URI uri = null;
        try {
            uri = new URI(url);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
//        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        JSONObject book2Add = new JSONObject();
        book2Add.put("bookId", addBookRequest.getBookDetails().getBookId());
        book2Add.put("bookName", addBookRequest.getBookDetails().getBookName());
        book2Add.put("authorName",addBookRequest.getBookDetails().getAuthorName());
        book2Add.put("ratings",addBookRequest.getBookDetails().getRatings());
        book2Add.put("isAvailable",addBookRequest.getBookDetails().getIsAvailable());
        HttpEntity<String> request = new HttpEntity<String>(book2Add.toString(), headers);
        ResponseEntity<String> result = restTemplate.postForEntity(url, request, String.class);
        if(result.getStatusCodeValue() == 201) {
            return true;
        }
        return false;
    }

    //update_book
    @Override
    public Boolean updateBook(String orig_id, UpdateBookRequest updateBookRequest) {
//        UpdateBookInDataStore updateBookInDataStore = new UpdateBookInDataStore();
//        return updateBookInDataStore.update(orig_id, updateBookRequest);
//        Boolean added = false;
//        DeleteBookFromDataStore deleteBookFromDataStore = new DeleteBookFromDataStore();
//        DeleteBookRequest deleteBookRequest = new DeleteBookRequest();
//        deleteBookRequest.setBookId(updateBookRequest.getBookDetails().getBookId());
//        Boolean deleted = deleteBookFromDataStore.deleteBookFromDb(deleteBookRequest);
//        if(deleted) {
//            AddBookRequest addBookRequest = new AddBookRequest();
//            addBookRequest.setBookDetails(updateBookRequest.getBookDetails());
//            AddBookToDataStore addBookToDataStore = new AddBookToDataStore();
//            added = addBookToDataStore.addBookToDb(addBookRequest);
//        } else{
//            return false;
//        }
//        return added;



        ///////////////////////////////////////////////////////////////////
        ////
        ////
        ////SPRING ENDPOINT
        ////
        ////
        ///////////////////////////////////////////////////////////////////

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8089/app/book/update/"+orig_id;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        JSONObject book2Add = new JSONObject();
        book2Add.put("bookId", updateBookRequest.getBookDetails().getBookId());
        book2Add.put("bookName", updateBookRequest.getBookDetails().getBookName());
        book2Add.put("authorName",updateBookRequest.getBookDetails().getAuthorName());
        book2Add.put("ratings",updateBookRequest.getBookDetails().getRatings());
        book2Add.put("isAvailable",updateBookRequest.getBookDetails().getIsAvailable());
        HttpEntity<String> request = new HttpEntity<String>(book2Add.toString(), headers);
        ResponseEntity<String> result = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);
        if(result.getStatusCodeValue() == 200) {
            return true;
        }
        return false;

    }

    //delete_book
    @Override
    public Boolean deleteBook(DeleteBookRequest deleteBookRequest) {
//        DeleteBookFromDataStore deleteBookFromDataStore = new DeleteBookFromDataStore();
//        return deleteBookFromDataStore.deleteBookFromDb(deleteBookRequest);

        ///////////////////////////////////////////////////////////////////
        ////
        ////
        ////SPRING ENDPOINT
        ////
        ////
        ///////////////////////////////////////////////////////////////////

        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8089/app/book/delete/"+deleteBookRequest.getBookId();
        restTemplate.delete(url);
        return true;
    }
}
