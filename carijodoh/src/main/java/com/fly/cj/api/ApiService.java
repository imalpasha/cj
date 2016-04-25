package com.fly.cj.api;

import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.ui.object.FlightSummary;
import com.fly.cj.ui.object.LoginRequest;
import com.fly.cj.ui.object.RegisterObj;
import com.fly.cj.ui.object.Signature;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

public interface ApiService {

    @GET("/users/{user}")
    void getFeed2(@Path("user") String user, Callback<LoginRequest> callback);

    @POST("/login")
    void onRequestToLogin(@Body LoginRequest task, Callback<LoginReceive> callback);


}


