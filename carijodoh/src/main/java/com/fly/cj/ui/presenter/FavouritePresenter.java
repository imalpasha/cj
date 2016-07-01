package com.fly.cj.ui.presenter;

import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;

public class FavouritePresenter {
    private SharedPrefManager pref;

    public interface FavouriteView {
    }

    private FavouriteView view;

    private final Bus bus;

    public FavouritePresenter(FavouriteView view, Bus bus) {
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
