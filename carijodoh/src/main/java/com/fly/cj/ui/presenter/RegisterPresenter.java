package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.ui.object.PushNotificationObj;
import com.squareup.otto.Bus;

/**
 * Created by User on 4/28/2016.
 */
public class RegisterPresenter {

    public interface RegisterView {

    }
    private RegisterView view;

    private final Bus bus;

    public RegisterPresenter(RegisterView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }


    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    public void onRegisterNotification(PushNotificationObj info) {
        bus.post(new PushNotificationObj(info));
    }
}