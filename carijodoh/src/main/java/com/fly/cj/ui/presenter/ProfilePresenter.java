package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.api.obj.UpdateReceive;
import com.fly.cj.ui.object.ProfileViewRequest;
import com.fly.cj.ui.object.UpdateRequest;
import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ProfilePresenter {

    private SharedPrefManager pref;

    public interface ProfileView {

        void onUpdateSuccess(UpdateReceive obj);
        void onUpdateFailed(String dumm);
        void onViewSuccess(ProfileViewReceive obj);
    }

    private ProfileView view;
    private final Bus bus;

    public ProfilePresenter(ProfileView view, Bus bus) {
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
    public void updateFunction(UpdateRequest data) {
        //otto function driven
        bus.post(new UpdateRequest(data));
    }


    //start sending data to api request handler
    public void viewFunction(ProfileViewRequest data) {
        //otto function driven
        bus.post(new ProfileViewRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessUpdate(UpdateReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onUpdateSuccess(event.getUserObj());
    }

    @Subscribe
    public void onProfileViewSuccess(ProfileViewReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onViewSuccess(event.getUserObj());
    }

}
