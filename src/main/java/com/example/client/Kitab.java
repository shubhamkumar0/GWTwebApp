package com.example.client;

import com.example.shared.book.AddBookRequest;
import com.example.shared.book.BookDetails;
import com.example.shared.book.UpdateBookRequest;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
import com.google.gwt.dom.client.Text;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import java.util.List;

import static com.google.gwt.dom.client.Style.Unit.EM;
import static com.google.gwt.dom.client.Style.Unit.PCT;
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
                                    Window.alert("signup failed.");
                                }

                                public void onSuccess(Boolean result) {
                                    getBooks();
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
            RootPanel.get("slot2").add(email);
            RootPanel.get("slot3").add(password);
            RootPanel.get("slot4").add(button);
            //signup();
            button.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    loginServiceAsync.validateUser(email.getText(), password.getText(),
                            new AsyncCallback<Boolean>() {
                                public void onFailure(Throwable caught) {
                                    Window.alert("login failed.");
                                }
                                public void onSuccess(Boolean result) {
                                    if(result == false) {
                                        Window.alert("login failed.");
                                    } else {
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
                Window.alert("Sorry, could not fetch books :(");
            }

            public void onSuccess(List<String> result) {
                MainPage mainPage = new MainPage(result);
            }
        });
    }

    private static class MainPage extends Composite {

        ListBox books = new ListBox();
        final Button addbook = new Button("addbook");
        MainPage(List<String> bookNames) {
            RootPanel.get("slot1").clear();
            RootPanel.get("slot2").clear();
            RootPanel.get("slot3").clear();
            RootPanel.get("slot4").clear();
            for( String name: bookNames) {
                books.addItem(name);
            }
            books.addChangeHandler(new ChangeHandler() {

                @Override
                public void onChange(ChangeEvent event) {
                    BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
                    bookServiceAsync.getBookDetailsByBookName(books.getValue(books.getSelectedIndex()),
                            new AsyncCallback<BookDetails>() {
                        public void onFailure(Throwable caught) {
                            //figure out the best message to show here
                            Window.alert("Sorry, could not fetch book details. :(");
                        }

                        public void onSuccess(BookDetails result) {
                            MyDialog myDialog = new MyDialog(result);
                            myDialog.show();
                        }
                    });
                    //Window.alert(books.getValue(books.getSelectedIndex()));
                }
            });

            addbook.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    RootPanel.get("addbook").clear();
                    Book_Adder book_adder = new Book_Adder();
                }
            });

            RootPanel.get("bookName").add(books);
            RootPanel.get("addbook").add(addbook);
        }
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
                        Window.alert("Please choose a valid rating!");
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
                                        Window.alert("adding book failed.");
                                    }

                                    public void onSuccess(Boolean result) {
                                        Window.alert("Added the book to library. You can search for it using the book name!");
                                        backtomain();
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
            setAnimationEnabled(true);
            setGlassEnabled(true);
            Button ok = new Button("OK");
            Button update = new Button("Update");
            ok.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                }
            });
            update.addClickHandler(new ClickHandler() {
                @Override
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                    UpdateBook updateBook = new UpdateBook(bookDetails);
                }
            });
            Label bookId = new Label(bookDetails.getBookId());
            Label bookName = new Label(bookDetails.getBookName());
            Label AName = new Label(bookDetails.getAuthorName());
            Label rating = new Label(String.valueOf(bookDetails.getRatings()));
            Label isAvail = new Label(String.valueOf(bookDetails.getIsAvailable()));

            panel.setHeight("100");
            panel.setWidth("300");
            panel.setSpacing(10);
            panel.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_RIGHT);
            panel.setVerticalAlignment(HasVerticalAlignment.ALIGN_BOTTOM);
            panel.add(bookId);
            panel.add(bookName);
            panel.add(AName);
            panel.add(rating);
            panel.add(isAvail);
            panel.add(ok);
            panel.add(update);
            setWidget(panel);
        }
    }

    private static class UpdateBook extends Composite {
        TextBox bookName = new TextBox();
        TextBox bookId = new TextBox();
        TextBox authorName = new TextBox();
        ListBox ratings = new ListBox();
        final ListBox isavail = new ListBox();
        final Button update = new Button("Update!");
        final BookServiceAsync bookServiceAsync = GWT.create(BookService.class);
        final String[] rate = new String[1];


        public UpdateBook(BookDetails bookDetails) {
            bookName.setText(bookDetails.getBookName());
            bookId.setText(bookDetails.getBookId());
            authorName.setText(bookDetails.getAuthorName());
            rate[0] = "How would you rate this book?";
            ratings = ratinglistfunc(ratings, rate);
            populaterootpanel(bookId,bookName,authorName,ratings);
            RootPanel.get("slot4").add(update);

            update.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    if(rate[0] == "How would you rate this book?") {
                        Window.alert("Please choose a valid rating!");
                    } else {
                        BookDetails bookDetails = new BookDetails();
                        bookDetails.setBookId(bookId.getText());
                        bookDetails.setBookName(bookName.getText());
                        bookDetails.setAuthorName(authorName.getText());
                        bookDetails.setRatings(valueOf(rate[0]));
                        bookDetails.setIsAvailable(true);
                        final UpdateBookRequest book_to_be_updated = new UpdateBookRequest();
                        book_to_be_updated.setBookDetails(bookDetails);
                        bookServiceAsync.updateBook(book_to_be_updated,
                                new AsyncCallback<Boolean>() {
                                    public void onFailure(Throwable caught) {
                                        Window.alert("updating book failed.");
                                    }

                                    public void onSuccess(Boolean result) {
                                        Window.alert("Updated in the library. You can search for it using the book name!");
                                        backtomain();
                                    }
                                });
                    }
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
                    Window.alert("Please choose a valid rating!");
                }
            }
        });
        return ratings;
    }

    private static void backtomain() {
        RootPanel.get("bookName").clear();
        RootPanel.get("addbook").clear();
        RootPanel.get("slot0").clear();
        getBooks();
    }
}


