package com.fly.cj.ui.activity.SplashScreen;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.presenter.HomePresenter;
import com.fly.cj.utils.App;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import javax.inject.Inject;

import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SplashScreenFragment extends BaseFragment {

    @Inject
    HomePresenter presenter;
    private int fragmentContainerId;
    private SharedPrefManager pref;
    private Boolean running = false;
    private static SweetAlertDialog pDialog;
    private boolean proceed = false;
    boolean goTimer = true;

    public static SplashScreenFragment newInstance(Bundle bundle) {

        SplashScreenFragment fragment = new SplashScreenFragment();
        fragment.setArguments(bundle);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.splash_screen, container, false);
        ButterKnife.inject(this, view);
        pref = new SharedPrefManager(getActivity());

        Bundle bundle = getArguments();

        String gcmKey = bundle.getString("GCM_KEY");
        if(gcmKey == null){
            gcmKey = "";
        }

        pref.setGCMKey(gcmKey);

        Intent intent = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(intent);
        getActivity().finish();


        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
