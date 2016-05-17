package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.RegisterReceive;
import com.fly.cj.ui.object.RegisterRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class RegisterPresenter {

    public interface RegisterView {

        void onRegisterSuccess(RegisterReceive obj);
        void onRegisterFailed(String dumm);

    }

    private final RegisterView view;
    private final Bus bus;

    public RegisterPresenter(RegisterView view, Bus bus) {
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
    public void registerFunction(RegisterRequest data) {
        //otto function driven
        bus.post(new RegisterRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessRegister(RegisterReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onRegisterSuccess(event.getUserObj());
    }
}