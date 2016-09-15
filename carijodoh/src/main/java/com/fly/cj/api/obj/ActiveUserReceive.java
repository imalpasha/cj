package com.fly.cj.api.obj;

import java.util.ArrayList;
import java.util.List;

public class ActiveUserReceive {

    private final ActiveUserReceive userObj;
    private String status;
    private List<ListUser> listUser;


    public ActiveUserReceive getUserObj() {
        return userObj;
    }
    public List<ListUser> getListUser() {
        return listUser;
    }

    public void setListUser(List<ListUser> listUser) {
        this.listUser = listUser;
    }
    public ActiveUserReceive(ActiveUserReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        listUser = param_userObj.getListUser();
    }

    public class ListUser {
        private String email;
        private String user_status;
        private String id;
        private String user_id;
        private String user_dob;
        private String user_image;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUserStatus() {
            return user_status;
        }

        public void setUserStatus(String user_status) {
            this.user_status = user_status;
        }

        public String getUserId() {
            return user_id;
        }

        public void setUserId(String user_id) {
            this.user_id = user_id;
        }

        public String getUserDOB() {
            return user_dob;
        }

        public void setUserDOB(String user_dob) {
            this.user_dob = user_dob;
        }

        public String getUserImage() {
            return user_image;
        }

        public void setUserImage(String user_image) {
            this.user_image = user_image;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
