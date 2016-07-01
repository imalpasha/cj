package com.fly.cj.api.obj;

 // Response From API
public class LoginReceive {

     private final LoginReceive userObj;
     private String status, auth_token, signature;

     private User user;

     public LoginReceive getUserObj() {
         return userObj;
     }

     public LoginReceive(LoginReceive param_userObj) {
         this.userObj = param_userObj;
         status = param_userObj.getStatus();
         auth_token = param_userObj.getAuth_token();
         signature = param_userObj.getSignature();
         user = param_userObj.getUser();
     }

     public class User {
         private String user_id, user_name, user_dob;

         public String getUserId() {
             return user_id;
         }

         public void setUserId(String user_id) {
             this.user_id = user_id;
         }

         public String getUserName() {
             return user_name;
         }

         public void setUserName(String user_name) {
             this.user_name = user_name;
         }

         public String getUserDob() {
             return user_dob;
         }

         public void setUserDob(String user_dob) {
             this.user_dob = user_dob;
         }
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

     public User getUser(){
         return user;
     }

     public void setUser(User user){
         this.user = user;
     }
}
