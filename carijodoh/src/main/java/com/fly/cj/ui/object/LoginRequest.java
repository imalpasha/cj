package com.fly.cj.ui.object;

public class LoginRequest{

    /*Local Data Send To Server*/
    String email;
    String password;

    /*Initiate Class*/
    public LoginRequest(){
    }

    //public LoginRequest(String username123){
    //    this.username = username123;
    //}

    public LoginRequest(LoginRequest data){
        email = data.getUsername();
        password = data.getPassword();
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



    /*Response Data From Server*/
    String status;


    public String getStatus() {
        return status;
    }


}
