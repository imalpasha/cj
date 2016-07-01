package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.ActiveUserReceive;
import com.fly.cj.ui.object.ActiveUserRequest;
import com.fly.cj.ui.object.PushNotificationObj;
import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class HomePresenter {

    private SharedPrefManager pref;
    public interface PushNotification {    }

    public interface HomeView {
        void onViewActiveSuccess(ActiveUserReceive obj);
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

    //start sending data to api request handler
    public void viewList(ActiveUserRequest data) {
        //otto function driven
        bus.post(new ActiveUserRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessViewList(ActiveUserReceive event) {
        /*Save Session And Redirect To Homepage*/
        view.onViewActiveSuccess(event.getUserObj());
    }
}
