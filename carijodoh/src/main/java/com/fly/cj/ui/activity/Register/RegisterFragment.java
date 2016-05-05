package com.fly.cj.ui.activity.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Profile.ProfileActivity;
import com.fly.cj.ui.presenter.RegisterPresenter;
import com.fly.cj.utils.SharedPrefManager;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class RegisterFragment extends BaseFragment implements RegisterPresenter.RegisterView,Validator.ValidationListener {

    private Validator mValidator;
    private int fragmentContainerId;
    private SharedPrefManager pref;

    @Inject
    RegisterPresenter presenter;

    @InjectView(R.id.reg_registerBtn)
    Button reg_registerBtn;

    public static RegisterFragment newInstance() {

        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        reg_registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getActivity(), ProfileActivity.class);
                getActivity().startActivity(register);
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
    public void onValidationSucceeded() {

    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {

    }
}
