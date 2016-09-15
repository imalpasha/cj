package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.AboutUs.AboutUsFragment;
import com.fly.cj.ui.presenter.AboutUsPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = AboutUsFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class AboutUsModule {
    private final AboutUsPresenter.AboutUsView aboutUsView;

    public AboutUsModule(AboutUsPresenter.AboutUsView aboutUsView) {
        this.aboutUsView = aboutUsView;
    }

    @Provides
    @Singleton
    AboutUsPresenter provideAboutUsPresenter(Bus bus) {
        return new AboutUsPresenter(aboutUsView, bus);
    }
}
