package com.fly.cj.ui.presenter;

import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;


public class ProfilePresenter {

    private SharedPrefManager pref;

    public interface ProfileView {

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
}
