package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Profile.ProfileFragment;
import com.fly.cj.ui.presenter.ProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = ProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class ProfileModule {

    private final ProfilePresenter.ProfileView profileView;

    public ProfileModule(ProfilePresenter.ProfileView profileView) {
        this.profileView = profileView;
    }

    @Provides
    @Singleton
    ProfilePresenter provideProfilePresenter(Bus bus) {
        return new ProfilePresenter(profileView, bus);
    }
}
