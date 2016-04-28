package com.fly.cj.ui.activity.Profile;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.fly.cj.FireFlyApplication;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.module.ProfileModule;
import com.fly.cj.ui.presenter.ProfilePresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.android.gms.analytics.Tracker;
import com.mobsandgeeks.saripaar.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;


public class ProfileFragment extends BaseFragment implements ProfilePresenter.ProfileView {
    private Validator mValidator;
    private Tracker mTracker;

    @Inject
    ProfilePresenter presenter;

    private int fragmentContainerId;

    private SharedPrefManager pref;

    View view;

    public static ProfileFragment newInstance() {

        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new ProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        pref = new SharedPrefManager(getActivity());

        return view;
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
        //presenter.onStop();
        Log.e("ONSTOP", "TRUE");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //presenter.onDestroy();
        Log.e("ONDESTROY", "TRUE");
    }
}