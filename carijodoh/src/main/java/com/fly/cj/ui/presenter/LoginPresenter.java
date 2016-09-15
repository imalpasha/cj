package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.ui.object.LoginRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class LoginPresenter {

    public interface LoginView {

        void onLoginSuccess(LoginReceive obj);
        void onLoginFailed(String dumm);
    }

    private final LoginView view;
    private final Bus bus;

    public LoginPresenter(LoginView view, Bus bus) {
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
    public void loginFunction(LoginRequest data) {
        //otto function driven
        bus.post(new LoginRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessLogin(LoginReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onLoginSuccess(event.getUserObj());
    }
}
