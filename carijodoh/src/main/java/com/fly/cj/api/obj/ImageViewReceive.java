package com.fly.cj.api.obj;

public class ImageViewReceive {

    private final ImageViewReceive userObj;
    private String status;
    private user_Image user_Image;

    public ImageViewReceive getUserObj() {
        return userObj;
    }

    public ImageViewReceive(ImageViewReceive param_userObj) {
        this.userObj = param_userObj;
        status = param_userObj.getStatus();
        user_Image = param_userObj.getUserImage();
    }

    public class user_Image {
        private String user_image;

        public String getImage() {
            return user_image;
        }

        public void setImage(String user_image) {
            this.user_image = user_image;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public user_Image getUserImage() {
        return user_Image;
    }

    public void setUserImage(user_Image user_Image) {
        this.user_Image = user_Image;
    }
}