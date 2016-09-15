package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.ShowProfile.ShowProfileFragment;
import com.fly.cj.ui.activity.UpdateProfile.UpdateProfileFragment;
import com.fly.cj.ui.presenter.ShowProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ShowProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class ShowProfileModule {

    private final ShowProfilePresenter.ShowProfileView showProfileView;

    public ShowProfileModule(ShowProfilePresenter.ShowProfileView showProfileView) {
        this.showProfileView = showProfileView;
    }

    @Provides
    @Singleton
    ShowProfilePresenter provideShowProfilePresenter(Bus bus) {
        return new ShowProfilePresenter(showProfileView, bus);
    }
}