package com.fly.cj.ui.presenter;

import android.util.Log;

import com.fly.cj.api.obj.ChangePasswordReceive;
import com.fly.cj.ui.object.ChangePasswordRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class PasswordExpiredPresenter {
    public interface PasswordExpiredView {
        void onUpdatePasswordSuccess(ChangePasswordReceive event);
    }

    private final PasswordExpiredView view;
    private final Bus bus;

    public PasswordExpiredPresenter(PasswordExpiredView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }


    public void changePassword(ChangePasswordRequest data) {
        Log.e("xxxx",data.getEmail());
        Log.e("xxxx",data.getCurrentPassword());
        Log.e("xxxx",data.getNewPassword());
        bus.post(new ChangePasswordRequest(data));
    }

    // @Subscribe
    @Subscribe
    public void onUserSuccessReqPassword(ChangePasswordReceive event) {

        //*Save Session And Redirect To Homepage*//*
        view.onUpdatePasswordSuccess(event.getUserObj());
    }

}
