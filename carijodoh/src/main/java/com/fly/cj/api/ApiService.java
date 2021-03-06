package com.fly.cj.api;

<<<<<<< HEAD
import com.fly.cj.api.obj.ChatPackageReceive.ChatReceive;
=======
import com.fly.cj.api.obj.ActiveUserReceive;
import com.fly.cj.api.obj.ImageViewReceive;
>>>>>>> 742552ab52a6708cc02d07f2ce6cbfb048e66cf0
import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.api.obj.RegisterReceive;
import com.fly.cj.api.obj.UpdateReceive;
<<<<<<< HEAD
import com.fly.cj.ui.object.ChatRequest;
=======
import com.fly.cj.api.obj.UploadImageReceive;
import com.fly.cj.ui.object.ActiveUserRequest;
import com.fly.cj.ui.object.ImageViewRequest;
>>>>>>> 742552ab52a6708cc02d07f2ce6cbfb048e66cf0
import com.fly.cj.ui.object.LoginRequest;
import com.fly.cj.ui.object.ProfileViewRequest;
import com.fly.cj.ui.object.RegisterRequest;
import com.fly.cj.ui.object.ShowProfileRequest;
import com.fly.cj.ui.object.UpdateRequest;
import com.fly.cj.ui.object.UploadImageRequest;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;

public interface ApiService {

    @POST("/login")
    void onRequestToLogin(@Body LoginRequest task, Callback<LoginReceive> callback);

    @POST("/register")
    void onRequestToRegister(@Body RegisterRequest obj, Callback<RegisterReceive> callback);

    @POST("/update")
    void onRequestToUpdate(@Header("Authorization") String token, @Body UpdateRequest task, Callback<UpdateReceive> callback);

<<<<<<< HEAD
    @POST("/chat")
    void onChatRequest(@Body ChatRequest task, Callback<ChatReceive> callback);

}
=======
   @POST("/getProfile")
   void onRequestToView(@Header("Authorization") String token, @Body ProfileViewRequest task, Callback<ProfileViewReceive> callback);
>>>>>>> 742552ab52a6708cc02d07f2ce6cbfb048e66cf0

    @POST("/list")
    void onRequestToList(@Header("Authorization") String token, @Body ActiveUserRequest task, Callback<ActiveUserReceive> callback);

    @POST("/getDetail")
    void onRequestToShow(@Header("Authorization") String token, @Body ShowProfileRequest task, Callback<ProfileViewReceive> callback);

    @POST("/getImage")
    void onRequestToDownload(@Header("Authorization") String token, @Body ImageViewRequest task, Callback<ImageViewReceive> callback);

    @POST("/uploadImage")
    void onRequestToUpload(@Header("Authorization") String token, @Body UploadImageRequest task, Callback<UploadImageReceive> callback);
}
