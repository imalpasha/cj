package com.fly.cj.api.obj;

public class UploadImageReceive {

    private final UploadImageReceive userObj;
    private String status;

    public UploadImageReceive getUserObj() {
        return userObj;
    }

    public UploadImageReceive(UploadImageReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
