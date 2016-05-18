package com.fly.cj.api.obj;
 // Response From API
public class LoginReceive {

    private final LoginReceive userObj;
    private String status, auth_token, signature;

    public LoginReceive(LoginReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        auth_token = param_userObj.getAuth_token();
        signature = param_userObj.getSignature();
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

     public String getSignature() {
         return signature;
     }

     public void setSignature(String signature) {
         this.signature = signature;
     }

    public LoginReceive getUserObj() {
        return userObj;
    }

}
