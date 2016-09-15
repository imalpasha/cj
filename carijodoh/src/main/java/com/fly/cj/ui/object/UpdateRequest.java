package com.fly.cj.ui.object;

public class UpdateRequest {
    /*Local Data Send To Server*/
    String userName;
    String userSex;
    String userDOB;
    String userMobile;
    String userHeight;
    String userWeight;
    String userSmoke;
    String userCountry;
    String userState;
    String userTown;
    String userEducation;
    String userOccupation;
    String signature;
    String userMarital;
    String userChildren;
    String userRelationship;
    String userPolygamy;
    String userFinancial;
    String userImage;
    private String auth_token;

    /*Initiate Class*/
    public UpdateRequest(){}

    public UpdateRequest(UpdateRequest data){
        userName = data.getName();
        userSex = data.getSex();
        userDOB = data.getDOB();
        userMobile = data.getMobile();
        userHeight = data.getHeight();
        userWeight = data.getWeight();
        userSmoke = data.getSmoke();
        userCountry = data.getCountry();
        userState = data.getState();
        userTown = data.getTown();
        userEducation = data.getEducation();
        userOccupation = data.getOccupation();
        userMarital = data.getMaritial();
        userChildren = data.getChildren();
        userRelationship = data.getRelationship();
        userPolygamy = data.getPolygamy();
        userFinancial = data.getFinancial();
        signature = data.getSignature();
        auth_token = data.getAuth_token();
        userImage = data.getImage();
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public String getSex() {
        return userSex;
    }

    public void setSex(String userSex) {
        this.userSex = userSex;
    }

    public String getDOB() {
        return userDOB;
    }

    public void setDOB(String userDOB) {
        this.userDOB = userDOB;
    }

    public String getMobile() {
        return userMobile;
    }

    public void setMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getHeight() {
        return userHeight;
    }

    public void setHeight(String userHeight) {
        this.userHeight = userHeight;
    }

    public String getWeight() {
        return userWeight;
    }

    public void setWeight(String userWeight) {
        this.userWeight = userWeight;
    }

    public String getSmoke() {
        return userSmoke;
    }

    public void setSmoke(String userSmoke) {
        this.userSmoke = userSmoke;
    }

    public String getCountry() {
        return userCountry;
    }

    public void setCountry(String userCountry) {
        this.userCountry = userCountry;
    }

    public String getState() {
        return userState;
    }

    public void setState(String userState) {
        this.userState = userState;
    }

    public String getTown() {
        return userTown;
    }

    public void setTown(String userTown) {
        this.userTown = userTown;
    }

    public String getEducation() {
        return userEducation;
    }

    public void setEducation(String userEducation) {
        this.userEducation = userEducation;
    }

    public String getOccupation() {
        return userOccupation;
    }

    public void setOccupation(String userOccupation) {
        this.userOccupation = userOccupation;
    }

    public String getMaritial() {
        return userMarital;
    }

    public void setMaritial(String userMarital) {
        this.userMarital = userMarital;
    }

    public String getChildren() {
        return userChildren;
    }

    public void setChildren(String userChildren) {
        this.userChildren = userChildren;
    }

    public String getRelationship() {
        return userRelationship;
    }

    public void setRelationship(String userRelationship) {
        this.userRelationship = userRelationship;
    }

    public String getPolygamy() {
        return userPolygamy;
    }

    public void setPolygamy(String userPolygamy) {
        this.userPolygamy = userPolygamy;
    }

    public String getFinancial() {
        return userFinancial;
    }

    public void setFinancial(String userFinancial) {
        this.userFinancial = userFinancial;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getImage(){return userImage;}

    public void setImage(String userImage){this.userImage = userImage; }

    public String getAuth_token() {
        return auth_token;
    }

    public void setAuth_token(String auth_token) {
        this.auth_token = auth_token;
    }

    /*Response Data From Server*/
    String status;

    public String getStatus() {
        return status;
    }

}
