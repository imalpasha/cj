package com.fly.cj.ui.activity.AboutUs;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.module.AboutUsModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.presenter.AboutUsPresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import io.realm.RealmResults;

public class AboutUsFragment extends BaseFragment implements AboutUsPresenter.AboutUsView{

    @Inject
    AboutUsPresenter presenter;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private Validator mValidator;

    public static AboutUsFragment newInstance() {

        AboutUsFragment fragment = new AboutUsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new AboutUsModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.about_us, container, false);
        ButterKnife.inject(this, view);

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
        presenter.onResume();

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if(result.size() > 0){
            Gson gson = new Gson();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}