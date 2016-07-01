package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.Gallery.GalleryFragment;
import com.fly.cj.ui.presenter.GalleryPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = GalleryFragment.class,
        addsTo = AppModule.class,
        complete = false
)
public class GalleryModule {
    private final GalleryPresenter.GalleryView galleryView;

    public GalleryModule(GalleryPresenter.GalleryView galleryView) {
        this.galleryView = galleryView;
    }

    @Provides
    @Singleton
    GalleryPresenter provideGalleryPresenter(Bus bus) {
        return new GalleryPresenter(galleryView, bus);
    }
}

