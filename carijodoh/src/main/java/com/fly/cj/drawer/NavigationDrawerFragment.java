/* Will change accordingly */
package com.fly.cj.drawer;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Fragment;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fly.cj.R;
import com.fly.cj.drawer.NavigationDrawerAdapter.DrawerViewType;
import com.fly.cj.utils.LazyList.ImageLoader;
import com.fly.cj.utils.SharedPrefManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Fragment used for managing interactions for and presentation of a navigation
 * drawer. See the <a href=
 * "https://developer.android.com/design/patterns/navigation-drawer.html#Interaction"
 * > design guidelines</a> for a complete explanation of the behaviors
 * implemented here.
 */
public class NavigationDrawerFragment extends Fragment {

    /**
     * Remember the position of the selected item.
     */
    private static final String STATE_SELECTED_POSITION = "selected_navigation_drawer_position";

    /**
     * Per the design guidelines, you should show the drawer on launch until the
     * user manually expands it. This shared preference tracks this.
     */
    private static final String PREF_USER_LEARNED_DRAWER = "navigation_drawer_learned";

    /**
     * A pointer to the current callbacks instance (the Activity).
     */
    private NavigationDrawerCallbacks mCallbacks;

    /**
     * Helper component that ties the action bar to the navigation drawer.
     */
    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerListView;
    private View mFragmentContainerView;
    //private static SharedPrefManager pref;
    //private static SessionManager session;
    private int mCurrentSelectedPosition = 0;
    private boolean mFromSavedInstanceState;
    private boolean mUserLearnedDrawer;
    ArrayList<DrawerItem> itemList = new ArrayList<DrawerItem>();

    private NavigationDrawerAdapter drawerAdapter;
    static String toBeDisplayD1;

    //SharedPrefManager prefer;
    public ImageLoader imageLoader;
    private SharedPrefManager pref;
    private String loginStatus;

    public NavigationDrawerFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Read in the flag indicating whether or not the user has demonstrated
        // awareness of the
        // drawer. See PREF_USER_LEARNED_DRAWER for details.
       SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
       // pref = new SharedPrefManager(getActivity());
       // session = new SessionManager(getActivity());

        mUserLearnedDrawer = sp.getBoolean(PREF_USER_LEARNED_DRAWER, false);

        if (savedInstanceState != null) {
            mCurrentSelectedPosition = savedInstanceState.getInt(STATE_SELECTED_POSITION);
            mFromSavedInstanceState = true;
        }

        // Select either the default item (0) or the last selected item.
        selectItem(mCurrentSelectedPosition);

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // Indicate that this fragment would like to influence the set of
        // actions in the action bar.
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        mDrawerListView = (ListView) inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        mDrawerListView.setVerticalScrollBarEnabled(false);
        mDrawerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position, (DrawerItem) parent.getItemAtPosition(position));
            }
        });

        pref = new SharedPrefManager(getActivity());

        try{
            HashMap<String, String> init = pref.getLoginStatus();
            loginStatus = init.get(SharedPrefManager.ISLOGIN);
            Log.e("loginStatus",loginStatus);
        }
        catch (Exception e){
            Log.e("loginStatus",e.getMessage());

        }

		/* Initiate NavigationDrawer Item */
        menuTop();

        drawerAdapter = new NavigationDrawerAdapter(getActivity(), this);
        drawerAdapter.setItems(itemList);
        mDrawerListView.setAdapter(drawerAdapter);
        mDrawerListView.setItemChecked(mCurrentSelectedPosition, true);

        return mDrawerListView;
    }

    public void menuTop() {
        itemList.clear();
        itemList = new ArrayList<DrawerItem>();

        if(loginStatus != null){
            if(loginStatus.equals("Y")) {

                DrawerItem vrsm = new DrawerItem();
                vrsm.setId(0);
                //vrsm.setTitle("Hi, " + userName);
                vrsm.setTitle("CARI JODOH");
                vrsm.setTag("HEADER");
                vrsm.setLayoutId(DrawerViewType.HEADER_CLOSEBTN);
                vrsm.setBackgroundColor(getResources().getColor(R.color.actionBarColour_default_theme));
                itemList.add(vrsm);

                // Set Hi
                DrawerItem pic = new DrawerItem();
                pic.setId(1);
                pic.setTag("USER");
                pic.setLayoutId(DrawerViewType.DRAWER_PROFILE);
                itemList.add(pic);

                DrawerItem home = new DrawerItem();
                home.setId(2);
                home.setTag("Home");
                home.setTitle("Laman Utama");
                home.setLayoutId(DrawerViewType.STATIC_MENU);
                home.setIconId(R.drawable.menu_home_s);
                itemList.add(home);


                DrawerItem profile = new DrawerItem();
                profile.setId(3);
                profile.setTag("Information_Update");
                profile.setTitle("Kemaskini Profil");
                profile.setLayoutId(DrawerViewType.STATIC_MENU);
                profile.setIconId(R.drawable.menu_profile_s);
                itemList.add(profile);

                DrawerItem gallery = new DrawerItem();
                gallery.setId(4);
                gallery.setTag("Gallery");
                gallery.setTitle("Galeri Foto");
                gallery.setLayoutId(DrawerViewType.STATIC_MENU);
                gallery.setIconId(R.drawable.menu_gallery_s);
                itemList.add(gallery);

                DrawerItem favourite = new DrawerItem();
                favourite.setId(5);
                favourite.setTag("Favourite");
                favourite.setTitle("Kegemaran");
                favourite.setLayoutId(DrawerViewType.STATIC_MENU);
                favourite.setIconId(R.drawable.menu_kegemaran_s);
                itemList.add(favourite);

                DrawerItem chat = new DrawerItem();
                chat.setId(6);
                chat.setTag("Chat");
                chat.setTitle("Peti Masuk");
                chat.setLayoutId(DrawerViewType.STATIC_MENU);
                chat.setIconId(R.drawable.menu_sembang_s);
                itemList.add(chat);

                DrawerItem search = new DrawerItem();
                search.setId(7);
                search.setTag("Search");
                search.setTitle("Carian");
                search.setLayoutId(DrawerViewType.STATIC_MENU);
                search.setIconId(R.drawable.menu_carian_s);
                itemList.add(search);

                DrawerItem setting = new DrawerItem();
                setting.setId(8);
                setting.setTag("Setting");
                setting.setTitle("Tetapan Carian");
                setting.setLayoutId(DrawerViewType.STATIC_MENU);
                setting.setIconId(R.drawable.menu_tetapan_carian_s);
                itemList.add(setting);

                DrawerItem about = new DrawerItem();
                about.setId(9);
                about.setTag("About");
                about.setTitle("Tentang Kami");
                about.setLayoutId(DrawerViewType.STATIC_MENU);
                about.setIconId(R.drawable.menu_about_s);
                itemList.add(about);

                DrawerItem sbb = new DrawerItem();
                sbb.setId(11);
                sbb.setTag("Logout");
                sbb.setTitle("Log Keluar");
                sbb.setLayoutId(DrawerViewType.STATIC_MENU);
                sbb.setIconId(R.drawable.menu_log_keluar_s);
                itemList.add(sbb);

            } else{
                DrawerItem vrsm = new DrawerItem();
                vrsm.setId(0);
                //vrsm.setTitle("Hi, " + userName);
                vrsm.setTitle("CARI JODOH");
                vrsm.setTag("HEADER");
                vrsm.setLayoutId(DrawerViewType.HEADER_CLOSEBTN);
                vrsm.setBackgroundColor(getResources().getColor(R.color.actionBarColour_default_theme));
                itemList.add(vrsm);

                DrawerItem home = new DrawerItem();
                home.setId(1);
                home.setTag("Home");
                home.setTitle("Laman Utama");
                home.setLayoutId(DrawerViewType.STATIC_MENU);
                home.setIconId(R.drawable.menu_home_s);
                itemList.add(home);

                DrawerItem sbb = new DrawerItem();
                sbb.setId(2);
                sbb.setTag("Login");
                sbb.setTitle("Log Masuk");
                sbb.setLayoutId(DrawerViewType.STATIC_MENU);
                sbb.setIconId(R.drawable.menu_log_masuk_s);
                itemList.add(sbb);

                DrawerItem register = new DrawerItem();
                register.setId(3);
                register.setTag("Register");
                register.setTitle("Daftar Baru");
                register.setLayoutId(DrawerViewType.STATIC_MENU);
                register.setIconId(R.drawable.menu_daftar_s);
                itemList.add(register);

                DrawerItem about = new DrawerItem();
                about.setId(4);
                about.setTag("About");
                about.setTitle("Tentang Kami");
                about.setLayoutId(DrawerViewType.STATIC_MENU);
                about.setIconId(R.drawable.menu_about_s);
                itemList.add(about);
            }
        }
    }

    public boolean isDrawerOpen() {
        return mDrawerLayout != null && mDrawerLayout.isDrawerOpen(mFragmentContainerView);
    }

    /**
     * Users of this fragment must call this method to set up the navigation
     * drawer interactions.
     *
     * @param fragmentId   The android:id of this fragment in its activity's layout.
     * @param drawerLayout The DrawerLayout containing this fragment's UI.
     */
    public void setUp(int fragmentId, DrawerLayout drawerLayout) {
        mFragmentContainerView = getActivity().findViewById(fragmentId);
        mDrawerLayout = drawerLayout;

        // set a custom shadow that overlays the main content when the drawer
        // opens
        mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
        // set up the drawer's list view with items and click listener

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the navigation drawer and the action bar app icon.
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), /* host Activity */
                mDrawerLayout, /* DrawerLayout object */
                R.drawable.ic_drawer, /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open, /*
                                         * "open drawer" description for
										 * accessibility
										 */
                R.string.navigation_drawer_close /*
                                         * "close drawer" description for
										 * accessibility
										 */
        ) {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (!isAdded()) {
                    return;
                }

                getActivity().invalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if (!isAdded()) {
                    return;
                }

                if (!mUserLearnedDrawer) {
                    // The user manually opened the drawer; store this
                    // flag to
                    // prevent auto-showing
                    // the navigation drawer automatically in the
                    // future.
                    mUserLearnedDrawer = true;
                    SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getActivity());
                    sp.edit().putBoolean(PREF_USER_LEARNED_DRAWER, true).apply();
                }

                getActivity().invalidateOptionsMenu(); // calls
                // onPrepareOptionsMenu()
            }
        };

        // If the user hasn't 'learned' about the drawer, open it to introduce
        // them to the drawer,
        // per the navigation drawer design guidelines.
        if (!mUserLearnedDrawer && !mFromSavedInstanceState) {
            // mDrawerLayout.openDrawer(mFragmentContainerView);
        }

        // Defer code dependent on restoration of previous instance state.
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });

        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void selectItem(int position, DrawerItem itemAtPosition) {
        selectItem(position);
        if (mCallbacks != null) {
            mCallbacks.onNavigationDrawerItemSelected(position, itemAtPosition);
        }
    }

    private void selectItem(int position) {
        Log.e("Clicked", "True");
        mCurrentSelectedPosition = position;
        // if (mDrawerListView != null) {
        // mDrawerListView.setItemChecked(position, true);
        // }
        if (mDrawerLayout != null) {
            int layoutId = itemList.get(position).getLayoutId();
            if (layoutId == DrawerViewType.HEADER || layoutId == DrawerViewType.HEADER_CLOSEBTN) {
                return;
            }
            closeDrawer();
        }
    }

    public void closeDrawer() {
        Log.e("Drawer Close", "<--------");
        mDrawerLayout.closeDrawer(mFragmentContainerView);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mCallbacks = (NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(STATE_SELECTED_POSITION, mCurrentSelectedPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Forward the new configuration the drawer toggle component.
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // If the drawer is open, show the global app actions in the action bar.
        // See also
        // showGlobalContextActionBar, which controls the top-left area of the
        // action bar.
        if (mDrawerLayout != null && isDrawerOpen()) {

            showGlobalContextActionBar();
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * Per the navigation drawer design guidelines, updates the action bar to
     * show the global app 'context', rather than just what's in the current
     * screen.
     */
    private void showGlobalContextActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        //actionBar.setElevation(0f);
        //actionBar.setCustomView(R.layout.actionbar)
        // actionBar.setDisplayShowTitleEnabled(true);
        // actionBar.setTitle(R.string.app_name);

    }

    private ActionBar getActionBar() {
        return getActivity().getActionBar();
    }

    /**
     * Callbacks interface that all activities using this fragment must
     * implement.
     */
    public static interface NavigationDrawerCallbacks {
        /**
         * Called when an item in the navigation drawer is selected.
         */
        // void onNavigationDrawerItemSelected(int position);

        void onNavigationDrawerItemSelected(int position, DrawerItem itemAtPosition);
    }

    public void openDrawer() {
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(mFragmentContainerView);
        } else {
            mDrawerLayout.openDrawer(mFragmentContainerView);
        }

    }

    public void lockDrawer() {
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
    }

    public void unlockDrawer(){
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED);
    }

    public void removeDrawer(){
    }

}
