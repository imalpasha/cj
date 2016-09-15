package com.fly.cj.api.obj;

public class ProfileViewReceive {

    private final ProfileViewReceive userObj;
    private String status;
    private user_profile user_profile;

    public ProfileViewReceive getUserObj() {
        return userObj;
    }

    public ProfileViewReceive(ProfileViewReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        user_profile = param_userObj.getUserProfile();
    }

    public class user_profile {
        private int id;
        private String user_name;
        private String user_sex;
        private String user_dob;
        private String user_mobile;
        private String user_height;
        private String user_weight;
        private String user_smoke;
        private String user_country;
        private String user_state;
        private String user_town;
        private String user_education;
        private String user_occupation;
        private String user_marital;
        private String user_children;
        private String user_relationship;
        private String user_polygamy;
        private String user_financial;
        private String user_package;
        private String user_image;
        private String user_id;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return user_name;
        }

        public void setName(String userName) {
            this.user_name = userName;
        }

        public String getSex() {
            return user_sex;
        }

        public void setSex(String userSex) {
            this.user_sex = userSex;
        }

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

        public String getCountry() {
            return user_country;
        }

        public void setCountry(String userCountry) {
            this.user_country = userCountry;
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

        public String getMaritial() {
            return user_marital;
        }

        public void setMaritial(String userMaritial) {
            this.user_marital = userMaritial;
        }

        public String getChildren() {
            return user_children;
        }

        public void setChildren(String userChildren) {
            this.user_children = userChildren;
        }

        public String getRelationship() {
            return user_relationship;
        }

        public void setRelationship(String userRelationship) {
            this.user_relationship = userRelationship;
        }

        public String getPolygamy() {
            return user_polygamy;
        }

        public void setPolygamy(String userPolygamy) {
            this.user_polygamy = userPolygamy;
        }

        public String getFinancial() {
            return user_financial;
        }

        public void setFinancial(String userFinancial) {
            this.user_financial = userFinancial;
        }

        public String getPackage() {
            return user_package;
        }

        public void setPackage(String userPackage) {
            this.user_package = userPackage;
        }

        public String getImage() {
            return user_image;
        }

        public void setImage(String userImage) {
            this.user_image = userImage;
        }

        public String getUserId() {
            return user_id;
        }

        public void setUserId(String userId) {
            this.user_id = userId;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public user_profile getUserProfile() {
        return user_profile;
    }

    public void setUserProfile(user_profile user_profile) {
        this.user_profile = user_profile;
    }
}