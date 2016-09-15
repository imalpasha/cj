package com.fly.cj.ui.object;

public class ActiveUserRequest {
    /*Local Data Send To Server*/
    String email;
    String password;
    private String auth_token;

    /*Initiate Class*/
    public ActiveUserRequest(){}

    public ActiveUserRequest(ActiveUserRequest data){
        email = data.getUsername();
        password = data.getPassword();
        auth_token = data.getAuth_token();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return email;
    }

    public void setUsername(String username) {
        this.email = username;
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
