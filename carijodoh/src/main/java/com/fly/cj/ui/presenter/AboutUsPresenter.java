package com.fly.cj.ui.presenter;

import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;

public class AboutUsPresenter {
    private SharedPrefManager pref;

    public interface AboutUsView {
    }

    private AboutUsView view;

    private final Bus bus;

    public AboutUsPresenter(AboutUsView view, Bus bus) {
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