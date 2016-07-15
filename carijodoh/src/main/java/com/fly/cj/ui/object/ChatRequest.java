package com.fly.cj.ui.object;

/**
 * Created by Dell on 5/18/2016.
 */
public class ChatRequest {

    private String message;
    private ChatRequest chatRequest;

    /*Initiate Class*/
    public ChatRequest(){}

    public ChatRequest(ChatRequest data){
        message = data.getMessage();
        chatRequest = data.getChatRequest();
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ChatRequest getChatRequest() {
        return chatRequest;
    }

    public void setChatRequest(ChatRequest chatRequest) {
        this.chatRequest = chatRequest;
    }


}
