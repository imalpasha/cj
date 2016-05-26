package com.fly.cj.ui.object;

public class OnlineUserRequest {
    /*Local Data Send To Server*/
    private String email;
    private String password;

    /*Initiate Class*/
    public OnlineUserRequest(){}

    public OnlineUserRequest(OnlineUserRequest data){
        email = data.getEmail();
        password = data.getPassword();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}
