package com.example.client;

import com.example.shared.book.DeleteBookRequest;
import com.example.shared.login.UserDetails;
import com.example.shared.search.SearchRequest;
import com.example.shared.search.SearchResponse;
import com.example.shared.book.AddBookRequest;
import com.example.shared.book.BookDetails;
import com.example.shared.book.UpdateBookRequest;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.http.client.*;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.sql.Time;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Timer;

import static java.lang.Float.valueOf;

public class Kitab implements EntryPoint {
    //////////////
    final Button login = new Button("Back Again? Login!");
    final Button signup = new Button("New User? Sign Up!");
    static String UserName;
//    private static final EventBus s_eventBus = new SimpleEventBus();

    public void onModuleLoad() {
        cleareverything();
        String sessionID = Cookies.getCookie("sid");
        if (sessionID == null || sessionID == "undefined")
        {
            RootPanel.get("login").add(login);
            RootPanel.get("signup").add(signup);
            init();
        } else
        {
//            Window.alert(sessionID);
            checkWithServerIfSessionIdIsStillLegal(sessionID);
        }
    }

    private void checkWithServerIfSessionIdIsStillLegal(final String sessionID)
    {
        LoginService.Util.getInstance().loginFromSessionServer(sessionID, new AsyncCallback<UserDetails>()
        {
            @Override
            public void onFailure(Throwable caught)
            {
                Window.alert("fail");
                LoginPage loginpage = new LoginPage();
            }

            @Override
            public void onSuccess(UserDetails result)
            {
                if (result == null)
                {
//                    Window.alert(sessionID);
                    LoginPage loginpage = new LoginPage();
                } else
                {
                    if (result.getLoggedIn())
                    {
//                        Window.alert(result.getName());
                        UserName = result.getName();
                        getBooks();
                    } else
                    {
//                        Window.alert("dont remember " + result.getEmail());
                        LoginPage loginpage = new LoginPage();
                    }
                }
            }

        });
    }
    void init() {
        login.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                LoginPage loginpage = new LoginPage();
            }
        });
        signup.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                RegisterPage registerpage = new RegisterPage();
            }
        });
    }

    private static class RegisterPage extends Composite {

        final Button button = new Button("Sign Up!");
        final TextBox name = new TextBox();
        final TextBox email = new TextBox();
        final Label existingUser = new Label("Already have an account? Log In!");
        final PasswordTextBox password = new PasswordTextBox();
        private LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);

        public RegisterPage() {
            RootPanel.get("signup").clear();
            RootPanel.get("login").clear();
            RootPanel.get("search2").clear();
            name.setText("Name");
            email.setText("email");
            password.setText("password");
            RootPanel.get("slot1").add(name);
            RootPanel.get("slot2").add(email);
            RootPanel.get("slot3").add(password);
            RootPanel.get("slot4").add(button);
            existingUser.setStyleName("cus");
            RootPanel.get("search1").add(existingUser);
            existingUser.getElement().getStyle().setCursor(Style.Cursor.POINTER);
            existingUser.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    cleareverything();
                    LoginPage loginPage = new LoginPage();
                }
            });
            button.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    loginServiceAsync.signUpUser(name.getText(),email.getText(),password.getText(),
                            new AsyncCallback<Boolean>() {
                                public void onFailure(Throwable caught) {
                                    Window.alert("Sign Up failed.");
                                }

                                public void onSuccess(Boolean result) {
                                    if(result == false) {
                                        Window.alert("Sign Up failed.");
                                    } else {
                                        Window.alert("User Registered! :)");
                                        cleareverything();
                                        LoginPage loginpage = new LoginPage();
                                    }
                                }
                            });
                }
            });
        }
    }

    private static class LoginPage extends Composite {

        final Button button = new Button("Log In!");
        final TextBox email = new TextBox();
        CheckBox rememberMe = new CheckBox("Remember me!");
        boolean checked= false;
        final Label newUser = new Label("First time Here? Sign Up!");
        final PasswordTextBox password = new PasswordTextBox();
        private LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);

        public LoginPage() {
            email.setText("email");
            password.setText("password");
            RootPanel.get("login").clear();
            RootPanel.get("signup").clear();
            RootPanel.get("search2").clear();
            RootPanel.get("slot2").add(email);
            RootPanel.get("slot3").add(password);
            RootPanel.get("slot4").add(button);
            RootPanel.get("checker").add(rememberMe);
            newUser.setStyleName("cus");
            RootPanel.get("search1").add(newUser);
            newUser.getElement().getStyle().setCursor(Style.Cursor.POINTER);
            newUser.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    cleareverything();
                    RegisterPage registerpage = new RegisterPage();
                }
            });
            //signup();

            button.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    //setloggedin taking input from user
//                    Window.alert("? "+checked);
                    loginServiceAsync.loginServer(email.getText(), password.getText(),checked,
                            new AsyncCallback<UserDetails>() {
                                public void onFailure(Throwable caught) {
                                    Window.alert("Login failed.");
                                }
                                public void onSuccess(UserDetails result) {
                                    if(result.getName() == null) {
                                        Window.alert("Login failed.");
                                    } else {
                                        //TODO-cookie
                                        String sessionID = result.getSessionId();
                                        final long DURATION = 1000 * 60 * 60 * 24 * 1;
                                        Date expires = new Date(System.currentTimeMillis() + DURATION);
                                        Cookies.setCookie("sid", sessionID, expires, null, "/", false);
                                        UserName = result.getName();
                                        getBooks();
                                    }
                                }
                            });
                }
            });

            rememberMe.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    checked=true;
                }
            });
        }
    }

    static void getBooks() {
        BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
        bookServiceAsync.getAllBooksName(new AsyncCallback<List<String>>() {
            public void onFailure(Throwable caught) {
                Window.alert("Sorry, could not fetch books :|");
            }

            public void onSuccess(List<String> result) {
                MainPage mainPage = new MainPage(result);
            }
        });
    }

    private static class MainPage extends Composite {
        ListBox books = new ListBox();
        Label naam = new Label("Welcome, "+UserName);
        final Button addbook = new Button("addbook");
        final Button search = new Button("Search My Library");
        final Button search_google =new Button("Search Google");
        final Button logout = new Button("Log Out");
        TextBox search_what = new TextBox();
        Label label = new Label();
        final SearchServiceAsync searchServiceAsync = GWT.create(SearchService.class);
        MainPage(final List<String> bookNames) {
            cleareverything();
            RootPanel.get("header").add(naam);
            RootPanel.get("search0").add(search_what);
            RootPanel.get("bookName").add(books);
            RootPanel.get("header2").add(addbook);
            RootPanel.get("search1").add(search);
            RootPanel.get("slot0").add(logout);
            RootPanel.get("search2").add(search_google);
            label.setText("Total "+bookNames.size()+" books in My Library.");
            RootPanel.get("search").add(label);

            books.setVisibleItemCount(10);
            for( String name: bookNames) {
                books.addItem(name);
            }
            getDetails(books);

            logout.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    //dialog and confirm
                    ConfirmDialog confirmDialog = new ConfirmDialog();
                    int left = Window.getClientWidth()/ 3;
                    int top = Window.getClientHeight()/ 3;
                    confirmDialog.setPopupPosition(left, top);
                    confirmDialog.show();
                }
            });

            addbook.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    RootPanel.get("addbook").clear();
                    RootPanel.get("search0").clear();
                    RootPanel.get("search1").clear();
                    RootPanel.get("header2").clear();
                    Book_Adder book_adder = new Book_Adder();
                }
            });

            //To use in updating list functionality
            search_what.addKeyUpHandler(new KeyUpHandler() {
                @Override
                public void onKeyUp(KeyUpEvent event) {
                    UpdateList(search_what.getText(),bookNames);
                }
            });

            search.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if(search_what.getText() != "") {
                        SearchRequest searchRequest = new SearchRequest();
                        searchRequest.setBookName(search_what.getText());
                        searchServiceAsync.search(searchRequest,
                                new AsyncCallback<SearchResponse>() {
                                    public void onFailure(Throwable caught) {
                                        Window.alert("Searching book failed.");
                                    }

                                    public void onSuccess(SearchResponse result) {
                                        //Window.alert("Added the book to library. You can search for it using the book name!");
                                        if (result.getIsavailable() == true) {
                                            MyDialog myDialog = new MyDialog(result.getBookDetails());
                                            int left = Window.getClientWidth()/ 3;
                                            int top = Window.getClientHeight()/ 3;
                                            myDialog.setPopupPosition(left, top);
                                            myDialog.show();
                                        } else {
                                            Window.alert("Book not found! :3");
                                        }
                                    }
                                });
                    } else {
                        Window.alert("Search for what? A brain! :/");
                    }
            }
            });
            search_google.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if(search_what.getText() != "") {
//                        long start = System.currentTimeMillis();
                        UpdateListViaHttp(search_what.getText());
//                        long finish = System.currentTimeMillis();
//                        long timeElapsed = finish-start;
//                        Window.alert(timeElapsed+"");
                    } else {
                        Window.alert("Search for what? A brain! :/");
                    }
                }
            });
        }
    }

    private static void UpdateListViaHttp(String text) {
        if(text != "") {
            RootPanel.get("bookName").clear();
            final ListBox updatedList = new ListBox();
            updatedList.setVisibleItemCount(10);
            final JSONObject[] jsonOnlineUser = {null};
            final JSONArray[] ja = {null};
            String url = "https://www.googleapis.com/books/v1/volumes?q="+text;
            RequestBuilder builder = new RequestBuilder(RequestBuilder.GET, url);
            builder.setHeader("Content-Type", "application/json");

//            long start = System.currentTimeMillis();
            try {
                Request response = builder.sendRequest(null, new RequestCallback() {
                    public void onError(Request request, Throwable exception) {
                        // Code omitted for clarity
                        Window.alert("error");
                    }

                    public void onResponseReceived(Request request, Response response) {
                        jsonOnlineUser[0] = JSONParser.parse(
                                response.getText()).isObject();
                        ja[0] = (JSONArray) jsonOnlineUser[0].get("items").isArray();
//                        Window.alert(".."+ ja[0].size());
                        for(int i = 0; i < ja[0].size(); i++) {
                            updatedList.addItem(ja[0].get(i).isObject().get("volumeInfo").isObject().get("title").toString());
                        }
                    }
                });

            } catch (RequestException e) {
                // Code omitted for clarity
                Window.alert("error");
            }

//            long finish = System.currentTimeMillis();
//            long timeElapsed = finish-start;
//            Window.alert(timeElapsed+"");

            updatedList.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {

//                    Window.alert(updatedList.getValue(updatedList.getSelectedIndex()));
                    String author = null;
                    String name = null;
                    Float rating = null;
                    String url = null;
                    try {
                        author = ja[0].get(updatedList.getSelectedIndex()).isObject().
                                get("volumeInfo").isObject().get("authors").isArray().get(0).toString();
                        name = ja[0].get(updatedList.getSelectedIndex()).isObject().
                                get("volumeInfo").isObject().get("title").toString();
                        rating = valueOf(ja[0].get(updatedList.getSelectedIndex()).isObject().
                                get("volumeInfo").isObject().get("averageRating").toString());
                        url = ja[0].get(updatedList.getSelectedIndex()).isObject().
                                get("volumeInfo").isObject().get("infoLink").toString();
                    } catch(Exception e) {
//                        Window.alert("shuhus");
                    }
//                    Window.alert("us");
                    BookDetails bookDetails = new BookDetails();
                    bookDetails.setAuthorName(author);
                    bookDetails.setBookName(name);
                    bookDetails.setRatings(rating);
                    bookDetails.setBookId(url);
                    GoogleDialog myDialog = new GoogleDialog(bookDetails);
                    int left = Window.getClientWidth()/ 3;
                    int top = Window.getClientHeight()/ 3;
                    myDialog.setPopupPosition(left, top);
                    myDialog.show();
                }
            });
            RootPanel.get("bookName").add(updatedList);
        }
    }

    private static void UpdateList(String text, List<String> books) {
        final ListBox updatedList = new ListBox();
        updatedList.setVisibleItemCount(10);
        if(text != "") {
            RootPanel.get("bookName").clear();


            final SearchServiceAsync searchServiceAsync = GWT.create(SearchService.class);
            SearchRequest searchRequest = new SearchRequest();
            searchRequest.setBookName(text);
            searchServiceAsync.search(searchRequest,
                    new AsyncCallback<SearchResponse>() {
                        public void onFailure(Throwable caught) {
                            Window.alert("Searching book failed.");
                        }
                        public void onSuccess(SearchResponse result) {
                            if (result.getIsavailable() == true) {
                                for(BookDetails book : result.getBookDetails()) {
                                    updatedList.addItem(book.getBookName());
                                }
                            } else {
//                                Window.alert("Book not found! :3");
                            }
                        }
                    });
            getDetails(updatedList);
            RootPanel.get("bookName").clear();
            RootPanel.get("bookName").add(updatedList);

        } else {
            for(String book : books) {
                updatedList.addItem(book);
            }
            getDetails(updatedList);
            RootPanel.get("bookName").clear();
            RootPanel.get("bookName").add(updatedList);
        }

    }

    private static void getDetails(final ListBox books) {

        books.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
                bookServiceAsync.getBookDetailsByBookName(books.getValue(books.getSelectedIndex()),
                        new AsyncCallback<List<BookDetails>>() {
                            public void onFailure(Throwable caught) {
                                //figure out the best message to show here
                                Window.alert("Sorry, could not fetch book details :|");
                            }

                            public void onSuccess(List<BookDetails> result) {
                                MyDialog myDialog = new MyDialog(result);
                                int left = Window.getClientWidth()/ 3;
                                int top = Window.getClientHeight()/ 3;
                                myDialog.setPopupPosition(left, top);
                                myDialog.show();
                            }
                        });
            }
        });
    }

    private static class Book_Adder extends Composite {

        final Button add = new Button("Add!");
        Label selectLabel = new Label("Add books via uploading CSV:");
        final FileUpload fileUpload = new FileUpload();
        final Button addFromCsv = new Button("Upload CSV");
        //TODO implement FileUpload & FormPanel by yourself
        final FormPanel form = new FormPanel();
        final TextBox bookId = new TextBox();
        final TextBox bookName = new TextBox();
        final TextBox authorName = new TextBox();
        ListBox ratings = new ListBox();
        final String[] rate = new String[1];
        final BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
        final Button back = new Button("Go back");

        public Book_Adder() {
            RootPanel.get("bookName").clear();
            RootPanel.get("search2").clear();
            bookId.setText("Book Id");
            bookName.setText("Book Name");
            authorName.setText("Author Name");
            rate[0] = "How would you rate this book?";
            ratings = ratinglistfunc(ratings, rate);
            populaterootpanel(bookId,bookName,authorName,ratings);
            RootPanel.get("uploadfilelabel").add(selectLabel);
            RootPanel.get("slot4").add(add);
            RootPanel.get("upl").add(fileUpload);
            RootPanel.get("upload").add(addFromCsv);
            RootPanel.get("addbook").add(back);
            RootPanel.get("gwtContainer").add(form);

            //TODO define url to which POST the form data
//            form.setAction("http://www.tutorialspoint.com/gwt/myFormHandler");
//            // set form to use the POST method, and multipart MIME encoding.
//            form.setEncoding(FormPanel.ENCODING_MULTIPART);
//            form.setMethod(FormPanel.METHOD_POST);

            back.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    cleareverything();
                    getBooks();
                }
            });

            add.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    if(rate[0] == "How would you rate this book?") {
                        Window.alert("Please choose a valid rating! :O");
                    } else {
                        BookDetails bookDetails = new BookDetails();
                        bookDetails.setBookId(bookId.getText());
                        bookDetails.setBookName(bookName.getText());
                        bookDetails.setAuthorName(authorName.getText());
                        bookDetails.setRatings(valueOf(rate[0]));
                        bookDetails.setIsAvailable(true);
                        final AddBookRequest book_to_be_added = new AddBookRequest();
                        book_to_be_added.setBookDetails(bookDetails);
                        bookServiceAsync.addBook(book_to_be_added,
                                new AsyncCallback<Boolean>() {
                                    public void onFailure(Throwable caught) {
                                        Window.alert("Adding book failed :(");
                                    }

                                    public void onSuccess(Boolean result) {
                                        Window.alert("Added the book to library. You can search for it using the book name!");
                                        cleareverything();
                                        getBooks();
                                    }
                                });
                    }
                }
            });

            addFromCsv.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    //get the filename to be uploaded
                    String filename = fileUpload.getFilename();
                    if (filename.length() == 0) {
                        Window.alert("No File Specified!");
                    } else {
                        //TODO
                        form.submit();
                        Window.alert(filename);
                    }
                }
            });
        }

    }

    private static class ConfirmDialog extends DialogBox {

        DockPanel panel = new DockPanel();
        Label label = new Label("Are you sure?");
        Button no= new Button("Cancel");
        Button yes = new Button("Log out?");

        public ConfirmDialog() {
            setAnimationEnabled(true);
            setGlassEnabled(true);

            yes.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
//                    if(text == "Log out?") {
                        ConfirmDialog.this.hide();
                        Cookies.removeCookie("sid");
                        LoginService.Util.getInstance().logout(Cookies.getCookie("sid"), new AsyncCallback<Boolean>() {
                            @Override
                            public void onFailure(Throwable caught)
                            {
                                Window.alert("logout fail");
                            }

                            @Override
                            public void onSuccess(Boolean result) {
                                if(result) {
                                    cleareverything();
                                    LoginPage loginPage = new LoginPage();
                                } else {
                                    Window.alert("Logout failed");
                                }
                            }
                        });
                }
            });
            no.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    ConfirmDialog.this.hide();
                }
            });
            panel.setHeight("50");
            panel.setWidth("250");
            panel.setSpacing(5);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            panel.add(label, DockPanel.NORTH);
            panel.add(yes, DockPanel.EAST);
            panel.add(no, DockPanel.WEST);
            setWidget(panel);
        }
    }

    private static class ConfirmDialog2 extends DialogBox {
        DockPanel panel = new DockPanel();
        Label label = new Label("Are you sure?");
        Button no= new Button("Cancel");
        Button yes = new Button("Delete?");
        public ConfirmDialog2(final String bookId) {
            setAnimationEnabled(true);
            setGlassEnabled(true);
            yes.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    DeleteBookRequest deleteBookRequest = new DeleteBookRequest();
                    deleteBookRequest.setBookId(bookId);
                    BookService.Util.getInstance().deleteBook(deleteBookRequest,new AsyncCallback<Boolean>() {
                        @Override
                        public void onFailure(Throwable caught)
                        {
                            Window.alert("delete fail");
                        }

                        @Override
                        public void onSuccess(Boolean result) {
                            if(result) {
                                ConfirmDialog2.this.hide();
                                cleareverything();
                                getBooks();
                            } else {
                                Window.alert("delete fail");
                            }
                        }
                    });
                }
            });
            no.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    ConfirmDialog2.this.hide();
                }
            });
            panel.setHeight("50");
            panel.setWidth("250");
            panel.setSpacing(5);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            panel.add(label, DockPanel.NORTH);
            panel.add(yes, DockPanel.EAST);
            panel.add(no, DockPanel.WEST);
            setWidget(panel);
        }

    }

    private static class MyDialog extends DialogBox {

        DockPanel panel = new DockPanel();
        Button ok = new Button("OK");
        Button update = new Button("Update");
        Button next = new Button("Next >>");
        Button back = new Button("<< Back");
        Button delete = new Button("Delete?");
        int i = 0;

        public MyDialog(final List<BookDetails> bookDetails) {
            setText("Book Details");
            setAnimationEnabled(true);
            setGlassEnabled(true);
            ok.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                }
            });
            delete.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    ConfirmDialog2 confirmDialog = new ConfirmDialog2(bookDetails.get(i).getBookId());
                    int left = Window.getClientWidth()/ 3;
                    int top = Window.getClientHeight()/ 3;
                    confirmDialog.setPopupPosition(left, top);
                    confirmDialog.show();
                    MyDialog.this.hide();
                }
            });
            update.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    cleareverything();
                    UpdateBook updateBook = new UpdateBook(bookDetails.get(i));
                }
            });
            next.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if(i != (bookDetails.size()-1) ) {
                        panel.clear();
                        i++;
                        work(bookDetails.get(i), bookDetails.size());
                    }
                }
            });
            back.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    if(i != 0) {
                        panel.clear();
                        i--;
                        work(bookDetails.get(i), bookDetails.size());
                    }
                }
            });

            work(bookDetails.get(i), bookDetails.size());
        }

        public void work(final BookDetails bookDetails, int count) {

            Label id = new Label("Id: "+bookDetails.getBookId());
            Label name = new Label("Name: "+bookDetails.getBookName());
            Label author = new Label("Author: "+bookDetails.getAuthorName());
            Label rating = new Label("Ratings(Out of 5): "+ bookDetails .getRatings());
            Label isAvail = new Label("Is it Available?: "+ bookDetails.getIsAvailable());
            Label stock = new Label("Stock count: " + count);
            panel.setHeight("100");
            panel.setWidth("500");
            panel.setSpacing(10);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            panel.add(stock, DockPanel.LINE_END);
            panel.add(id,DockPanel.NORTH);
            panel.add(name,DockPanel.NORTH);
            panel.add(author,DockPanel.NORTH);
            panel.add(rating,DockPanel.NORTH);
            panel.add(isAvail,DockPanel.NORTH);
            panel.add(update,DockPanel.SOUTH);
            panel.add(ok,DockPanel.CENTER);
            panel.add(next,DockPanel.EAST);
            panel.add(back,DockPanel.WEST);
            panel.add(delete,DockPanel.LINE_END);
            setWidget(panel);
        }
    }

    private static class GoogleDialog extends DialogBox {
        DockPanel panel = new DockPanel();
        Button ok = new Button("OK");

        public GoogleDialog(final BookDetails book) {
//            Image image = new Image();
//            image.setUrl(book.getBookId());

            Label name = new Label("Name: "+book.getBookName());
            Label author = new Label("Author: "+book.getAuthorName());
            Label rating = new Label("Average Rating: "+ book.getRatings());
            setText("Book Details");
            setAnimationEnabled(true);
            setGlassEnabled(true);
            panel.setHeight("100");
            panel.setWidth("500");
            panel.setSpacing(10);
            panel.add(name,DockPanel.NORTH);
            panel.add(author,DockPanel.NORTH);
            panel.add(rating,DockPanel.NORTH);
            panel.add(ok,DockPanel.CENTER);
//            UrlBuilder url =new UrlBuilder();
//            if(book.getBookId() != null) {
//                url.setProtocol("https");
//                url.setHost("play.google.com");
//                url.setPath("/store/books/details");
//                String id = book.getBookId().substring(book.getBookId().lastIndexOf('?')+4,book.getBookId().lastIndexOf('&'));
//                url.setParameter("id",id);
//                url.setParameter("source","gbs_api");
//            } else {
//                url.setPath("null");
//            }
//            Anchor anchor = new Anchor("Preview >>",
//                    false,
//                    url.buildString(),
//                    "_blank");
//            panel.add(anchor,DockPanel.EAST);
            ok.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    GoogleDialog.this.hide();
                }
            });
            setWidget(panel);
        }
    }

    private static class UpdateBook extends Composite {
        String orig_id;
        TextBox bookName = new TextBox();
        TextBox bookId = new TextBox();
        TextBox authorName = new TextBox();
        ListBox ratings = new ListBox();
        final ListBox isavail = new ListBox();
        final Button update = new Button("Update!");
        final BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
        final String[] rate = new String[1];
        final Button back = new Button("Go back");


        public UpdateBook(BookDetails bookDetails) {
            RootPanel.get("search0").clear();
            RootPanel.get("search1").clear();
            orig_id = bookDetails.getBookId();
            bookName.setText(bookDetails.getBookName());
            bookId.setText(bookDetails.getBookId());
            authorName.setText(bookDetails.getAuthorName());
            rate[0] = "How would you rate this book?";
            ratings = ratinglistfunc(ratings, rate);
            populaterootpanel(bookId,bookName,authorName,ratings);
            RootPanel.get("slot4").add(update);
            RootPanel.get("addbook").add(back);

            update.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    if(rate[0] == "How would you rate this book?") {
                        Window.alert("Please choose a valid rating! :O");
                    } else {
                        BookDetails bookDetails = new BookDetails();
                        bookDetails.setBookId(bookId.getText());
                        bookDetails.setBookName(bookName.getText());
                        bookDetails.setAuthorName(authorName.getText());
                        bookDetails.setRatings(valueOf(rate[0]));
                        bookDetails.setIsAvailable(true);
                        final UpdateBookRequest book_to_be_updated = new UpdateBookRequest();
                        book_to_be_updated.setBookDetails(bookDetails);
                        bookServiceAsync.updateBook(orig_id, book_to_be_updated,
                                new AsyncCallback<Boolean>() {
                                    public void onFailure(Throwable caught) {
                                        Window.alert("Updating book failed :(");
                                    }

                                    public void onSuccess(Boolean result) {
                                        if( result == true) {
                                            Window.alert("Updated in the library. You can search for it using the book name!");
                                            cleareverything();
                                            getBooks();
                                        } else {
                                            Window.alert("Updating failed :(");
                                        }
                                    }
                                });
                    }
                }
            });
            back.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    cleareverything();
                    getBooks();
                }
            });
        }
    }

    private static void populaterootpanel(
            final TextBox bookId, final TextBox bookName,
            final TextBox authorName, final ListBox ratings) {
        RootPanel.get("slot0").clear();
        RootPanel.get("slot1").clear();
        RootPanel.get("slot2").clear();
        RootPanel.get("slot3").clear();
        RootPanel.get("slot0").add(bookId);
        RootPanel.get("slot1").add(bookName);
        RootPanel.get("slot2").add(authorName);
        RootPanel.get("slot3").add(ratings);
    }

    private static ListBox ratinglistfunc(final ListBox ratings, final String[] rate) {
        ratings.addItem("How would you rate this book?");
        ratings.addItem("1");
        ratings.addItem("2");
        ratings.addItem("3");
        ratings.addItem("4");
        ratings.addItem("5");
        ratings.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                if(ratings.getSelectedIndex() != 0) {
                    rate[0] = ratings.getValue(ratings.getSelectedIndex());
                } else {
                    Window.alert("Please choose a valid rating! :O");
                }
            }
        });
        return ratings;
    }

    private static void cleareverything() {
        RootPanel.get("search2").clear();
        RootPanel.get("header").clear();
        RootPanel.get("header2").clear();
        RootPanel.get("checker").clear();
        RootPanel.get("slot0").clear();
        RootPanel.get("slot1").clear();
        RootPanel.get("slot2").clear();
        RootPanel.get("slot3").clear();
        RootPanel.get("slot4").clear();
        RootPanel.get("bookName").clear();
        RootPanel.get("addbook").clear();
        RootPanel.get("upload").clear();
        RootPanel.get("upl").clear();
        RootPanel.get("search0").clear();
        RootPanel.get("search1").clear();
        RootPanel.get("search").clear();
        RootPanel.get("uploadfilelabel").clear();
    }
}


