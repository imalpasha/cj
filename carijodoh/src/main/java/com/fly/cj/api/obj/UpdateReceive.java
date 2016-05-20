package com.fly.cj.api.obj;
// Response From API
public class UpdateReceive {

    private final UpdateReceive userObj;
    private String status;
    private String userDOB;
    private String userMobile;
    private String userHeight;
    private String userWeight;
    private String userSmoke;
    private String userState;
    private String userTown;
    private String userEducation;
    private String userOccupation;
    String signature;
    private String auth_token;

    public UpdateReceive(UpdateReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        userDOB = param_userObj.getDOB();
        userMobile = param_userObj.getMobile();
        userHeight = param_userObj.getHeight();
        userWeight = param_userObj.getWeight();
        userSmoke = param_userObj.getSmoke();
        userWeight = param_userObj.getState();
        userWeight = param_userObj.getTown();
        userWeight = param_userObj.getEducation();
        userWeight = param_userObj.getOccupation();
        signature = param_userObj.getSignature();
        auth_token = param_userObj.getAuth_token();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getEducation () {
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

    public UpdateReceive getUserObj() {
        return userObj;
    }
}
