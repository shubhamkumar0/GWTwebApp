package com.example.client;

import com.example.shared.book.BookDetails;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.Style;
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
                                    getBooks();
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
                Window.alert("getAllBooksName failed.");
            }

            public void onSuccess(List<String> result) {
                MainPage mainPage = new MainPage(result);
            }
        });
    }

    private static class MainPage extends Composite {

        ListBox books = new ListBox();
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
                            Window.alert("getBookDetails failed.");
                        }

                        public void onSuccess(BookDetails result) {
                            MyDialog myDialog = new MyDialog(result);
                            myDialog.show();
                        }
                    });
                    //Window.alert(books.getValue(books.getSelectedIndex()));
                }
            });
            RootPanel.get("bookName").add(books);
        }
    }

    private static class MyDialog extends DialogBox {
        public MyDialog(BookDetails bookDetails) {
            setAnimationEnabled(true);
            setGlassEnabled(true);
            Button ok = new Button("OK");
            ok.addClickHandler(new ClickHandler() {
                public void onClick(ClickEvent event) {
                    MyDialog.this.hide();
                }
            });
            Label bookId = new Label(bookDetails.getBookId());
            Label bookName = new Label(bookDetails.getBookName());
            Label AName = new Label(bookDetails.getAuthorName());
            Label rating = new Label(String.valueOf(bookDetails.getRatings()));
            Label isAvail = new Label(String.valueOf(bookDetails.getIsAvailable()));
            VerticalPanel panel = new VerticalPanel();
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
            setWidget(panel);
        }

    }
}


