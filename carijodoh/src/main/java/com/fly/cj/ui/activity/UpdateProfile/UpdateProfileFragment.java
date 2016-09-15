package com.fly.cj.ui.activity.UpdateProfile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.PasswordTransformationMethod;
import android.text.method.ScrollingMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.api.obj.ImageViewReceive;
import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.api.obj.UpdateReceive;
import com.fly.cj.api.obj.UploadImageReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.module.UpdateProfileModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.object.ImageViewRequest;
import com.fly.cj.ui.object.ProfileViewRequest;
import com.fly.cj.ui.object.UpdateRequest;
import com.fly.cj.ui.object.UploadImageRequest;
import com.fly.cj.ui.presenter.UpdateProfilePresenter;
import com.fly.cj.utils.App;
import com.fly.cj.utils.DropDownItem;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;

import com.google.android.gms.analytics.Tracker;

import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;
import com.mobsandgeeks.saripaar.annotation.Order;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.realm.RealmResults;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class UpdateProfileFragment extends BaseFragment implements Validator.ValidationListener, com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener,UpdateProfilePresenter.ProfileView {

    @Inject
    UpdateProfilePresenter presenter;

    @InjectView(R.id.profile_submitBtn)
    Button profile_submitBtn;

    @Order(2)
    @InjectView(R.id.profile_password)
    EditText profile_password;

    @Order(3)
    @InjectView(R.id.profile_cmpassword)
    EditText profile_cmpassword;

    @Order(4)
    @NotEmpty(message = "Sila isi nama")
    @InjectView(R.id.profile_name)
    EditText profile_name;

    @Order(5)
    @NotEmpty(message = "Sila pilih jantina")
    @InjectView(R.id.profile_gender)
    TextView profile_gender;

    @Order(6)
    @NotEmpty(message = "Sila isi tarikh lahir")
    @InjectView(R.id.profile_dob)
    TextView profile_dob;

    @Order(7)
    @NotEmpty(message = "Sila isi nombor telefon")
    @InjectView(R.id.profile_mobile)
    EditText profile_mobile;

    @Order(8)
    @NotEmpty(message = "Sila isi tinggi")
    @InjectView(R.id.profile_height)
    EditText profile_height;

    @Order(9)
    @NotEmpty(message = "Sila isi berat")
    @InjectView(R.id.profile_weight)
    EditText profile_weight;

    @Order(10)
    @NotEmpty(message = "Sila pilih vaper/merokok")
    @InjectView(R.id.profile_smoker)
    TextView profile_smoker;

    @Order(11)
    @NotEmpty(message = "Sila pilih negara")
    @InjectView(R.id.profile_country)
    TextView profile_country;

    @Order(12)
    @NotEmpty(message = "Sila pilih negeri")
    @InjectView(R.id.profile_state)
    TextView profile_state;

    @Order(13)
    @NotEmpty(message = "Sila isi bandar")
    @InjectView(R.id.profile_town)
    EditText profile_town;

    @Order(14)
    @NotEmpty(message = "Sila pilih pendidikan tertinggi")
    @InjectView(R.id.profile_education)
    TextView profile_education;

    @Order(15)
    @NotEmpty(message = "Sila pilih pekerjaan")
    @InjectView(R.id.profile_occupation)
    TextView profile_occupation;

    @Order(16)
    @NotEmpty(message = "Sila pilih status perkahwinan")
    @InjectView(R.id.profile_maritial)
    TextView profile_maritial;

    @Order(17)
    @NotEmpty(message = "Sila pilih bilangan anak")
    @InjectView(R.id.profile_children)
    TextView profile_children;

    @Order(18)
    @NotEmpty(message = "Sila pilih status hubungan")
    @InjectView(R.id.profile_relationship)
    TextView profile_relationship;

    @Order(19)
    @NotEmpty(message = "Sila pilih poligami")
    @InjectView(R.id.profile_polygamy)
    TextView profile_polygamy;

    @Order(20)
    @NotEmpty(message = "Sila pilih kedudukan kewangan")
    @InjectView(R.id.profile_financial)
    TextView profile_financial;

    @InjectView(R.id.profile_picture)
    ImageView profile_picture;

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
    private String storeAuth_token;

    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String DATEPICKER_TAG = "DATEPICKER_TAG";
    private ArrayList<DropDownItem> genderList;
    private ArrayList<DropDownItem> smokerList;
    private ArrayList<DropDownItem> countryList;
    private ArrayList<DropDownItem> stateList;
    private ArrayList<DropDownItem> maritialList;
    private ArrayList<DropDownItem> childList;
    private ArrayList<DropDownItem> relationList;
    private ArrayList<DropDownItem> polygamyList;
    private ArrayList<DropDownItem> financialList;
    private ArrayList<DropDownItem> educationList;
    private ArrayList<DropDownItem> occupationList;

    View view;

    public static UpdateProfileFragment newInstance() {

        UpdateProfileFragment fragment = new UpdateProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FireFlyApplication.get(getActivity()).createScopedGraph(new UpdateProfileModule(this)).inject(this);
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
        profile_password.setTransformationMethod(new PasswordTransformationMethod());
        profile_cmpassword.setTransformationMethod(new PasswordTransformationMethod());

        //Password hint
        String simple2 = "Kata Laluan (Jika Ingin Tukar)";
        //String colored2 = "*";
        SpannableStringBuilder builder2 = new SpannableStringBuilder();

        builder2.append(simple2);
        int start2 = builder2.length();
        //builder2.append(colored2);
        int end2 = builder2.length();

        builder2.setSpan(new ForegroundColorSpan(Color.RED), start2, end2,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_password.setHint(builder2);

        //Confirm Password hint
        String simple3 = "Sah Kata Laluan ";
        //String colored3 = "*";
        SpannableStringBuilder builder3 = new SpannableStringBuilder();

        builder3.append(simple3);
        int start3 = builder3.length();
        //builder3.append(colored3);
        int end3 = builder3.length();

        builder3.setSpan(new ForegroundColorSpan(Color.RED), start3, end3,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_cmpassword.setHint(builder3);

        //Name hint
        String simple4 = "Nama ";
        String colored4 = "*";
        SpannableStringBuilder builder4 = new SpannableStringBuilder();

        builder4.append(simple4);
        int start4 = builder4.length();
        builder4.append(colored4);
        int end4 = builder4.length();

        builder4.setSpan(new ForegroundColorSpan(Color.RED), start4, end4,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_name.setHint(builder4);

        //Gender hint
        String simple5 = "Jantina ";
        String colored5 = "*";
        SpannableStringBuilder builder5 = new SpannableStringBuilder();

        builder5.append(simple5);
        int start5 = builder5.length();
        builder5.append(colored5);
        int end5 = builder5.length();

        builder5.setSpan(new ForegroundColorSpan(Color.RED), start5, end5,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_gender.setHint(builder5);

        //DOB hint
        String simple6 = "Tarikh Lahir ";
        String colored6 = "*";
        SpannableStringBuilder builder6 = new SpannableStringBuilder();

        builder6.append(simple6);
        int start6 = builder6.length();
        builder6.append(colored6);
        int end6 = builder6.length();

        builder6.setSpan(new ForegroundColorSpan(Color.RED), start6, end6,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_dob.setHint(builder6);

        //Phone Number hint
        String simple7 = "Telefon Bimbit ";
        String colored7 = "*";
        SpannableStringBuilder builder7 = new SpannableStringBuilder();

        builder7.append(simple7);
        int start7 = builder7.length();
        builder7.append(colored7);
        int end7 = builder7.length();

        builder7.setSpan(new ForegroundColorSpan(Color.RED), start7, end7,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_mobile.setHint(builder7);

        //Height hint
        String simple8 = "Tinggi (cm) ";
        String colored8 = "*";
        SpannableStringBuilder builder8 = new SpannableStringBuilder();

        builder8.append(simple8);
        int start8 = builder8.length();
        builder8.append(colored8);
        int end8 = builder8.length();

        builder8.setSpan(new ForegroundColorSpan(Color.RED), start8, end8,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_height.setHint(builder8);

        //Weight hint
        String simple9 = "Berat (kg) ";
        String colored9 = "*";
        SpannableStringBuilder builder9 = new SpannableStringBuilder();

        builder9.append(simple9);
        int start9 = builder9.length();
        builder9.append(colored9);
        int end9 = builder9.length();

        builder9.setSpan(new ForegroundColorSpan(Color.RED), start9, end9,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_weight.setHint(builder9);

        //Smoker hint
        String simple10 = "Merokok/Vaper ";
        String colored10 = "*";
        SpannableStringBuilder builder10 = new SpannableStringBuilder();

        builder10.append(simple10);
        int start10 = builder10.length();
        builder10.append(colored10);
        int end10 = builder10.length();

        builder10.setSpan(new ForegroundColorSpan(Color.RED), start10, end10,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_smoker.setHint(builder10);

        //Country hint
        String simple11 = "Negara ";
        String colored11 = "*";
        SpannableStringBuilder builder11 = new SpannableStringBuilder();

        builder11.append(simple11);
        int start11 = builder11.length();
        builder11.append(colored11);
        int end11 = builder11.length();

        builder11.setSpan(new ForegroundColorSpan(Color.RED), start11, end11,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_country.setHint(builder11);

        //State hint
        String simple12 = "Negeri ";
        String colored12 = "*";
        SpannableStringBuilder builder12 = new SpannableStringBuilder();

        builder12.append(simple12);
        int start12 = builder12.length();
        builder12.append(colored12);
        int end12 = builder12.length();

        builder12.setSpan(new ForegroundColorSpan(Color.RED), start12, end12,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_state.setHint(builder12);

        //Town hint
        String simple13 = "Bandar ";
        String colored13 = "*";
        SpannableStringBuilder builder13 = new SpannableStringBuilder();

        builder13.append(simple13);
        int start13 = builder13.length();
        builder13.append(colored13);
        int end13 = builder13.length();

        builder13.setSpan(new ForegroundColorSpan(Color.RED), start13, end13,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_town.setHint(builder13);

        //Education hint
        String simple14 = "Pendidikan Tertinggi ";
        String colored14 = "*";
        SpannableStringBuilder builder14 = new SpannableStringBuilder();

        builder14.append(simple14);
        int start14 = builder14.length();
        builder14.append(colored14);
        int end14 = builder14.length();

        builder14.setSpan(new ForegroundColorSpan(Color.RED), start14, end14,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_education.setHint(builder14);
        profile_education.setMovementMethod(new ScrollingMovementMethod());

        //Occupation hint
        String simple = "Pekerjaan ";
        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(simple);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_occupation.setHint(builder);
        profile_occupation.setMovementMethod(new ScrollingMovementMethod());

        //Maritial hint
        String simple15 = "Status Perkahwinan ";
        String colored15 = "*";
        SpannableStringBuilder builder15 = new SpannableStringBuilder();

        builder15.append(simple15);
        int start15 = builder15.length();
        builder15.append(colored15);
        int end15 = builder15.length();

        builder15.setSpan(new ForegroundColorSpan(Color.RED), start15, end15,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_maritial.setHint(builder15);

        //Children hint
        String simple16 = "Mempunyai Anak ";
        String colored16 = "*";
        SpannableStringBuilder builder16 = new SpannableStringBuilder();

        builder16.append(simple16);
        int start16 = builder16.length();
        builder16.append(colored16);
        int end16 = builder16.length();

        builder16.setSpan(new ForegroundColorSpan(Color.RED), start16, end16,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_children.setHint(builder16);

        //Relationship hint
        String simple17 = "Status Hubungan ";
        String colored17 = "*";
        SpannableStringBuilder builder17 = new SpannableStringBuilder();

        builder17.append(simple17);
        int start17 = builder17.length();
        builder17.append(colored17);
        int end17 = builder17.length();

        builder17.setSpan(new ForegroundColorSpan(Color.RED), start17, end17,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_relationship.setHint(builder17);

        //Polygamy hint
        String simple18 = "Poligami ";
        String colored18 = "*";
        SpannableStringBuilder builder18 = new SpannableStringBuilder();

        builder18.append(simple18);
        int start18 = builder18.length();
        builder18.append(colored18);
        int end18 = builder18.length();

        builder18.setSpan(new ForegroundColorSpan(Color.RED), start18, end18,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_polygamy.setHint(builder18);

        //Financial hint
        String simple19 = "Kedudukan Kewangan ";
        String colored19 = "*";
        SpannableStringBuilder builder19 = new SpannableStringBuilder();

        builder19.append(simple19);
        int start19 = builder19.length();
        builder19.append(colored19);
        int end19 = builder19.length();

        builder19.setSpan(new ForegroundColorSpan(Color.RED), start19, end19,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        profile_financial.setHint(builder19);

        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        HashMap<String, String> initSign = pref.getSignatureFromLocalStorage();
        String s = initSign.get(SharedPrefManager.SIGNATURE);

        HashMap<String, String> initAuth = pref.getAuth();
        String a = initAuth.get(SharedPrefManager.USER_AUTH);

        //Request to get profile data
        ProfileViewRequest data = new ProfileViewRequest();
        data.setSignature(s);
        data.setAuth_token(a);
        presenter.viewFunction(data);

        genderList = getGender(getActivity());
        smokerList = getSmoker(getActivity());
        countryList = getCountry(getActivity());
        stateList = getState(getActivity());
        educationList = getEducation(getActivity());
        occupationList = getOccupation(getActivity());
        maritialList = getMaritial(getActivity());
        childList = getChild(getActivity());
        relationList = getRelation(getActivity());
        polygamyList = getPolygamy(getActivity());
        financialList = getFinancial(getActivity());

        profile_gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(genderList, getActivity(), profile_gender, true, view);
            }
        });

        profile_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePickerDialog.setYearRange(year - 80, year);
                datePickerDialog.show(getActivity().getSupportFragmentManager(), DATEPICKER_TAG);
            }
        });

        profile_smoker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(smokerList, getActivity(), profile_smoker, true, view);
            }
        });

        profile_country.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(countryList, getActivity(), profile_country, true, view);
            }
        });

        profile_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(stateList, getActivity(), profile_state, true, view);
            }
        });

        profile_education.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(educationList, getActivity(), profile_education, true, view);
            }
        });

        profile_occupation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(occupationList, getActivity(), profile_occupation, true, view);
            }
        });


        profile_maritial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(maritialList, getActivity(), profile_maritial, true, view);
            }
        });

        profile_children.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(childList, getActivity(), profile_children, true, view);
            }
        });

        profile_relationship.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(relationList, getActivity(), profile_relationship, true, view);
            }
        });

        profile_polygamy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(polygamyList, getActivity(), profile_polygamy, true, view);
            }
        });

        profile_financial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(financialList, getActivity(), profile_financial, true, view);
            }
        });

        profile_submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mValidator.validate();
            }
        });

        profile_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
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

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if(result.size() > 0){
            //Gson gson = new Gson();
            //UpdateReceive obj = gson.fromJson(result.get(0).getCachedResult(), UpdateReceive.class);
            //onUpdateSuccess(obj);
        }
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

    @Override
    public void onValidationSucceeded() {
        requestUpdateProfile();
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
        int mon = month+1;

        //String monthInAlphabet = getMonthAlphabet(month);
        profile_dob.setText(day + "/" + mon + "/" + year);

        //fullDate = day + " " + monthInAlphabet + " " + year;
        fullDate = day + "/" + mon + "/" + year;
        Log.e("fullDate", fullDate);
        int currentYear = calendar.get(Calendar.YEAR);
        age = currentYear - year;
        if(age < 18){
            limitAge = true;
            Toast.makeText(getActivity(), "Underage", Toast.LENGTH_SHORT).show();
        }else{
            limitAge = false;
        }
    }

    //-------------------------------UPDATE PROFILE---------------------------------//

    @Override
    public void onUpdateSuccess(final UpdateReceive obj) {
        dismissLoading();

        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status){

            //Log.e("Status update : ", obj.getStatus());
            //Log.e("Token", storeAuth_token);
            //setSuccessDialog(getActivity(), "Information Successfully Updated", HomeActivity.class,"Update Information");

            new SweetAlertDialog(getActivity(), SweetAlertDialog.SUCCESS_TYPE)
                    .setTitleText("Successfully Update!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            home();
                        }
                    })
                    .show();
        }
        RealmObjectController.clearCachedResult(getActivity());
    }

    public void home()
    {
        Intent loginPage = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(loginPage);
        getActivity().finish();
    }

    @Override
    public void onUpdateFailed(String dumm) {}

    public void requestUpdateProfile() {
        initiateLoading(getActivity());

        HashMap<String, String> initSign = pref.getSignatureFromLocalStorage();
        String s = initSign.get(SharedPrefManager.SIGNATURE);

        HashMap<String, String> initAuth = pref.getAuth();
        String a = initAuth.get(SharedPrefManager.USER_AUTH);

        UpdateRequest data = new UpdateRequest();

        data.setName(profile_name.getText().toString());
        data.setSex(profile_gender.getText().toString());
        data.setMobile(profile_mobile.getText().toString());
        data.setHeight(profile_height.getText().toString());
        data.setWeight(profile_weight.getText().toString());
        data.setSmoke(profile_smoker.getText().toString());
        data.setCountry(profile_country.getText().toString());
        data.setState(profile_state.getText().toString());
        data.setTown(profile_town.getText().toString());
        data.setEducation(profile_education.getText().toString());
        data.setOccupation(profile_occupation.getText().toString());
        data.setMaritial(profile_maritial.getText().toString());
        data.setChildren(profile_children.getText().toString());
        data.setRelationship(profile_relationship.getText().toString());
        data.setPolygamy(profile_polygamy.getText().toString());
        data.setFinancial(profile_financial.getText().toString());
        data.setAuth_token(a);
        data.setSignature(s);
        //date
        data.setDOB(fullDate);

        presenter.updateFunction(data);
        upload();
    }

    //-------------------------------VIEW SAVED PROFILE---------------------------------//

    @Override
    public void onViewSuccess(ProfileViewReceive obj) {
        dismissLoading();
        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status){
            setSummary(obj);

            /*HashMap<String, String> initUniq = pref.getUniqId();
            String u = initUniq.get(SharedPrefManager.UNIQ_ID);

            //Request to get image
            ImageViewRequest data = new ImageViewRequest();
            data.setUserId(u);
            data.setAuth_token(storeAuth_token);
            presenter.viewImageFunction(data);*/
        }
    }

    public void setSummary(ProfileViewReceive obj){

        String name = obj.getUserProfile().getName();
        String gender = obj.getUserProfile().getSex();
        String dob = obj.getUserProfile().getDOB();
        String mobile = obj.getUserProfile().getMobile();
        String height = obj.getUserProfile().getHeight();
        String weight = obj.getUserProfile().getWeight();
        String smoke = obj.getUserProfile().getSmoke();
        String country = obj.getUserProfile().getCountry();
        String state = obj.getUserProfile().getState();
        String town = obj.getUserProfile().getTown();
        String education = obj.getUserProfile().getEducation();
        String occupation = obj.getUserProfile().getOccupation();
        String userStatus = obj.getUserProfile().getMaritial();
        String child = obj.getUserProfile().getChildren();
        String relationship  = obj.getUserProfile().getRelationship();
        String polygamy = obj.getUserProfile().getPolygamy();
        String financial = obj.getUserProfile().getFinancial();
        String userPackage = obj.getUserProfile().getPackage();
        String imageUrl = App.IMAGE_URL + obj.getUserProfile().getImage();

        profile_package.setText(userPackage, TextView.BufferType.EDITABLE);

        if (name!=""){
            profile_name.setText(name,TextView.BufferType.EDITABLE);
        }
        if (gender!=""){
            profile_gender.setText(gender, TextView.BufferType.EDITABLE);
        }
        if (mobile!=""){
            profile_mobile.setText(mobile, TextView.BufferType.EDITABLE);
        }
        if (height!=null){
            profile_height.setText(height, TextView.BufferType.EDITABLE);
        }
        if (weight!=null){
            profile_weight.setText(weight, TextView.BufferType.EDITABLE);
        }
        if (smoke!=""){
            profile_smoker.setText(smoke, TextView.BufferType.EDITABLE);
        }
        if (country!=""){
            profile_country.setText(country, TextView.BufferType.EDITABLE);
        }
        if (state!=""){
            profile_state.setText(state, TextView.BufferType.EDITABLE);
        }
        if (town!=""){
            profile_town.setText(town, TextView.BufferType.EDITABLE);
        }
        if (education!=""){
            profile_education.setText(education, TextView.BufferType.EDITABLE);
        }
        if (occupation!=""){
            profile_occupation.setText(occupation, TextView.BufferType.EDITABLE);
        }
        if (userStatus!=""){
            profile_maritial.setText(userStatus, TextView.BufferType.EDITABLE);
        }
        if (child!=""){
            profile_children.setText(child, TextView.BufferType.EDITABLE);
        }
        if (relationship!=""){
            profile_relationship.setText(relationship, TextView.BufferType.EDITABLE);
        }
        if (polygamy!=""){
            profile_polygamy.setText(polygamy, TextView.BufferType.EDITABLE);
        }
        if (financial!=""){
            profile_financial.setText(financial, TextView.BufferType.EDITABLE);
        }
        if(dob!=""){
            profile_dob.setText(dob, TextView.BufferType.EDITABLE);
            fullDate = dob;
        }

        aq.id(R.id.profile_picture).image(imageUrl);
    }

    //-----------------------------------ADD NEW IMAGE--------------------------------------//

    private void selectImage() {
        dismissLoading();

        final CharSequence[] items = {"Take Photo", "Choose from Gallery",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Add Photo");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                //choose camera
                if (items[item].equals("Take Photo")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString());
                    startActivityForResult(intent, REQUEST_CAMERA);

                    //choose from gallery
                } else if (items[item].equals("Choose from Gallery")) {
                    Intent intent = new Intent(
                            Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(
                            Intent.createChooser(intent, "Select File"),
                            SELECT_FILE);

                    //exit message box--back to main page
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_FILE) {
                onSelectFromGalleryResult(data);

            } else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        profile_picture.setImageBitmap(thumbnail);
        changeImage();
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri selectedImageUri = data.getData();
        String[] projection = {MediaStore.MediaColumns.DATA};
        Cursor cursor = getActivity().managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 400;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        profile_picture.setImageBitmap(bm);

        changeImage();
    }

    //-----------------------------------CONVERT IMAGE TO 64BASE--------------------------------------//

    public void changeImage(){
        BitmapDrawable drawable = (BitmapDrawable) profile_picture.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, 0);

        pref.setImageBase(image);

        HashMap<String, String> initImage = pref.getImageBase();
        String ib = initImage.get(SharedPrefManager.IMAGEBASE);
        Log.e("64Base", ib);
    }

    //-----------------------------------IMAGE VIEW FROM DATABASE--------------------------------------//

    /*@Override
    public void onViewImageSuccess(ImageViewReceive obj) {

        dismissLoading();
        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());

        if (status){
            String image = obj.getUserImage().getImage();

            Log.e("ImageLoad", image);
            String imageUrl = App.IMAGE_URL + image;

            aq.id(R.id.profile_picture).image(imageUrl);
            RealmObjectController.clearCachedResult(getActivity());
        }
    }*/

    //-----------------------------------SAVE IMAGE TO DATABASE--------------------------------------//

    @Override
    public void onUploadImageSuccess(UploadImageReceive obj) {
        Log.e("status upload image", "success");
    }

    public void upload(){
        HashMap<String, String> initSign = pref.getSignatureFromLocalStorage();
        String sign = initSign.get(SharedPrefManager.SIGNATURE);

        HashMap<String, String> initImage = pref.getImageBase();
        String ib = initImage.get(SharedPrefManager.IMAGEBASE);

        HashMap<String, String> initAuth = pref.getAuth();
        String auth = initAuth.get(SharedPrefManager.USER_AUTH);

        //Request to get image
        UploadImageRequest data = new UploadImageRequest();
        data.setAuth_token(auth);
        data.setSignature(sign);
        data.setUserImage(ib);

        presenter.uploadImageFunction(data);
    }
}