package com.fly.cj.ui.activity.Register;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Login.LoginActivity;
import com.fly.cj.ui.activity.Profile.ProfileActivity;
import com.fly.cj.ui.presenter.RegisterPresenter;
import com.fly.cj.utils.SharedPrefManager;
import com.fly.cj.utils.Utils;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

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

    @NotEmpty(sequence = 1)
    @Order(1)
    @InjectView(R.id.txtRegisterUname)
    EditText txtRegisterUname;

    @NotEmpty(sequence = 1)
    @Order(2)
    @InjectView(R.id.txtRegisterEmail)
    EditText txtRegisterEmail;

    @NotEmpty(sequence = 1)
    @Order(3)
    @InjectView(R.id.txtRegisterPassword)
    EditText txtRegisterPassword;

    @NotEmpty(sequence = 1)
    @Order(4)
    @InjectView(R.id.txtRegisterCpassword)
    EditText txtRegisterCpassword;

    public static RegisterFragment newInstance() {

        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.register, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        reg_registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidator.validate();
                Utils.hideKeyboard(getActivity(), v);


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
        Intent register = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(register);
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        for (ValidationError error : errors) {
            View view = error.getView();
            setShake(view);

            /* Split Error Message. Display first sequence only */
            String message = error.getCollatedErrorMessage(getActivity());
            String splitErrorMsg[] = message.split("\\r?\\n");

            // Display error messages
            if (view instanceof EditText) {
                ((EditText) view).setError(splitErrorMsg[0]);
            } else {
                croutonAlert(getActivity(), splitErrorMsg[0]);
            }
        }

    }
}
