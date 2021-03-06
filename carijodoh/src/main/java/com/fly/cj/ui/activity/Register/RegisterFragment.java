package com.fly.cj.ui.activity.Register;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import io.realm.RealmResults;

public class RegisterFragment extends BaseFragment implements RegisterPresenter.RegisterView,Validator.ValidationListener {

    @Inject
    RegisterPresenter presenter;

    @InjectView(R.id.create_Bcreate)
    Button create_Bcreate;

    @NotEmpty(sequence = 1, message = "Sila isi e-mel")
    @Order(1)
    @Email(sequence = 2, message = "Invalid Email")
    @InjectView(R.id.create_ETemail)
    EditText create_ETemail;

    @NotEmpty(sequence = 1, message = "Sila isi kata laluan")
    @Order(2)
    @Length(sequence = 2, min = 8, max = 16, message = "Minimum 8 aksara")
    @InjectView(R.id.create_ETpass)
    EditText create_ETpass;

    @NotEmpty(sequence = 1, message = "Sila isi sah kata laluan")
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

        //Email hint
        String simple1 = "E-mel ";
        String colored1 = "*";
        SpannableStringBuilder builder1 = new SpannableStringBuilder();

        builder1.append(simple1);
        int start1 = builder1.length();
        builder1.append(colored1);
        int end1 = builder1.length();

        builder1.setSpan(new ForegroundColorSpan(Color.RED), start1, end1,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        create_ETemail.setHint(builder1);

        //Password hint
        String simple2 = "Kata Laluan ";
        String colored2 = "*";
        SpannableStringBuilder builder2 = new SpannableStringBuilder();

        builder2.append(simple2);
        int start2 = builder2.length();
        builder2.append(colored2);
        int end2 = builder2.length();

        builder2.setSpan(new ForegroundColorSpan(Color.RED), start2, end2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        create_ETpass.setHint(builder2);

        //Confirm Password hint
        String simple3 = "Sah Kata Laluan ";
        String colored3 = "*";
        SpannableStringBuilder builder3 = new SpannableStringBuilder();

        builder3.append(simple3);
        int start3 = builder3.length();
        builder3.append(colored3);
        int end3 = builder3.length();

        builder3.setSpan(new ForegroundColorSpan(Color.RED), start3, end3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        create_ETcpass.setHint(builder3);

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
            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successfully Register!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Intent loginPage = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(loginPage);
                            getActivity().finish();
                        }
                    })
                    .show();
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
