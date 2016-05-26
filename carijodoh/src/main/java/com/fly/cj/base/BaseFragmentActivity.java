package com.fly.cj.base;

import android.app.ActionBar;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;

import com.fly.cj.R;
import com.fly.cj.utils.App;
import com.fly.cj.utils.RealmObjectController;

public class BaseFragmentActivity extends FragmentActivity {

    public com.fly.cj.base.AQuery aq;
    //TabView tabsView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        aq = new com.fly.cj.base.AQuery(this);


       if ((getApplicationContext().getResources().getConfiguration().screenLayout &
                android.content.res.Configuration.SCREENLAYOUT_SIZE_MASK) >= android.content.res.Configuration.SCREENLAYOUT_SIZE_LARGE){
           setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
           getApplicationContext().getResources().getConfiguration().orientation = 2;

        }else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            getApplicationContext().getResources().getConfiguration().orientation = 1;
        }

        try
        {
            ActionBar actionBar = getActionBar();
            actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
            actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            actionBar.setCustomView(R.layout.actionbar);
            View actionBarView = actionBar.getCustomView();
            aq.recycle(actionBarView);
            aq.id(R.id.title).typeface(Typeface.createFromAsset(getAssets(), App.FONT_HELVETICA_NEUE)).textSize(22).textColor(Color.WHITE);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void setTitle(CharSequence title)
    {
        super.setTitle(title);
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.title).text(title);
    }

    public void hideTitle()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.title).visibility(View.GONE);
    }

    public void setLogOutButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.btnLogOut).visible();
    }



    public void setARButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.btnAR).visible();
    }

    public void setBackButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.backbutton).visible();
        aq.id(R.id.backbutton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void setGlobalSearchButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.globalSearchBoxTablet).visible();
        aq.id(R.id.tabMySearch1).gone();
        aq.id(R.id.centerPart).gone();
    }

    public void setTabBackButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.tabBackButton).visible();
        aq.id(R.id.tabBackButton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void setTitle(int titleId)
    {
        super.setTitle(titleId);
    }

    public void setTitleImage(String imageUrl)
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.icon).image(imageUrl);
    }

    public void setTitleImage(int imageId)
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.icon).image(imageId);
    }

    @Override
    public void startActivity(Intent intent)
    {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        RealmObjectController.clearCachedResult(this);

    }

    @Override
    public void finish()
    {
        super.finish();
        //overridePendingTransition(R.anim.fadeout,R.anim.fadein);

    }

    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.fade_out);
        RealmObjectController.clearCachedResult(this);

        //setResult(RESULT_CANCELED);
    }

    @Override
    public void onPause()
    {
        super.onPause();
    }
}
