package com.fly.cj.ui.activity.PasswordExpired;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.R;
import com.fly.cj.api.obj.ChangePasswordReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.activity.Login.LoginActivity;
import com.fly.cj.ui.module.PasswordExpiredModule;
import com.fly.cj.ui.object.ChangePasswordRequest;
import com.fly.cj.ui.presenter.PasswordExpiredPresenter;
import com.fly.cj.utils.AESCBC;
import com.fly.cj.utils.App;
import com.fly.cj.utils.SharedPrefManager;
import com.google.android.gms.analytics.Tracker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.ConfirmPassword;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.mobsandgeeks.saripaar.annotation.Password;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

public class PasswordExpiredFragment extends BaseFragment implements PasswordExpiredPresenter.PasswordExpiredView,Validator.ValidationListener {
    private Validator mValidator;
    private Tracker mTracker;
    private AlertDialog dialog;
    private SharedPrefManager pref;
    private int fragmentContainerId;
    private View view;

    @Inject
    PasswordExpiredPresenter presenter;

    //@InjectView(R.id.search_edit_text) EditText searchEditText;
    @Order(1)@NotEmpty(sequence = 1)@Email(sequence = 2)
    @InjectView(R.id.txtExpUsername)
    EditText txtExpUsername;

    @Order(2)
    @NotEmpty (sequence = 1)
    @InjectView(R.id.txtExpLpass)
    EditText txtExpLpass;

    @Order(3)
    @NotEmpty (sequence = 1)
    @ConfirmPassword(message = "Password mismatch")
    @InjectView(R.id.txtExpCpass)
    EditText txtExpCpass;

    @Order(4)
    @NotEmpty(sequence = 1)
    @Length(sequence = 2, min = 6, max = 16 , message = "Must be at least 8 and maximum 16 characters")
    @Password(sequence = 3,scheme = Password.Scheme.ALPHA_NUMERIC_MIXED_CASE_SYMBOLS,message = "Password invalid , please refer to the password hint") // Password validator
    @InjectView(R.id.txtExpNpass)
    EditText txtExpNpass;


    @InjectView(R.id.expired_button)
    Button expired_button;

    public static PasswordExpiredFragment newInstance() {

        PasswordExpiredFragment fragment = new PasswordExpiredFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new PasswordExpiredModule(this)).inject(this);
        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.password_expired, container, false);
        ButterKnife.inject(this, view);

        pref = new SharedPrefManager(getActivity());

        //password
        txtExpLpass.setTransformationMethod(new PasswordTransformationMethod());
        txtExpCpass.setTransformationMethod(new PasswordTransformationMethod());
        txtExpNpass.setTransformationMethod(new PasswordTransformationMethod());

        HashMap<String, String> userinfo = pref.getUserEmail();
        String email = userinfo.get(SharedPrefManager.USER_EMAIL);

        txtExpUsername.setText(email, TextView.BufferType.EDITABLE);

        expired_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Action", "changepasswordButton");
                mValidator.validate();
            }
        });

        return view;
    }


    public void requestChangePassword(String username,String password ,String new_password){
        initiateLoading(getActivity());
        Log.e("Changepassword", "success");
        ChangePasswordRequest data = new ChangePasswordRequest();
        data.setEmail(username);
        data.setNewPassword(new_password);
        data.setCurrentPassword(password);
        presenter.changePassword(data);
    }


    public void goLoginPage()
    {

        Intent loginPage = new Intent(getActivity(), LoginActivity.class);
        getActivity().startActivity(loginPage);
        getActivity().finish();
    }


    @Override
    public void onUpdatePasswordSuccess(ChangePasswordReceive obj) {
        dismissLoading();
        Boolean status = Controller.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            setSuccessDialog(getActivity(), obj.getMessage(), HomeActivity.class,"Password Changed!");
        }

    }


    //Validator Result//
    @Override
    public void onValidationSucceeded() {

        //check if new password same with confirm password
        if(!isValidatePassword(txtExpCpass.getText().toString())){

            Crouton.makeText(getActivity(), "Password must contain uppercase char,number and symbols", Style.ALERT).show();
            EditText txtCurrentPassword = (EditText) view.findViewById(R.id.txtExpCpass);
            txtCurrentPassword.setError(null);

            //editTextPasswordNew.setError(true);
        }else{
            requestChangePassword(txtExpUsername.getText().toString(), AESCBC.encrypt(App.KEY, App.IV, txtExpLpass.getText().toString()), AESCBC.encrypt(App.KEY, App.IV, txtExpNpass.getText().toString()));
        }
    }


    private boolean isValidatePassword(String password) {

        String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=-])(?=\\S+$).{8,}$";

        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();

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
            }
        }

        //Crouton.makeText(getActivity(), message, Style.ALERT).show();

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

