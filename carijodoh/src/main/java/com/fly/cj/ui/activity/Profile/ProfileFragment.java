package com.fly.cj.ui.activity.Profile;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fly.cj.AnalyticsApplication;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.R;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.module.ProfileModule;
import com.fly.cj.ui.presenter.ProfilePresenter;
import com.fly.cj.utils.DropDownItem;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.android.gms.analytics.Tracker;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;


public class ProfileFragment extends BaseFragment implements Validator.ValidationListener, com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener,ProfilePresenter.ProfileView {

    @Inject
    ProfilePresenter presenter;

    @InjectView(R.id.profile_submitBtn)
    Button profile_submitBtn;

    @Order(1) @NotEmpty
    @InjectView(R.id.profile_email)
    TextView profile_email;

    @Order(2) @NotEmpty
    @InjectView(R.id.profile_password)
    TextView profile_password;

    @Order(3) @NotEmpty
    @InjectView(R.id.profile_cmpassword)
    TextView profile_cmpassword;

    @Order(4) @NotEmpty
    @InjectView(R.id.profile_name)
    TextView profile_name;

    @Order(5) @NotEmpty
    @InjectView(R.id.profile_gender)
    TextView profile_gender;

    @Order(6) @NotEmpty
    @InjectView(R.id.profile_dob)
    TextView profile_dob;

    @Order(7) @NotEmpty
    @InjectView(R.id.profile_mobile)
    TextView profile_mobile;

    @Order(8) @NotEmpty
    @InjectView(R.id.profile_height)
    TextView profile_height;

    @Order(9) @NotEmpty
    @InjectView(R.id.profile_weight)
    TextView profile_weight;

    @Order(10) @NotEmpty
    @InjectView(R.id.profile_smoker)
    TextView profile_smoker;

    @Order(11) @NotEmpty
    @InjectView(R.id.profile_country)
    TextView profile_country;

    @Order(12) @NotEmpty
    @InjectView(R.id.profile_state)
    TextView profile_state;

    @Order(13) @NotEmpty
    @InjectView(R.id.profile_town)
    TextView profile_town;

    @Order(14) @NotEmpty
    @InjectView(R.id.profile_education)
    TextView profile_education;

    @Order(15) @NotEmpty
    @InjectView(R.id.profile_occupation)
    TextView profile_occupation;

    @Order(16) @NotEmpty
    @InjectView(R.id.profile_maritial)
    TextView profile_maritial;

    @Order(17) @NotEmpty
    @InjectView(R.id.profile_children)
    TextView profile_children;

    @Order(18) @NotEmpty
    @InjectView(R.id.profile_relationship)
    TextView profile_relationship;

    @Order(19) @NotEmpty
    @InjectView(R.id.profile_polygamy)
    TextView profile_polygamy;

    @Order(20) @NotEmpty
    @InjectView(R.id.profile_financial)
    TextView profile_financial;

    @InjectView(R.id.profile_package)
    TextView profile_package;

    private Validator mValidator;
    private Tracker mTracker;
    private int fragmentContainerId;
    private SharedPrefManager pref;
    private Calendar calendar;
    private String fullDate;
    private int age;
    private Boolean limitAge;

    private String DATEPICKER_TAG = "DATEPICKER_TAG";
    private ArrayList<DropDownItem> smokerList;
    private ArrayList<DropDownItem> stateList;
    private ArrayList<DropDownItem> maritialList;
    private ArrayList<DropDownItem> childList;
    private ArrayList<DropDownItem> relationList;
    private ArrayList<DropDownItem> polygamyList;
    private ArrayList<DropDownItem> financialList;

    View view;

    public static ProfileFragment newInstance() {

        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new ProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());

        // Validator
        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
        mValidator.setValidationMode(Validator.Mode.BURST);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.profile, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        pref = new SharedPrefManager(getActivity());

        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        smokerList = getSmoker(getActivity());
        stateList = getState(getActivity());
        maritialList = getMaritial(getActivity());
        childList = getChild(getActivity());
        relationList = getRelation(getActivity());
        polygamyList = getPolygamy(getActivity());
        financialList = getFinancial(getActivity());

        profile_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Dob");
                datePickerDialog.setYearRange(year - 80, year);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });


        profile_smoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Smoke");
                Log.e("Clicked", "Ok");
                popupSelection(smokerList, getActivity(), profile_smoker, true, view);
            }
        });

        profile_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "State");
                Log.e("Clicked", "Ok");
                popupSelection(stateList, getActivity(), profile_state, true, view);
            }
        });

        profile_maritial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Maritial");
                Log.e("Clicked", "Ok");
                popupSelection(maritialList, getActivity(), profile_maritial, true, view);
            }
        });

        profile_children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Child");
                Log.e("Clicked", "Ok");
                popupSelection(childList, getActivity(), profile_children, true, view);
            }
        });

        profile_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Relationship");
                Log.e("Clicked", "Ok");
                popupSelection(relationList, getActivity(), profile_relationship, true, view);
            }
        });

        profile_polygamy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Polygamy");
                Log.e("Clicked", "Ok");
                popupSelection(polygamyList, getActivity(), profile_polygamy, true, view);
            }
        });

        profile_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Financial");
                Log.e("Clicked", "Ok");
                popupSelection(financialList, getActivity(), profile_financial, true, view);
            }
        });

        profile_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }


    // ------------------------------------------------------------------------------------------- //


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        fragmentContainerId = ((FragmentContainerActivity) getActivity()).getFragmentContainerId();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
        Log.e("ONRESUME", "TRUE");
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        Log.e("ONPAUSE", "TRUE");
    }

    @Override
    public void onStop() {
        super.onStop();
        //presenter.onStop();
        Log.e("ONSTOP", "TRUE");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //presenter.onDestroy();
        Log.e("ONDESTROY", "TRUE");
    }

    /*@Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        String monthInAlphabet = getMonthAlphabet(month);
        profileDob.setText(day + " " + monthInAlphabet + " " + year);

        //Reconstruct DOB
        String varMonth = "";
        String varDay = "";

        if(month < 10){
            varMonth = "0";
        }
        if(day < 10){
            varDay = "0";
        }

        fullDate = year + "-" + varMonth+""+(month+1)+"-"+varDay+""+day;
        Log.e("fullDate", fullDate);
        int currentYear = calendar.get(Calendar.YEAR);
        age = currentYear - year;
        if(age < 18){
            limitAge = false;
        }else{
            limitAge = true;
        }
    }*/

    @Override
    public void onValidationSucceeded() {

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
                croutonAlert(getActivity(), splitErrorMsg[0]);
            }
        }
    }

    @Override
    public void onDateSet(com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog, int year, int month, int day) {

        String monthInAlphabet = getMonthAlphabet(month);
        profile_dob.setText(day + " " + monthInAlphabet + " " + year);

        //Reconstruct DOB
        String varMonth = "";
        String varDay = "";

        if(month < 10){
            varMonth = "0";
        }
        if(day < 10){
            varDay = "0";
        }

        fullDate = year + "-" + varMonth+""+(month+1)+"-"+varDay+""+day;
        Log.e("fullDate", fullDate);
        int currentYear = calendar.get(Calendar.YEAR);
        age = currentYear - year;
        if(age < 18){
            limitAge = false;
        }else{
            limitAge = true;
        }

    }
}