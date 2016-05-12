package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.PasswordExpired.PasswordExpiredFragment;
import com.fly.cj.ui.presenter.PasswordExpiredPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = PasswordExpiredFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class PasswordExpiredModule {
    private final PasswordExpiredPresenter.PasswordExpiredView PasswordExpiredview;

    public PasswordExpiredModule(PasswordExpiredPresenter.PasswordExpiredView PasswordExpiredview) {
        this.PasswordExpiredview = PasswordExpiredview;
    }

    @Provides
    @Singleton
    PasswordExpiredPresenter providePasswordExpiredPresenter(Bus bus) {
        return new PasswordExpiredPresenter(PasswordExpiredview, bus);
    }
}