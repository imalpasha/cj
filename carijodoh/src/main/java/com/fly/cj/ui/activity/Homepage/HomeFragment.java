package com.fly.cj.ui.activity.Homepage;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.R;
import com.fly.cj.api.obj.ActiveUserReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Login.LoginActivity;
import com.fly.cj.ui.activity.Register.RegisterActivity;
import com.fly.cj.ui.activity.ShowProfile.ShowProfileActivity;
import com.fly.cj.ui.module.HomeModule;
import com.fly.cj.ui.object.ActiveUserRequest;
import com.fly.cj.ui.presenter.HomePresenter;
import com.fly.cj.utils.App;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.android.gms.analytics.Tracker;

import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class HomeFragment extends BaseFragment implements HomePresenter.HomeView {

    private Tracker mTracker;

    @Inject
    HomePresenter presenter;

    @InjectView(R.id.layout_after_login)
    LinearLayout layout_after_login;

    @InjectView(R.id.layout_before_login)
    LinearLayout layout_before_login;

    @InjectView(R.id.homeLogin)
    Button homeLogin;

    @InjectView(R.id.homeRegister)
    Button homeRegister;

    @InjectView(R.id.gridview)
    GridView gridView;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private ActiveUserReceive objc;

    View view;

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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.home, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        pref = new SharedPrefManager(getActivity());

        RealmObjectController.clearCachedResult(getActivity());

        Log.e("url", App.IMAGE_URL);

        HashMap<String, String> initEmail = pref.getUserEmail();
        String e = initEmail.get(SharedPrefManager.USER_EMAIL);

        HashMap<String, String> initPass = pref.getUserPassword();
        String p = initPass.get(SharedPrefManager.PASSWORD);

        HashMap<String, String> initAuth = pref.getAuth();
        String a = initAuth.get(SharedPrefManager.USER_AUTH);

        ActiveUserRequest data = new ActiveUserRequest();
        data.setUsername(e);
        data.setPassword(p);
        data.setAuth_token(a);

        homeLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login = new Intent(getActivity(), LoginActivity.class);
                login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(login);
            }
        });

        homeRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getActivity(), RegisterActivity.class);
                register.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                getActivity().startActivity(register);
            }
        });

        HashMap<String, String> initLogin = pref.getLoginStatus();
        String login = initLogin.get(SharedPrefManager.ISLOGIN);

        Log.e("status", login);

        if (login.equals("Y")) {
            layout_before_login.setVisibility(View.GONE);
            layout_after_login.setVisibility(View.VISIBLE);
            presenter.viewList(data);
            Log.e("1","1");

        } else {
            layout_before_login.setVisibility(View.VISIBLE);
            layout_after_login.setVisibility(View.GONE);
            Log.e("2","2");
        }

        AnalyticsApplication application = (AnalyticsApplication) getActivity().getApplication();
        mTracker = application.getDefaultTracker();

        screenSize();
        return view;
    }

    public void screenSize() {

        WindowManager wm = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        Log.e(Integer.toString(width), Integer.toString(height));
    }

    public void getScreenSize() {

        int screenSize = getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK;

        String toastMsg;
        switch (screenSize) {
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
        Log.e("ONPAUSE", "TRUE");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("ONSTOP", "TRUE");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e("ONDESTROY", "TRUE");
    }

    public void registerBackFunction() {

       /* new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Adakah anda pasti mahu keluar?")
                .showCancelButton(true)
                .setCancelText("Batal")
                .setConfirmText("Keluar")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {*/
                        getActivity().finishAffinity();
                        System.exit(0);
                    /*}
                })
                .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();*/
    }

    @Override
    public void onViewActiveSuccess(ActiveUserReceive obj) {
        dismissLoading();

        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status) {
            setViewList(obj);
        }
    }

    public void setViewList(final ActiveUserReceive obj){
        Log.e("obj.getListUser()", Integer.toString(obj.getListUser().size()));
        gridView.setAdapter(new ImageAdapter(getActivity(), obj.getListUser()));

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id){
                String y = obj.getListUser().get(position).getId();
                Log.e("id click",y);
                pref.setProfileId(y);

                Intent intent = new Intent(getActivity(), ShowProfileActivity.class);
                getActivity().startActivity(intent);

            }
        });
    }
}