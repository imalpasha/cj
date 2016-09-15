package com.fly.cj.ui.object;

public class ImageViewRequest {
    /*Local Data Send To Server*/
    private String user_id;
    private String auth_token;


    /*Initiate Class*/
    public ImageViewRequest(){}

    public ImageViewRequest(ImageViewRequest data){
        user_id = data.getUserId();
        auth_token = data.getAuth_token();
    }

    public String getUserId() {
        return user_id;
    }

    public void setUserId(String user_id) {
        this.user_id = user_id;
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
