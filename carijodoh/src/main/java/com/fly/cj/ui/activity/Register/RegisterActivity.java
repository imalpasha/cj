package com.fly.cj.ui.activity.Register;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;

import butterknife.ButterKnife;

public class RegisterActivity extends MainFragmentActivity implements FragmentContainerActivity{

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, RegisterFragment.newInstance()).commit();

        setMenuButton();
        //hideTitle();
        setTitle("DAFTAR AKAUN");
        unlockDrawer();
        BaseFragment.removeLogoHeader(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}
