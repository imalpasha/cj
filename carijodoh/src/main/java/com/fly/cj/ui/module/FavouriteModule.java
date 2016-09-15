package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Favourite.FavouriteFragment;
import com.fly.cj.ui.presenter.FavouritePresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = FavouriteFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class FavouriteModule {
    private final FavouritePresenter.FavouriteView favouriteView;

    public FavouriteModule(FavouritePresenter.FavouriteView favouriteView) {
        this.favouriteView = favouriteView;
    }

    @Provides
    @Singleton
    FavouritePresenter provideFavouritePresenter(Bus bus) {
        return new FavouritePresenter(favouriteView, bus);
    }
}

