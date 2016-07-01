package com.fly.cj.ui.presenter;

import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;

public class ForgotPasswordPresenter {

    private SharedPrefManager pref;

    public interface ForgotPasswordView {
    }

    private ForgotPasswordView view;

    private final Bus bus;

    public ForgotPasswordPresenter(ForgotPasswordView view, Bus bus) {
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
