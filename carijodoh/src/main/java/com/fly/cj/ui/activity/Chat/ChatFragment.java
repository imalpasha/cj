package com.fly.cj.ui.activity.Chat;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.activity.Profile.ProfileActivity;
import com.fly.cj.ui.activity.Register.RegisterActivity;
import com.fly.cj.ui.module.ChatModule;
import com.fly.cj.ui.module.LoginModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.object.ChatRequest;
import com.fly.cj.ui.object.LoginRequest;
import com.fly.cj.ui.presenter.ChatPresenter;
import com.fly.cj.ui.presenter.LoginPresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.fly.cj.utils.Utils;
import com.google.android.gms.analytics.Tracker;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import io.realm.RealmResults;

public class ChatFragment extends BaseFragment implements ChatPresenter.ChatView {

    @Inject
    ChatPresenter presenter;

    @InjectView(R.id.chatButton)
    Button chatButton;

    private SharedPrefManager pref;
    private int fragmentContainerId;

    public static ChatFragment newInstance() {

        ChatFragment fragment = new ChatFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new ChatModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dummy_chat, container, false);
        ButterKnife.inject(this, view);

        chatButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ChatRequest obj = new ChatRequest();
                presenter.loginFunction(obj);
                //Intent register = new Intent(getActivity(), RegisterActivity.class);
                //getActivity().startActivity(register);
            }
        });

        return view;
    }

    @Override
    public void onMessageSuccess(String message) {

        /*Dismiss Loading*/
        dismissLoading();
        Log.e("Message Send","Y");
    }

    /*IF Login Failed*/
    @Override
    public void onMessageFailed(String obj) {

        /*Dismiss Loading*/
        dismissLoading();
        Log.e("Message Send","N");
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
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
