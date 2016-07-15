package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Chat.ChatFragment;
import com.fly.cj.ui.activity.Login.LoginFragment;
import com.fly.cj.ui.presenter.ChatPresenter;
import com.fly.cj.ui.presenter.LoginPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ChatFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class ChatModule {

    private final ChatPresenter.ChatView loginView;

    public ChatModule(ChatPresenter.ChatView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    ChatPresenter provideLoginPresenter(Bus bus) {
        return new ChatPresenter(loginView, bus);
    }
}
