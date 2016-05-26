package com.fly.cj.api.obj;

// Response From API
public class UpdateReceive {

    private final UpdateReceive userObj;
    private String status, auth_token;

    public UpdateReceive getUserObj() {
        return userObj;
    }

    public UpdateReceive(UpdateReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        auth_token = param_userObj.getAuth_token();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }
}
