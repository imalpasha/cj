package com.fly.cj.api.obj;
// Response From API
public class UpdateReceive {

    private final UpdateReceive userObj;
    private String status;

    public UpdateReceive(UpdateReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateReceive getUserObj() {
        return userObj;
    }
}
