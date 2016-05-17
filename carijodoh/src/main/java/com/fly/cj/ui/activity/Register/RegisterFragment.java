package com.fly.cj.ui.activity.Register;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.api.obj.RegisterReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.activity.Login.LoginActivity;
import com.fly.cj.ui.module.RegisterModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.object.RegisterRequest;
import com.fly.cj.ui.presenter.RegisterPresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.fly.cj.utils.Utils;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import io.realm.RealmResults;

public class RegisterFragment extends BaseFragment implements RegisterPresenter.RegisterView,Validator.ValidationListener {

    @Inject
    RegisterPresenter presenter;

    @InjectView(R.id.create_Bcreate)
    Button create_Bcreate;

    @NotEmpty(sequence = 1)
    @Order(1)
    @Email(sequence = 2, message = "Invalid Email")
    @InjectView(R.id.create_ETemail)
    EditText create_ETemail;

    @NotEmpty(sequence = 1)
    @Order(2)
    @Length(sequence = 2, min = 8, max = 16, message = "Minimum 8 aksara")
    @InjectView(R.id.create_ETpass)
    EditText create_ETpass;

    @NotEmpty(sequence = 1)
    @Order(3)
    @InjectView(R.id.create_ETcpass)
    EditText create_ETcpass;


    private Validator mValidator;
    private int fragmentContainerId;
    private SharedPrefManager pref;
    private String storePassword, storeEmail, storeConfirmPassword;

    public static RegisterFragment newInstance() {

        RegisterFragment fragment = new RegisterFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new RegisterModule(this)).inject(this);

        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.register, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        //set edittext password input type
        create_ETpass.setTransformationMethod(new PasswordTransformationMethod());
        create_ETcpass.setTransformationMethod(new PasswordTransformationMethod());

        create_Bcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AnalyticsApplication.sendEvent("Click", "ContinueButton");
                mValidator.validate();
                Utils.hideKeyboard(getActivity(), v);
            }
        });

        return view;
    }

    public void registerFromFragment(String email, String password, String password_confirmation) {
        /*Start Loading*/
        initiateLoading(getActivity());

        //insert value into object
        RegisterRequest data = new RegisterRequest();
        data.setEmail(email);
        data.setPassword(password);
        data.setConfirmPassword(password_confirmation);

        //save username & password
        storeEmail = email;
        storePassword = password;
        storeConfirmPassword = password_confirmation;

        //start call api at presenter fail
        presenter.registerFunction(data);
    }

    public void homepage()
    {
        Intent home = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(home);
    }

    @Override
    public void onRegisterSuccess(RegisterReceive obj) {

        dismissLoading();

        /*pref.setUserEmail(storeEmail);
        pref.setUserPassword(storePassword);
        pref.setUserConfirmPassword(storeConfirmPassword);*/

        Boolean status = Controller.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {

            pref.setRegisterStatus("Y");
            Log.e(storeEmail, storePassword);
            Log.e("Request Status", obj.getStatus());

            Intent loginPage = new Intent(getActivity(), LoginActivity.class);
            getActivity().startActivity(loginPage);
            getActivity().finish();
        }
    }

    /*IF Login Failed*/
    @Override
    public void onRegisterFailed(String obj) {
        Crouton.makeText(getActivity(), obj, Style.ALERT).show();
        setAlertDialog(getActivity(), obj, "Register Error");
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onValidationSucceeded() {
        registerFromFragment(create_ETemail.getText().toString(), create_ETpass.getText().toString(), create_ETcpass.getText().toString());
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

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            RegisterReceive obj = gson.fromJson(result.get(0).getCachedResult(), RegisterReceive.class);
            onRegisterSuccess(obj);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}
