package com.fly.cj;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fly.cj.base.AQuery;
import com.fly.cj.base.BaseFragmentActivity;
import com.fly.cj.drawer.DrawerItem;
import com.fly.cj.drawer.NavigationDrawerFragment;
import com.fly.cj.ui.activity.AboutUs.AboutUsActivity;
import com.fly.cj.ui.activity.Favourite.FavouriteActivity;
import com.fly.cj.ui.activity.Gallery.GalleryActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.activity.Login.LoginActivity;
import com.fly.cj.ui.activity.PaidPlan.PaidPlanActivity;
import com.fly.cj.ui.activity.PasswordExpired.PasswordExpiredActivity;
import com.fly.cj.ui.activity.UpdateProfile.UpdateProfileActivity;
import com.fly.cj.ui.activity.Register.RegisterActivity;
import com.fly.cj.utils.SharedPrefManager;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainFragmentActivity extends BaseFragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {
    private NavigationDrawerFragment mNavigationDrawerFragment;
    private SharedPrefManager pref;
    private static MainFragmentActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aq = new AQuery(this);
        ButterKnife.inject(this);
        instance = this;

        /*Testing*/
        moveDrawerToTop();
        pref = new SharedPrefManager(this);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    private void initActionBar() {
    }

    private void moveDrawerToTop() {
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        DrawerLayout drawer = (DrawerLayout) inflater.inflate(R.layout.decor3, null); // "null" is important.

        // HACK: "steal" the first child of decor view
        ViewGroup decor = (ViewGroup) getWindow().getDecorView();
        View child = decor.getChildAt(0);
        decor.removeView(child);
        LinearLayout container = (LinearLayout) drawer.findViewById(R.id.main_activity_fragment_container); // This is the container we defined just now.
        container.addView(child, 0);
        drawer.findViewById(R.id.navigation_drawer).setPadding(0, getStatusBarHeight(), 0, 0);

        // Make the drawer replace the first child
        decor.addView(drawer);
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public static Activity getContext() {
        return instance;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (isTaskRoot())
        {
            // clean the file cache with advance option
            long triggerSize = 3000000; // starts cleaning when cache size is
            // larger than 3M
            long targetSize = 2000000; // remove the least recently used files
            // until cache size is less than 2M
            //AQUtility.cleanCacheAsync(this, triggerSize, targetSize);
        }
    }

    public void setMenuButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.menubutton).visible();
        aq.id(R.id.menubutton).clicked(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mNavigationDrawerFragment.openDrawer();
            }
        });
    }

    public void lockDrawer(){
        mNavigationDrawerFragment.lockDrawer();
    }

    public void unlockDrawer(){
        mNavigationDrawerFragment.unlockDrawer();
    }

    public void setTitle(String title)
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.title).text(title);
    }

    public void hideMenuButton()
    {
        View actionBarView = getActionBar().getCustomView();
        aq.recycle(actionBarView);
        aq.id(R.id.menubutton).gone();
        aq.id(R.id.menubutton).clicked(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mNavigationDrawerFragment.openDrawer();
            }
        });
    }

    @Override
    public void onNavigationDrawerItemSelected(int position, DrawerItem item)
    {
        // update the main content by replacing fragments
        try
        {
            if (item.getTag().equals("Home"))
            {
                item.setBackgroundColor(getResources().getColor(R.color.white));
                Intent homepage = new Intent(this, HomeActivity.class);
                homepage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(homepage);
            }
            else if (item.getTag().equals("Login"))
            {
                Intent login = new Intent(this, LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(login);
            }
            else if (item.getTag().equals("Register"))
            {
                Intent register = new Intent(this, RegisterActivity.class);
                register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(register);
            }
            else if (item.getTag().equals("About"))
            {
                Intent about = new Intent(this, AboutUsActivity.class);
                about.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(about);
            }
            else if (item.getTag().equals("Information_Update"))
            {
                Intent update = new Intent(this, UpdateProfileActivity.class);
                update.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(update);
            }
            else if (item.getTag().equals("Gallery"))
            {
                Intent gallery = new Intent(getContext(), GalleryActivity.class);
                gallery.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gallery);
            }
            else if (item.getTag().equals("Favourite"))
            {
                Intent favourite = new Intent(getContext(), FavouriteActivity.class);
                favourite.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(favourite);
            }
            else if (item.getTag().equals("Chat"))
            {
                Intent chat = new Intent(getContext(), PaidPlanActivity.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
            }
            else if (item.getTag().equals("Search"))
            {
                Intent chat = new Intent(getContext(), PaidPlanActivity.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
            }
            else if (item.getTag().equals("Setting"))
            {
                Intent chat = new Intent(getContext(), PaidPlanActivity.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
            }
            else if (item.getTag().equals("Logout"))
            {
                new SweetAlertDialog(getContext(), SweetAlertDialog.NORMAL_TYPE)
                        .setTitleText("Adakah anda pasti mahu log keluar?")
                        .showCancelButton(true)
                        .setCancelText("Batal")
                        .setConfirmText("Log Keluar")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                pref.setLoginStatus("N");
                                Controller.clearAll(getContext());
                                Intent logout = new Intent(getContext(), HomeActivity.class);
                                logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(logout);
                            }
                        })
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();
                            }
                        })
                        .show();
            }
            else if (item.getTag().equals("HEADER"))
            {

            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public void restoreActionBar()
    {
        ActionBar actionBar = getActionBar();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }
}
