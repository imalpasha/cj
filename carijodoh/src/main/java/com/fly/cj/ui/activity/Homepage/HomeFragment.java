package com.fly.cj.ui.activity.Homepage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Login.LoginActivity;
import com.fly.cj.ui.activity.Register.RegisterActivity;
import com.fly.cj.ui.module.HomeModule;
import com.fly.cj.ui.presenter.HomePresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

//import com.estimote.sdk.BeaconManager;

public class HomeFragment extends BaseFragment implements HomePresenter.HomeView{

    private Tracker mTracker;

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.homeLogin)
    Button homeLogin;

    @InjectView(R.id.homeRegister)
    Button homeRegister;

  /*  //@InjectView(R.id.test)
    //LinearLayout test;

    @InjectView(R.id.homeManageFlight)
    LinearLayout homeManageFlight;

    @InjectView(R.id.homeMobileBoardingPass)
    LinearLayout homeMobileBoardingPass;

    @InjectView(R.id.bannerImg)
    ImageView bannerImg;

    @InjectView(R.id.facebookLink)
    LinearLayout fbLink;

    @InjectView(R.id.twitterLink)
    LinearLayout twtLink;

    @InjectView(R.id.instagramLink)
    LinearLayout igLink;

    @InjectView(R.id.bookFlightBtn)
    TextView bookFlightBtn;
*/
    private String facebookUrl,twitterUrl,instagramUrl;
    private int fragmentContainerId;

    private SharedPrefManager pref;

    View view;

   /* private static final int NUMBER_OF_LOCATION_ITERATIONS = 10;
    private GoogleMap map; // Might be null if Google Play services APK is not available.
    private MyPlaces happyPlace;
    private MyPlaces home;
    private List<Geofence> myFences = new ArrayList<>();
    private GoogleApiClient googleApiClient;
    private PendingIntent geofencePendingIntent;
    //private UpdateLocationRunnable updateLocationRunnable;
    private LocationManager locationManager;
    private int marker = 0;
    private Location lastLocation;*/
    //FragmentManager fm;

    public static HomeFragment newInstance(Bundle bundle) {

        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new HomeModule(this)).inject(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        pref = new SharedPrefManager(getActivity());

        RealmObjectController.clearCachedResult(getActivity());

        homeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent (getActivity(), LoginActivity.class);
                getActivity().startActivity(login);
            }
        });

        homeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent (getActivity(), RegisterActivity.class);
                getActivity().startActivity(register);
            }
        });

        HashMap<String, String> initPromoBanner = pref.getGCMKey();
        String gcmKey = initPromoBanner.get(SharedPrefManager.GCMKey);
        Log.e("gcmKey","x"+gcmKey);


        /*GET PREF DATA
        HashMap<String, String> initPromoBanner = pref.getPromoBanner();
        String banner = initPromoBanner.get(SharedPrefManager.PROMO_BANNER);

        if(banner == null || banner == ""){
            HashMap<String, String> initDefaultBanner = pref.getDefaultBanner();
            banner = initDefaultBanner.get(SharedPrefManager.DEFAULT_BANNER);
        }

        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion <= Build.VERSION_CODES.JELLY_BEAN){
            // Do your code for froyo and above versions
            Log.e("NOTE1","TRUE");
        } else{
            Log.e("NOTE2","TRUE");
            // do your code for before froyo versions
        }

        //aq.id(R.id.bannerImg).image(banner);

        HashMap<String, String> initBannerModule = pref.getBannerModule();
        final String bannerModule = initBannerModule.get(SharedPrefManager.BANNER_MODULE);

        HashMap<String, String> initSocialMedia = pref.getSocialMedia();
        String socialMedia = initSocialMedia.get(SharedPrefManager.SOCIAL_MEDIA);
        */


        // [START shared_tracker]
        // Obtain the shared Tracker instance.
        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();
        // [END shared_tracker]

        /*mobileFaq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToBeacon();
            }
        });*/

        //homeBeacon.setOnClickListener(new View.OnClickListener() {
        //    @Override
        //    public void onClick(View v) {
        //        AnalyticsApplication.sendEvent("Click", "homeBeacon");
        //        goToBeacon();
        //    }
        //});


        //facebook link
       /* fbLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("fb://page/"+facebookUrl)));

                } catch(Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.facebook.com/"+facebookUrl)));
                }
            }
        });

        //twitter link
        twtLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("twitter://user?screen_name="+twitterUrl)));

                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://twitter.com/"+twitterUrl)));
                }
            }
        });

        //instagram link
        igLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse("http://instagram.com/_u/"+instagramUrl);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);

                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("https://www.instagram.com/"+instagramUrl)));
                }
            }
        });*/

        //setUpMap();
        //trySetAlarm();
        //LocalNotification.convert(getActivity());

        screenSize();
        return view;
    }


    public void screenSize(){

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.e(Integer.toString(width),Integer.toString(height));
    }

    public void getScreenSize(){

        int screenSize = getResources().getConfiguration().screenLayout &  Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch(screenSize) {
            case Configuration.SCREENLAYOUT_SIZE_LARGE:
                toastMsg = "Large screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_NORMAL:
                toastMsg = "Normal screen";
                break;
            case Configuration.SCREENLAYOUT_SIZE_SMALL:
                toastMsg = "Small screen";
                break;
            default:
                toastMsg = "Screen size is neither large, normal or small";
        }
            Log.e("toastMsg", toastMsg);
    }

    // ------------------------------------------------------------------------------------------- //

    /*Public-Inner Func
    public void goToLoginPage(){
       Intent loginPage = new Intent(getActivity(), LoginActivity.class);
       // Intent loginPage = new Intent(getActivity(), SensorActivity.class);
       //Intent loginPage = new Intent(getActivity(), Touch.class);
       // Intent loginPage = new Intent(getActivity(), RelativeFragment.class);
        getActivity().startActivity(loginPage);

    }*/

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        Log.e("ONRESUME", "TRUE");

    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        Log.e("ONPAUSE","TRUE");
    }

    @Override
    public void onStop() {
        super.onStop();
        //presenter.onStop();
        Log.e("ONSTOP","TRUE");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //presenter.onDestroy();
        Log.e("ONDESTROY","TRUE");
    }

    public void registerBackFunction() {
        new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("EXIT ONLINE DATING?")
                .setContentText("Confirm exit?")
                .showCancelButton(true)
                .setCancelText("Cancel")
                .setConfirmText("Close")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        getActivity().finish();
                        System.exit(0);

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
}