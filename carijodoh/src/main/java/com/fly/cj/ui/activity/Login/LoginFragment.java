package com.fly.cj.ui.activity.Login;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.PasswordTransformationMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.ForgotPassword.ForgotPasswordActivity;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.activity.UpdateProfile.UpdateProfileActivity;
import com.fly.cj.ui.activity.Register.RegisterActivity;
import com.fly.cj.ui.module.LoginModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.object.LoginRequest;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;

import io.realm.RealmResults;

public class LoginFragment extends BaseFragment implements LoginPresenter.LoginView,Validator.ValidationListener {

    @Inject
    LoginPresenter presenter;

    @InjectView(R.id.registerBtn)
    Button registerBtn;

    @InjectView(R.id.btnLogin)
    Button btnLogin;

    @InjectView(R.id.txtForgotPassword)
    TextView txtForgotPassword;

    @NotEmpty(sequence = 1, message = "Sila isi e-mel")
    @Order(1)
    @InjectView(R.id.txtLoginEmail) EditText txtLoginEmail;


    @NotEmpty(sequence = 1, message = "Sila isi kata laluan")
    //@Length(sequence = 2, min = 6, max = 16 , message = "Must be at least 8 and maximum 16 characters")
    @Order(2)
    @InjectView(R.id.txtLoginPassword) EditText txtLoginPassword;

    private AlertDialog dialog;
    private SharedPrefManager pref;
    private String storePassword, storeUsername, storeAuth_token, storeSignature, storeId;
    private int fragmentContainerId;

    private Validator mValidator;
    private Tracker mTracker;

    public static LoginFragment newInstance() {

        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new LoginModule(this)).inject(this);

        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);
        ButterKnife.inject(this, view);

        //Email hint
        String simple = "E-mel ";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtLoginEmail.setHint(builder);

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

        txtLoginPassword.setHint(builder2);

        pref = new SharedPrefManager(getActivity());

        //set edittext password input type
        txtLoginPassword.setTransformationMethod(new PasswordTransformationMethod());

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AnalyticsApplication.sendEvent("Click", "btnLogin");
                mValidator.validate();
                Utils.hideKeyboard(getActivity(), v);
            }
        });

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getActivity(), RegisterActivity.class);
                getActivity().startActivity(register);
            }
        });

        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //AnalyticsApplication.sendEvent("Click", "forget password");
                forgotPassword();
            }
        });

        return view;
    }

    public void forgotPassword()
    {
        Intent forgotPage = new Intent(getActivity(), ForgotPasswordActivity.class);
        getActivity().startActivity(forgotPage);
        getActivity().finish();
    }

    public void loginFromFragment(String username,String password){
        /*Start Loading*/
        initiateLoading(getActivity());

        String deviceId = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        //insert value into object
        LoginRequest data = new LoginRequest();
        data.setUsername(username);
        data.setPassword(password);
        data.setDeviceId(deviceId);

        //save username & password
        storeUsername = username;
        storePassword = password;

        //start call api at presenter fail
        presenter.loginFunction(data);
    }

    public void homepage()
    {
        Intent loginPage = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(loginPage);
        loginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().finish();
    }

    public void profile()
    {
        Intent profilePage = new Intent(getActivity(), UpdateProfileActivity.class);
        getActivity().startActivity(profilePage);
        profilePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        getActivity().finish();
    }

    @Override
    public void onLoginSuccess(LoginReceive obj) {

        /*Dismiss Loading*/
        dismissLoading();

        pref.setUserEmail(storeUsername);
        pref.setUserPassword(storePassword);
        pref.setUserName(obj.getUser().getUserName());


        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status) {

            pref.setLoginStatus("Y");

            storeAuth_token = obj.getAuth_token();
            storeSignature = obj.getSignature();
            storeId = obj.getUser().getUserId();
            Log.e("uniqid", storeId);


            pref.setUserAuth(storeAuth_token);

            pref.setSignatureToLocalStorage(storeSignature);
            pref.setUniqId(storeId);
            pref.setUserName(obj.getUser().getUserName());


            String dob = obj.getUser().getUserDob();

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            try {
                Date date = dateFormat.parse(dob);
                System.out.println(date);
                int d = getDay(date);
                System.out.println(d);

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);

                int real = year - d;
                String age = String.valueOf(real);
                pref.setUserAge(age + " tahun");
                Log.e("Age", age);

            } catch (ParseException e) {
                e.printStackTrace();
            }

            //-------------------------CALL FROM PREF----------------------------------//
            HashMap<String, String> initAuth = pref.getAuth();
            String token = initAuth.get(SharedPrefManager.USER_AUTH);

            HashMap<String, String> initSign = pref.getSignatureFromLocalStorage();
            String sign = initSign.get(SharedPrefManager.SIGNATURE);
            //Toast.makeText(getActivity(), sign, Toast.LENGTH_LONG).show();

            Log.e(storeUsername,storePassword);
            Log.e("Login Status",obj.getStatus());
            Log.e("Signature ", sign);
            Log.e("Token ", token);

            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                .setTitleText("Successfully Login!")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        homepage();
                    }
                })
                .show();
        }
    }

    /*IF Login Failed*/
    @Override
    public void onLoginFailed(String obj) {
        Crouton.makeText(getActivity(), obj, Style.ALERT).show();
        setAlertDialog(getActivity(),obj,"Login Error");

    }
/*
    @Override
    public void onRequestPasswordSuccess(ForgotPasswordReceive obj) {
        dismissLoading();
        Log.e("Message", obj.getMessage());

        Boolean status = Controller.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
        if (status) {
            setSuccessDialog(getActivity(), obj.getMessage(),null,"Success!");
        }

    }

*/
    /* Validation Success - Start send data to server */
    @Override
    public void onValidationSucceeded() {

        loginFromFragment(txtLoginEmail.getText().toString(),txtLoginPassword.getText().toString());
    }

    /* Validation Failed - Toast Error */
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

    //Popup Forgot Password
    /*public void forgotPassword(){

        LayoutInflater li = LayoutInflater.from(getActivity());
        final View myView = li.inflate(R.layout.forgot_password, null);
        Button cont = (Button)myView.findViewById(R.id.btncontinue);

        final EditText editEmail = (EditText)myView.findViewById(R.id.forgot_email);
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";


        cont.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(editEmail.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_LONG).show();

                }
                else if (!editEmail.getText().toString().matches(emailPattern)) {
                   Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();
                }
                else{
                    requestForgotPassword(editEmail.getText().toString(),"");
                    dialog.dismiss();
                }

            }

        });

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(myView);

        dialog = builder.create();

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        //lp.height = 570;
        dialog.getWindow().setAttributes(lp);
        dialog.show();
    }*/

   /* public void requestForgotPassword(String username,String signature){
        initiateLoading(getActivity());
        PasswordRequest data = new PasswordRequest();
        data.setEmail(username);
        data.setSignature(signature);
        presenter.forgotPassword(data);
    }*/

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
            LoginReceive obj = gson.fromJson(result.get(0).getCachedResult(), LoginReceive.class);
            onLoginSuccess(obj);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
}
