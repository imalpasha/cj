package com.fly.cj.ui.object;

public class LoginRequest{

    /*Local Data Send To Server*/
    String email;
    String password;
    String deviceId;

    /*Initiate Class*/
    public LoginRequest(){    }

    public LoginRequest(LoginRequest data){
        email = data.getUsername();
        password = data.getPassword();
        deviceId = data.getDeviceId();
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

    public String getDeviceId() {

        return deviceId;
    }

    public void setDeviceId(String deviceId) {

        this.deviceId = deviceId;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }
}
