package com.fly.cj.ui.activity.UpdateProfile;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.google.android.gms.analytics.Tracker;

import butterknife.ButterKnife;

public class UpdateProfileActivity extends MainFragmentActivity implements FragmentContainerActivity {
     private Tracker mTracker;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, UpdateProfileFragment.newInstance()).commit();

        setMenuButton();
        setTitle("KEMASKINI PROFIL");

        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

    }
    @Override
    public void onResume() {
        super.onResume();
        // presenter.onResume();
    }

    @Override
    public int getFragmentContainerId() {
        return R.id.main_activity_fragment_container;
    }
}

