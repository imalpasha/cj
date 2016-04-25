package com.fly.cj.ui.activity.SplashScreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class SplashScreenActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);

        //hideMenuButton();
        //hideTitle();
        //lockDrawer();
        BaseFragment.removeLogoHeader(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.splash_content, SplashScreenFragment.newInstance()).commit();

    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
