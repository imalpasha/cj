package com.fly.cj.ui.object;

public class ShowProfileRequest {
    /*Local Data Send To Server*/
    private String id;
    private String auth_token;


    /*Initiate Class*/
    public ShowProfileRequest(){}

    public ShowProfileRequest(ShowProfileRequest data){
        id = data.getId();
        auth_token = data.getAuth_token();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
