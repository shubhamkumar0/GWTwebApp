package com.example.client;

import com.example.shared.login.UserDetails;
import com.example.shared.search.SearchRequest;
import com.example.shared.search.SearchResponse;
import com.example.shared.book.AddBookRequest;
import com.example.shared.book.BookDetails;
import com.example.shared.book.UpdateBookRequest;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;

import java.util.Date;
import java.util.List;

import static java.lang.Float.valueOf;

public class Kitab implements EntryPoint {
    //////////////
    final Button login = new Button("Login");
    final Button signup = new Button("Sign Up");
//    private static final EventBus s_eventBus = new SimpleEventBus();

    public void onModuleLoad() {
        RootPanel.get("login").add(login);
        RootPanel.get("signup").add(signup);
        init();
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
        final PasswordTextBox password = new PasswordTextBox();
        private LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);

        public RegisterPage() {
            RootPanel.get("signup").clear();
            RootPanel.get("login").clear();
            RootPanel.get("slot1").add(name);
            RootPanel.get("slot2").add(email);
            RootPanel.get("slot3").add(password);
            RootPanel.get("slot4").add(button);
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
                                        getBooks();
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
        final PasswordTextBox password = new PasswordTextBox();
        private LoginServiceAsync loginServiceAsync = GWT.create(LoginService.class);

        public LoginPage() {
            email.setText("email");
            password.setText("password");
            RootPanel.get("login").clear();
            RootPanel.get("signup").clear();
//            RootPanel.get("slot0").add(back);
            RootPanel.get("slot2").add(email);
            RootPanel.get("slot3").add(password);
            RootPanel.get("slot4").add(button);
            //signup();
            button.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    loginServiceAsync.validateUser(email.getText(), password.getText(),
                            new AsyncCallback<Boolean>() {
                                public void onFailure(Throwable caught) {
                                    Window.alert("Login failed.");
                                }
                                public void onSuccess(Boolean result) {
                                    if(result == false) {
                                        Window.alert("Login failed.");
                                    } else {
//                                        String sessionID = UserDetails.getSessionId();
//                                        final long DURATION = 1000 * 60 * 60 * 24;
//                                        Date expires = new Date(System.currentTimeMillis() + DURATION);
//                                        Cookies.setCookie("sid", sessionID, expires, null, "/", false);
                                        getBooks();
                                    }
                                }
                            });
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
        final Button addbook = new Button("addbook");
        final Button search = new Button("Search");
        TextBox search_what = new TextBox();
        final SearchServiceAsync searchServiceAsync = GWT.create(SearchService.class);

        MainPage(List<String> bookNames) {
            cleareverything();

            RootPanel.get("slot0").add(search_what);
            RootPanel.get("bookName").add(books);
            RootPanel.get("addbook").add(addbook);
            RootPanel.get("slot1").add(search);

            for( String name: bookNames) {
                books.addItem(name);
            }
            getBookDetails(books);

            addbook.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    RootPanel.get("addbook").clear();
                    Book_Adder book_adder = new Book_Adder();
                }
            });

            //To use in updating list functionality
            search_what.addKeyUpHandler(new KeyUpHandler() {
                @Override
                public void onKeyUp(KeyUpEvent event) {
                    UpdateList(search_what.getText());
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

                                                    //TO-DO
                                                    MyDialog myDialog = new MyDialog(result.getBookDetails().get(0));
                                                    int left = Window.getClientWidth()/ 2;
                                                    int top = Window.getClientHeight()/ 2;
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
        }
    }

    private static void UpdateList(String text) {
        if(text != "") {
            RootPanel.get("bookName").clear();
            final ListBox updatedList = new ListBox();
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
                                Window.alert("Book not found! :3");
                            }
                        }
                    });
            getBookDetails(updatedList);
            RootPanel.get("bookName").add(updatedList);
        }
    }

    private static void getBookDetails(final ListBox books) {

        books.addChangeHandler(new ChangeHandler() {

            @Override
            public void onChange(ChangeEvent event) {
                BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
                bookServiceAsync.getBookDetailsByBookName(books.getValue(books.getSelectedIndex()),
                        new AsyncCallback<BookDetails>() {
                            public void onFailure(Throwable caught) {
                                //figure out the best message to show here
                                Window.alert("Sorry, could not fetch book details :|");
                            }

                            public void onSuccess(BookDetails result) {
                                MyDialog myDialog = new MyDialog(result);
                                int left = Window.getClientWidth()/ 2;
                                int top = Window.getClientHeight()/ 2;
                                myDialog.setPopupPosition(left, top);
                                myDialog.show();
                            }
                        });
            }
        });
    }

    private static class Book_Adder extends Composite {

        final Button add = new Button("Add!");
        final TextBox bookId = new TextBox();
        final TextBox bookName = new TextBox();
        final TextBox authorName = new TextBox();
        ListBox ratings = new ListBox();
        final String[] rate = new String[1];
        final BookServiceAsync bookServiceAsync = GWT.create(BookService.class);

        public Book_Adder() {
            bookId.setText("Book Id");
            bookName.setText("Book Name");
            authorName.setText("Author Name");
            rate[0] = "How would you rate this book?";
            ratings = ratinglistfunc(ratings, rate);
            populaterootpanel(bookId,bookName,authorName,ratings);
            RootPanel.get("slot4").add(add);

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
        }
    }

    private static class MyDialog extends DialogBox {

        VerticalPanel panel = new VerticalPanel();

        public MyDialog(final BookDetails bookDetails) {
            setText("Book Details");
            setAnimationEnabled(true);
            setGlassEnabled(true);
            Button ok = new Button("OK");
            Button update = new Button("Update");
//            Button next = new Button("Next->");
            ok.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                }
            });
            update.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    cleareverything();
                    UpdateBook updateBook = new UpdateBook(bookDetails);
                }
            });
//            next.addClickHandler(new ClickHandler() {
//                @Override
//                public void onClick(ClickEvent event) {
//
//                }
//            });
            Label id = new Label(bookDetails.getBookId());
            Label name = new Label(bookDetails.getBookName());
            Label author = new Label(bookDetails.getAuthorName());
            Label rating = new Label(String.valueOf(bookDetails.getRatings()));
            Label isAvail = new Label(String.valueOf(bookDetails.getIsAvailable()));
            panel.setHeight("100");
            panel.setWidth("300");
            panel.setSpacing(10);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_CENTER);
            panel.add(id);
            panel.add(name);
            panel.add(author);
            panel.add(rating);
            panel.add(isAvail);
            panel.add(ok);
            panel.add(update);
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
        RootPanel.get("slot0").clear();
        RootPanel.get("slot1").clear();
        RootPanel.get("slot2").clear();
        RootPanel.get("slot3").clear();
        RootPanel.get("slot4").clear();
        RootPanel.get("bookName").clear();
        RootPanel.get("addbook").clear();
    }
}


