//package com.example.client;
//
//import com.google.gwt.core.com.example.client.EntryPoint;
//import com.google.gwt.event.dom.com.example.client.ClickEvent;
//import com.google.gwt.event.dom.com.example.client.ClickHandler;
//import com.google.gwt.user.com.example.client.ui.*;
//import com.google.gwt.user.com.example.client.rpc.AsyncCallback;
//import java.sql.SQLException;
//
///**
// * Entry point classes define <code>onModuleLoad()</code>
// */
//public class LoginApplication implements EntryPoint {
//
//
//    /**
//     * This is the entry point method.
//     *
//     * Will have SignIn/SignUp buttons.
//     *
//     *
//     */
//    public void onModuleLoad() {
//        final Button button = new Button("Sign Up!");
//        final Label label = new Label();
//        final TextBox name = new TextBox();
//        final TextBox email = new TextBox();
//        final PasswordTextBox password = new PasswordTextBox();
//
//        button.addClickHandler(new ClickHandler() {
//            public void onClick(ClickEvent event)  {
//            if (name.getText().equals("") || email.getText().equals("") || password.getText().equals("")) {
////                LoginService.App.getInstance().showMessage("ERROR!", new MyAsyncCallback(label));
//            } else {
//                label.setText("");
//                try {
//                    LoginService.App.getInstance().signUpUser(name.getText(), email.getText(), password.getText(), MyAsyncCallback<Boolean>);
//                } catch (ClassNotFoundException e) {
//                    e.printStackTrace();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//            }
//            }
//        });
//
//        // Assume that the host HTML has elements defined whose
//        // IDs are "slot1", "slot2".  In a real app, you probably would not want
//        // to hard-code IDs.  Instead, you could, for example, search for all
//        // elements with a particular CSS class and replace them with widgets.
//        //
////        RootPanel.get("slot1").add(name);
////        RootPanel.get("slot2").add(email);
////        RootPanel.get("slot3").add(password);
////        RootPanel.get("slot4").add(button);
//    }
//
//    private static class MyAsyncCallback implements AsyncCallback<Boolean> {
//        private Label label;
//
//        MyAsyncCallback(Label label) {
//            this.label = label;
//        }
//
//        public void onSuccess(String result) {
//            label.getElement().setInnerHTML(result);
//        }
//
//        public void onFailure(Throwable throwable) {
//            label.setText("Failed to receive answer from com.example.server!");
//        }
//    }
//}
