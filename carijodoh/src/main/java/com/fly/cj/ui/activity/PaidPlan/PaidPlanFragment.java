package com.fly.cj.ui.activity.PaidPlan;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Test.TestActivity;
import com.fly.cj.ui.module.PaidPlanModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.presenter.PaidPlanPresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.Validator;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.RealmResults;

public class PaidPlanFragment extends BaseFragment implements PaidPlanPresenter.PaidPlanView{

    @Inject
    PaidPlanPresenter presenter;

    @InjectView(R.id.pack_20)
    ImageButton pack_20;

    @InjectView(R.id.pack_30)
    ImageButton pack_30;

    @InjectView(R.id.pack_50)
    ImageButton pack_50;

    @InjectView(R.id.pack_70)
    ImageButton pack_70;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private Validator mValidator;

    public static PaidPlanFragment newInstance() {

        PaidPlanFragment fragment = new PaidPlanFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new PaidPlanModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.paid_plan, container, false);
        ButterKnife.inject(this, view);

        new SweetAlertDialog(getActivity(), SweetAlertDialog.NORMAL_TYPE)
                .setTitleText("Info")
                .setContentText("Anda perlu menjadi ahli premium")
                .showCancelButton(false)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.cancel();
                    }
                })
                .show();

        pack_20.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pack = new Intent(getActivity(), TestActivity.class);
                pack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pack);
            }
        });

        pack_30.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pack = new Intent(getActivity(), TestActivity.class);
                pack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pack);
            }
        });

        pack_50.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pack = new Intent(getActivity(), TestActivity.class);
                pack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pack);
            }
        });

        pack_70.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pack = new Intent(getActivity(), TestActivity.class);
                pack.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(pack);
            }
        });

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
