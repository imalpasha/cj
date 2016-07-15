package com.fly.cj.api.obj.ChatPackageReceive;

/**
 * Created by Dell on 5/18/2016.
 */
public class ChatReceive {

    private String status;
    private ChatReceive chatReceive;

    public ChatReceive(ChatReceive param) {
        this.status = param.getMessage();
        this.chatReceive = param
                .getChatReceive();
    }

    public ChatReceive getChatReceive() {
        return chatReceive;
    }

    public void setChatReceive(ChatReceive chatReceive) {
        this.chatReceive = chatReceive;
    }

    public String getMessage() {
        return status;
    }

    public void setMessage(String message) {
        this.status = message;
    }

}
