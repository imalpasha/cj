package com.fly.cj.ui.activity.ForgotPassword;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Login.LoginFragment;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;

public class ForgotPasswordActivity extends MainFragmentActivity implements FragmentContainerActivity {

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, ForgotPasswordFragment.newInstance()).commit();

        setMenuButton();
        setTitle("LUPA KATA LALUAN");
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
