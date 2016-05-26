package com.fly.cj.api.obj;

public class ProfileViewReceive {

    private final ProfileViewReceive userObj;
    private String auth_token;
    private String status;
    private user_profile user_profile;

    public ProfileViewReceive getUserObj() {
        return userObj;
    }

    public ProfileViewReceive(ProfileViewReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        user_profile = param_userObj.getUserProfile();
        auth_token = param_userObj.getAuth_token();
    }

    public class user_profile {
        private String user_dob;
        private String user_mobile;
        private String user_height;
        private String user_weight;
        private String user_smoke;
        private String user_state;
        private String user_town;
        private String user_education;
        private String user_occupation;

        public String getDOB() {
            return user_dob;
        }

        public void setDOB(String userDOB) {
            this.user_dob = userDOB;
        }

        public String getMobile() {
            return user_mobile;
        }

        public void setMobile(String userMobile) {
            this.user_mobile = userMobile;
        }

        public String getHeight() {
            return user_height;
        }

        public void setHeight(String userHeight) {
            this.user_height = userHeight;
        }

        public String getWeight() {
            return user_weight;
        }

        public void setWeight(String userWeight) {
            this.user_weight = userWeight;
        }

        public String getSmoke() {
            return user_smoke;
        }

        public void setSmoke(String userSmoke) {
            this.user_smoke = userSmoke;
        }

        public String getState() {
            return user_state;
        }

        public void setState(String userState) {
            this.user_state = userState;
        }

        public String getTown() {
            return user_town;
        }

        public void setTown(String userTown) {
            this.user_town = userTown;
        }

        public String getEducation() {
            return user_education;
        }

        public void setEducation(String userEducation) {
            this.user_education = userEducation;
        }

        public String getOccupation() {
            return user_occupation;
        }

        public void setOccupation(String userOccupation) {
            this.user_occupation = userOccupation;
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

    public user_profile getUserProfile() {
        return user_profile;
    }

    public void setUserProfile(user_profile user_profile) {
        this.user_profile = user_profile;
    }
}