package com.fly.cj.ui.presenter;

import android.util.Log;

import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.ui.object.LoginRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class LoginPresenter {

    public interface LoginView {

        void onLoginSuccess(LoginReceive obj);
        void onLoginFailed(String dumm);
        //  void onPasswordRequestFailed(ForgotPasswordReceive obj);
        //void onPasswordRequesFailed(String dumm);


    }

    private final LoginView view;
    private final Bus bus;

    public LoginPresenter(LoginView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void loginFunction(LoginRequest data) {
        Log.e("xxxx",data.getUsername());
        bus.post(new LoginRequest(data));
    }


    @Subscribe
    public void onUserSuccessLogin(LoginReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onLoginSuccess(event.getUserObj());
    }



   // @Subscribe
    //public void onUserFailedReqPassword(SplashFailedConnect event) {
    /*@Subscribe
    public void onUserFailedReqPassword(FailedConnectToServer event) {
>>>>>>> 6401c7b9c51ce353bbc670f4f01fdaaabf4b7ad8
//
        /*//*Save Session And Redirect To Homepage*//**//*
      view.onPasswordRequestFailed(event.getDummy());
   }*/

}
