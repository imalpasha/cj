package com.fly.cj.ui.activity.Homepage;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;

import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;

import butterknife.ButterKnife;

//import android.view.WindowManager;

public class HomeActivity extends MainFragmentActivity implements FragmentContainerActivity {

    //implements ToolbarActivity, ProgressIndicatorActivity, FragmentContainerActivity {
    //@InjectView(R.id.main_activity_toolbar) Toolbar toolbar;
    //@InjectView(R.id.main_activity_progress_indicator) ProgressBar progressIndicator;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.inject(this);

        Bundle bundle = getIntent().getExtras();

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_content, HomeFragment.newInstance(bundle),"Home").commit();


        setMenuButton();
        //hideTitle();
        setTitle("Welcome");
        unlockDrawer();
        BaseFragment.removeLogoHeader(this);
    }

   /* private void goToSearchFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.main_activity_fragment_container, SearchFragment.newInstance())
                .commit();
    }*/

    @Override
    public void onBackPressed(){

        final FragmentManager manager = getSupportFragmentManager();
        HomeFragment fragment = (HomeFragment) manager.findFragmentByTag("Home");
        fragment.registerBackFunction();
    }

    /*@Override
    public ProgressBar getProgressIndicator() {
        return progressIndicator;
    }

    @Override
    public Toolbar getToolbar() {
        return toolbar;
    }*/
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
