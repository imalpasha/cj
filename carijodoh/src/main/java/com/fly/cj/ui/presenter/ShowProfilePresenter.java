package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.ui.object.ShowProfileRequest;
import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ShowProfilePresenter {
    private SharedPrefManager pref;

    public interface ShowProfileView {
        void onShowSuccess(ProfileViewReceive obj);
    }

    private ShowProfileView view;
    private final Bus bus;

    public ShowProfilePresenter(ShowProfileView view, Bus bus) {
        this.view = view;
        this.bus = bus;
    }

    public void onResume() {
        bus.register(this);
    }

    public void onPause() {
        bus.unregister(this);
    }

    //start sending data to api request handler
    public void showFunction(ShowProfileRequest data) {
        //otto function driven
        bus.post(new ShowProfileRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onProfileShowSuccess(ProfileViewReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onShowSuccess(event.getUserObj());
    }

}

