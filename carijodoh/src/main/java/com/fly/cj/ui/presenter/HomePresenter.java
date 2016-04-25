package com.fly.cj.ui.presenter;

import com.fly.cj.MainFragmentActivity;
import com.fly.cj.ui.object.PushNotificationObj;
import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class HomePresenter {

    private SharedPrefManager pref;

    public interface PushNotification {

    }

    public interface HomeView {

    }

    private HomeView view;
    private PushNotification view3;

    private final Bus bus;

    public HomePresenter(HomeView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public HomePresenter(PushNotification view, Bus bus) {
        this.view3 = view;
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
