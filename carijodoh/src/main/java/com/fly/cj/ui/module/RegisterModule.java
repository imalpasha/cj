package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Register.RegisterFragment;
import com.fly.cj.ui.presenter.RegisterPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = RegisterFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class RegisterModule {

    private final RegisterPresenter.RegisterView registerView;

    public RegisterModule(RegisterPresenter.RegisterView registerView) {
        this.registerView = registerView;
    }

    @Provides
    @Singleton
    RegisterPresenter provideRegisterPresenter(Bus bus) {
        return new RegisterPresenter(registerView, bus);
    }
}