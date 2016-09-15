package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.ForgotPassword.ForgotPasswordFragment;
import com.fly.cj.ui.presenter.ForgotPasswordPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ForgotPasswordFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class ForgotPasswordModule {
    private final ForgotPasswordPresenter.ForgotPasswordView forgotPasswordView;

    public ForgotPasswordModule(ForgotPasswordPresenter.ForgotPasswordView forgotPasswordView) {
        this.forgotPasswordView = forgotPasswordView;
    }

    @Provides
    @Singleton
    ForgotPasswordPresenter provideForgotPasswordPresenter(Bus bus) {
        return new ForgotPasswordPresenter(forgotPasswordView, bus);
    }
}
