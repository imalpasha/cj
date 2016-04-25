package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Login.LoginFragment;
import com.fly.cj.ui.presenter.LoginPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = LoginFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class LoginModule {

    private final LoginPresenter.LoginView loginView;

    public LoginModule(LoginPresenter.LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    @Singleton
    LoginPresenter provideLoginPresenter(Bus bus) {
        return new LoginPresenter(loginView, bus);
    }
}
