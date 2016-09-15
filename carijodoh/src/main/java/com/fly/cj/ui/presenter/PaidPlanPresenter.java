package com.fly.cj.ui.presenter;

import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;

public class PaidPlanPresenter {
    private SharedPrefManager pref;

    public interface PaidPlanView {
    }

    private PaidPlanView view;

    private final Bus bus;

    public PaidPlanPresenter(PaidPlanView view, Bus bus) {
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
