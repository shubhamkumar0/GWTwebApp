package com.example.shared.login;

import com.google.gwt.user.client.rpc.IsSerializable;

public class SignUpResponse implements IsSerializable {
    private boolean isSignedUp;

    public SignUpResponse(){
         isSignedUp = false;
    }
    public boolean getSignedUp() {
        return isSignedUp;
    }

    public void setSignedUp(boolean signedUp) {
        isSignedUp = signedUp;
    }
}
