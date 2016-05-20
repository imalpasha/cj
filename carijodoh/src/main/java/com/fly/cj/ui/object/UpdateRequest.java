package com.fly.cj.ui.object;

public class UpdateRequest {
    /*Local Data Send To Server*/
    String userDOB;
    String userMobile;
    String userHeight;
    String userWeight;
    String userSmoke;
    String userState;
    String userTown;
    String userEducation;
    String userOccupation;
    String signature;
    private String auth_token;

    /*Initiate Class*/
    public UpdateRequest(){}

    public UpdateRequest(UpdateRequest data){
        userDOB = data.getDOB();
        userMobile = data.getMobile();
        userHeight = data.getHeight();
        userWeight = data.getWeight();
        userSmoke = data.getSmoke();
        userState = data.getState();
        userTown = data.getTown();
        userEducation = data.getEducation();
        userOccupation = data.getOccupation();
        signature = data.getSignature();
        auth_token = data.getAuth_token();
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

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

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
