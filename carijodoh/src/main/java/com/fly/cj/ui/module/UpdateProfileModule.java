package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.UpdateProfile.UpdateProfileFragment;
import com.fly.cj.ui.presenter.UpdateProfilePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = UpdateProfileFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class UpdateProfileModule {

    private final UpdateProfilePresenter.ProfileView profileView;

    public UpdateProfileModule(UpdateProfilePresenter.ProfileView profileView) {
        this.profileView = profileView;
    }

    @Provides
    @Singleton
    UpdateProfilePresenter provideProfilePresenter(Bus bus) {
        return new UpdateProfilePresenter(profileView, bus);
    }
}
