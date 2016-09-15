package com.fly.cj.ui.presenter;

import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;

public class GalleryPresenter {
    private SharedPrefManager pref;

    public interface GalleryView {
    }

    private GalleryView view;

    private final Bus bus;

    public GalleryPresenter(GalleryView view, Bus bus) {
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
