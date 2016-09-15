package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.ImageViewReceive;
import com.fly.cj.api.obj.UploadImageReceive;
import com.fly.cj.ui.object.ImageViewRequest;
import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.ui.object.ProfileViewRequest;
import com.fly.cj.api.obj.UpdateReceive;
import com.fly.cj.ui.object.UpdateRequest;
import com.fly.cj.ui.object.UploadImageRequest;
import com.fly.cj.utils.SharedPrefManager;

import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class UpdateProfilePresenter {
    private SharedPrefManager pref;

    public interface ProfileView {
        void onUpdateSuccess(UpdateReceive obj);
        void onUpdateFailed(String dumm);
        void onViewSuccess(ProfileViewReceive obj);
        //void onViewImageSuccess(ImageViewReceive obj);
        void onUploadImageSuccess(UploadImageReceive obj);
    }

    private ProfileView view;
    private final Bus bus;

    public UpdateProfilePresenter(ProfileView view, Bus bus) {
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

    public void viewFunction(ProfileViewRequest data) {
        bus.post(new ProfileViewRequest(data));
    }

    /*public void viewImageFunction(ImageViewRequest data) {
        bus.post(new ImageViewRequest(data));
    }*/

    public void uploadImageFunction(UploadImageRequest data) {
        bus.post(new UploadImageRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessUpdate(UpdateReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onUpdateSuccess(event.getUserObj());
    }

    @Subscribe
    public void onProfileViewSuccess(ProfileViewReceive event) {

        view.onViewSuccess(event.getUserObj());
    }

    /*@Subscribe
    public void onImageViewSuccess(ImageViewReceive event) {

        view.onViewImageSuccess(event.getUserObj());
    }*/

    @Subscribe
    public void onImageUploadSuccess(UploadImageReceive event) {

        view.onUploadImageSuccess(event.getUserObj());
    }
}
