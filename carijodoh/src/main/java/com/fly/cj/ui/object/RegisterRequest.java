package com.fly.cj.ui.object;

public class RegisterRequest {

    /*Local Data Send To Server*/
    private String email;
    private String password;
    private String password_confirmation;

    /*Initiate Class*/
    public RegisterRequest(){
    }

    public RegisterRequest(RegisterRequest data){
        email = data.getEmail();
        password = data.getPassword();
        password_confirmation = data.getConfirmPassword();
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

    public String getConfirmPassword() {
        return password_confirmation;
    }

    public void setConfirmPassword(String password_confirmation) {
        this.password_confirmation = password_confirmation;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}
