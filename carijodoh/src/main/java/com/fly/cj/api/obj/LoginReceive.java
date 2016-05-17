package com.fly.cj.api.obj;
 // Response From API
public class LoginReceive {

    private final LoginReceive userObj;
    private String status;

    public LoginReceive(LoginReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LoginReceive getUserObj() {
        return userObj;
    }

}
