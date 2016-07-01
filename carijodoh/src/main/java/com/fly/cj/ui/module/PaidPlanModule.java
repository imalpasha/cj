package com.fly.cj.ui.module;

import com.fly.cj.AppModule;
import com.fly.cj.ui.activity.PaidPlan.PaidPlanFragment;
import com.fly.cj.ui.presenter.PaidPlanPresenter;
import com.squareup.otto.Bus;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module(
        injects = PaidPlanFragment.class,
        addsTo = AppModule.class,
        complete = false
)

public class PaidPlanModule {
    private final PaidPlanPresenter.PaidPlanView paidPlanView;

    public PaidPlanModule(PaidPlanPresenter.PaidPlanView paidPlanView) {
        this.paidPlanView = paidPlanView;
    }

    @Provides
    @Singleton
    PaidPlanPresenter providePaidPlanPresenter(Bus bus) {
        return new PaidPlanPresenter(paidPlanView, bus);
    }
}
