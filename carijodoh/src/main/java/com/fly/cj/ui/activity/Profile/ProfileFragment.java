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

    @InjectView(R.id.scrollviewProfile)
    ScrollView scrollviewProfile;

    @InjectView(R.id.buttonprofileSubmit)
    Button buttonprofileSubmit;

    @InjectView(R.id.b)
    TextView b;

    @InjectView(R.id.d)
    TextView d;

    @InjectView(R.id.e)
    TextView e;

    @InjectView(R.id.f)
    TextView f;

    @InjectView(R.id.g)
    TextView g;

    @InjectView(R.id.h)
    TextView h;

    @InjectView(R.id.i)
    TextView i;

    @InjectView(R.id.j)
    TextView j;

    @Order(1) @NotEmpty
    @InjectView(R.id.txtprofileName)
    LinearLayout txtprofileName;

    @Order(2) @NotEmpty
    @InjectView(R.id.txtprofileDob)
    LinearLayout txtprofileDob;

    @InjectView(R.id.profileDob)
    TextView profileDob;

    @Order(3) @NotEmpty
    @InjectView(R.id.txtprofileMobile)
    LinearLayout txtprofileMobile;

    @Order(4) @NotEmpty
    @InjectView(R.id.txtprofileHeight)
    LinearLayout txtprofileHeight;

    @Order(5) @NotEmpty
    @InjectView(R.id.txtprofileWeight)
    LinearLayout txtprofileWeight;

    @Order(6) @NotEmpty
    @InjectView(R.id.txtprofileSmoke)
    LinearLayout txtprofileSmoke;

    @Order(7) @NotEmpty
    @InjectView(R.id.txtprofileState)
    LinearLayout txtprofileState;

    @Order(8) @NotEmpty
    @InjectView(R.id.txtprofileTown)
    LinearLayout txtprofileTown;

    @Order(9) @NotEmpty
    @InjectView(R.id.txtprofileEducation)
    LinearLayout txtprofileEducation;

    @Order(10) @NotEmpty
    @InjectView(R.id.txtprofileOccupation)
    LinearLayout txtprofileOccupation;

    @Order(11) @NotEmpty
    @InjectView(R.id.txtprofileMaritial)
    LinearLayout txtprofileMaritial;

    @Order(12) @NotEmpty
    @InjectView(R.id.txtprofileChild)
    LinearLayout txtprofileChild;

    @Order(13) @NotEmpty
    @InjectView(R.id.txtprofileRelation)
    LinearLayout txtprofileRelation;

    @Order(14) @NotEmpty
    @InjectView(R.id.txtprofilePolygamy)
    LinearLayout txtprofilePolygamy;

    @Order(15) @NotEmpty
    @InjectView(R.id.txtprofileFinancial)
    LinearLayout txtprofileFinancial;

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

        txtprofileDob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Dob");
                datePickerDialog.setYearRange(year - 80, year);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });


        txtprofileSmoke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Smoke");
                Log.e("Clicked", "Ok");
                popupSelection(smokerList, getActivity(), b, true, view);
            }
        });

        txtprofileState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "State");
                Log.e("Clicked", "Ok");
                popupSelection(stateList, getActivity(), d, true, view);
            }
        });

        txtprofileMaritial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Maritial");
                Log.e("Clicked", "Ok");
                popupSelection(maritialList, getActivity(), f, true, view);
            }
        });

        txtprofileChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Child");
                Log.e("Clicked", "Ok");
                popupSelection(childList, getActivity(), g, true, view);
            }
        });

        txtprofileRelation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Relationship");
                Log.e("Clicked", "Ok");
                popupSelection(relationList, getActivity(), h, true, view);
            }
        });

        txtprofilePolygamy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Polygamy");
                Log.e("Clicked", "Ok");
                popupSelection(polygamyList, getActivity(), i, true, view);
            }
        });

        txtprofileFinancial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnalyticsApplication.sendEvent("Edit", "Financial");
                Log.e("Clicked", "Ok");
                popupSelection(financialList, getActivity(), j, true, view);
            }
        });

        buttonprofileSubmit.setOnClickListener(new View.OnClickListener() {
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

    }
}