package com.fly.cj.ui.activity.ForgotPassword;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.module.ForgotPasswordModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.presenter.ForgotPasswordPresenter;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.gson.Gson;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Email;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;

public class ForgotPasswordFragment extends BaseFragment implements ForgotPasswordPresenter.ForgotPasswordView, Validator.ValidationListener {

    @Inject
    ForgotPasswordPresenter presenter;

    @InjectView(R.id.btncontinue)
    Button btncontinue;

    @NotEmpty(sequence = 1, message = "Sila isi e-mel")
    @Email(sequence = 2, message = "Invalid Email")
    @InjectView(R.id.forgot_email)
    EditText forgot_email;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private Validator mValidator;

    public static ForgotPasswordFragment newInstance() {

        ForgotPasswordFragment fragment = new ForgotPasswordFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new ForgotPasswordModule(this)).inject(this);

        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.forgot_password, container, false);
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

        forgot_email.setHint(builder);

        pref = new SharedPrefManager(getActivity());
        final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        btncontinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(forgot_email.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Email is required", Toast.LENGTH_LONG).show();
                }
                else if (!forgot_email.getText().toString().matches(emailPattern)) {
                    Toast.makeText(getActivity(), "Invalid Email", Toast.LENGTH_LONG).show();
                }
                /*else{
                    requestForgotPassword(editEmail.getText().toString(),"");
                    dialog.dismiss();
                }*/
            }
        });
        return view;
    }

    public void homepage()
    {
        Intent loginPage = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(loginPage);
        getActivity().finish();
    }

    /*@Override
        public void onRequestPasswordSuccess(ForgotPasswordReceive obj) {
            dismissLoading();
            Log.e("Message", obj.getMessage());

            Boolean status = Controller.getRequestStatus(obj.getStatus(), obj.getMessage(), getActivity());
            if (status) {
                setSuccessDialog(getActivity(), obj.getMessage(),null,"Success!");
            }
        }*/

    /* Validation Success - Start send data to server */
    @Override
    public void onValidationSucceeded() {
        Toast.makeText(getActivity(), "Yeay", Toast.LENGTH_SHORT).show();
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
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
    }
}