//package com.example.server.login.SignUp;
//
//import com.example.client.LoginService;
//import com.example.client.SignUpUser;
//import com.example.shared.login.SignUpRequest;
//import com.example.shared.login.SignUpResponse;
//
//public class SignUpUserImpl implements SignUpUser {
//    //service layer
//    private LoginService loginService;
//
//    public SignUpUserImpl(LoginService loginServiceImpl) {
//        loginService = loginServiceImpl;
//    }
//
//    @Override
//    public SignUpResponse addUserToDb(SignUpRequest signUpRequest) {
//        boolean ans = loginService.signUpUser(signUpRequest.getName(),signUpRequest.getEmail(),signUpRequest.getPassword());
//        SignUpResponse signUpResponse = new SignUpResponse();
//        signUpResponse.setSignedUp(ans);
//        return signUpResponse;
//    }
//
//
//    public void setLoginService(LoginService loginService) {
//        this.loginService = loginService;
//    }
//}
