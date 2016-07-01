package com.fly.cj.ui.object;

public class UploadImageRequest {
    /*Local Data Send To Server*/
    private String user_image, signature;
    private String auth_token;

    /*Initiate Class*/
    public UploadImageRequest(){}

    public UploadImageRequest(UploadImageRequest data){
        user_image = data.getUserImage();
        auth_token = data.getAuth_token();
        signature = data.getSignature();
    }

    public String getUserImage() {
        return user_image;
    }

    public void setUserImage(String user_image) {
        this.user_image = user_image;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}
