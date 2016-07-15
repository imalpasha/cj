package com.fly.cj.ui.presenter;

import com.fly.cj.api.obj.ChatPackageReceive.ChatFailed;
import com.fly.cj.api.obj.ChatPackageReceive.ChatReceive;
import com.fly.cj.ui.object.ChatRequest;
import com.fly.cj.ui.object.LoginRequest;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

public class ChatPresenter {

    public interface ChatView {

        void onMessageSuccess(String message);
        void onMessageFailed(String status);

    }

    private final ChatView view;
    private final Bus bus;

    public ChatPresenter(ChatView view, Bus bus) {
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
    public void loginFunction(ChatRequest data) {
        //otto function driven
        bus.post(new ChatRequest(data));
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessLogin(ChatReceive event) {

        /*Save Session And Redirect To Homepage*/
        view.onMessageSuccess(event.getMessage());
    }

    //receive data from ApiRequestHandler using otto
    @Subscribe
    public void onUserSuccessLogin(ChatFailed event) {

        /*Save Session And Redirect To Homepage*/
        view.onMessageFailed(event.getStatus());
    }
}
