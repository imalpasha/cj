package com.fly.cj.api;

import com.fly.cj.api.obj.ChatPackageReceive.ChatReceive;
import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.api.obj.RegisterReceive;
import com.fly.cj.api.obj.UpdateReceive;
import com.fly.cj.ui.object.ChatRequest;
import com.fly.cj.ui.object.LoginRequest;
import com.fly.cj.ui.object.RegisterRequest;
import com.fly.cj.ui.object.UpdateRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

public interface ApiService {

    @POST("/login")
    void onRequestToLogin(@Body LoginRequest task, Callback<LoginReceive> callback);

    @POST("/register")
    void onRequestToRegister(@Body RegisterRequest obj, Callback<RegisterReceive> callback);

    @POST("/update")
    void onRequestToUpdate(@Body UpdateRequest task, Callback<UpdateReceive> callback);

    @POST("/chat")
    void onChatRequest(@Body ChatRequest task, Callback<ChatReceive> callback);

}


