package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Homepage.HomeFragment;
import com.fly.cj.ui.presenter.HomePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = HomeFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class HomeModule {

    private final HomePresenter.HomeView homeView;

    public HomeModule(HomePresenter.HomeView homeView) {
        this.homeView = homeView;
    }

    @Provides
    @Singleton
    HomePresenter provideHomePresenter(Bus bus) {
        return new HomePresenter(homeView, bus);
    }
}
