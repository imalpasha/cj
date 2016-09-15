package com.fly.cj.api.obj;
// Response From API
public class RegisterReceive {

    private final RegisterReceive userObj;
    private String status;
    private String message;
    private user_info user_info;

    public RegisterReceive getUserObj() {
        return userObj;
    }

    public RegisterReceive(RegisterReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
    }

    public class user_info{

        private String email;
        private String password;
        private String signature;

        public String getSignature() {
            return signature;
        }
        public void setSignature(String signature) {
            this.signature = signature;
        }

        public String getEmail() {
            return email;
        }
        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }
        public void setPassword(String password) {
            this.password = password;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public user_info getUserInfo() {
        return user_info;
    }

    public void setUserInfo(user_info userInfo) {
        this.user_info = userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}