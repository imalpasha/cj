package com.fly.cj.ui.object;

public class ProfileViewRequest {
    /*Local Data Send To Server*/
    private String signature;
    private String auth_token;


    /*Initiate Class*/
    public ProfileViewRequest(){}

    public ProfileViewRequest(ProfileViewRequest data){
        signature = data.getSignature();
        auth_token = data.getAuth_token();
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}
