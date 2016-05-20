package com.fly.cj.ui.activity.Profile;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
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
import com.fly.cj.R;
import com.fly.cj.api.obj.UpdateReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.ui.module.ProfileModule;
import com.fly.cj.ui.object.UpdateRequest;
import com.fly.cj.ui.presenter.ProfilePresenter;
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

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ProfileFragment extends BaseFragment implements Validator.ValidationListener, com.fourmob.datetimepicker.date.DatePickerDialog.OnDateSetListener,ProfilePresenter.ProfileView {

    @Inject
    ProfilePresenter presenter;

    @InjectView(R.id.profile_submitBtn)
    Button profile_submitBtn;

    @Order(1)
    @InjectView(R.id.profile_email)
    TextView profile_email;

    @Order(2)
    @InjectView(R.id.profile_password)
    TextView profile_password;

    @Order(3)
    @InjectView(R.id.profile_cmpassword)
    TextView profile_cmpassword;

    @Order(4)
    @InjectView(R.id.profile_name)
    TextView profile_name;

    @Order(5)
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

    @Order(11)
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

    @Order(16)
    @InjectView(R.id.profile_maritial)
    TextView profile_maritial;

    @Order(17)
    @InjectView(R.id.profile_children)
    TextView profile_children;

    @Order(18)
    @InjectView(R.id.profile_relationship)
    TextView profile_relationship;

    @Order(19)
    @InjectView(R.id.profile_polygamy)
    TextView profile_polygamy;

    @Order(20)
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
    String auth, signature;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;

    private String DATEPICKER_TAG = "DATEPICKER_TAG";
    private ArrayList<DropDownItem> genderList;
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

        HashMap<String, String> initAuth = pref.getAuth();
        auth = initAuth.get(SharedPrefManager.USER_AUTH);
        Log.e("Token pref", auth);

        HashMap<String, String> initSignature = pref.getSignatureFromLocalStorage();
        signature = initSignature.get(SharedPrefManager.SIGNATURE);
        //Log.e("Signature pref", signature);

        calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final com.fourmob.datetimepicker.date.DatePickerDialog datePickerDialog = com.fourmob.datetimepicker.date.DatePickerDialog.newInstance(this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

        //UpdateRequest info = new UpdateRequest();
        //presenter.viewFunction(info);

        genderList = getGender(getActivity());
        smokerList = getSmoker(getActivity());
        stateList = getState(getActivity());
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

        profile_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupSelection(stateList, getActivity(), profile_state, true, view);
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
            limitAge = true;
            Toast.makeText(getActivity(), "Underage", Toast.LENGTH_SHORT).show();

        }else{
            limitAge = false;
        }

    }

    @Override
    public void onUpdateSuccess(UpdateReceive obj) {
        dismissLoading();
        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status){
            Log.e("Status update : ", obj.getStatus());
            Log.e("Token", obj.getAuth_token());
            Log.e("Signature", obj.getSignature());
            Log.e("New Education", obj.getEducation());
            setSuccessDialog(getActivity(), "Information Successfully Updated", HomeActivity.class,"Update Information");

            pref.setUserAuth(obj.getAuth_token());
            pref.setSignatureToLocalStorage(obj.getSignature());

            home();
        }
    }

    @Override
    public void onUpdateFailed(String dumm) {}

    public void requestUpdateProfile() {
        initiateLoading(getActivity());

        HashMap<String, String> init = pref.getSignatureFromLocalStorage();
        String signatureFromLocal = init.get(SharedPrefManager.SIGNATURE);

        UpdateRequest data = new UpdateRequest();

        data.setMobile(profile_mobile.getText().toString());
        data.setHeight(profile_height.getText().toString());
        data.setWeight(profile_weight.getText().toString());
        data.setSmoke(profile_smoker.getText().toString());
        data.setState(profile_state.getText().toString());
        data.setTown(profile_town.getText().toString());
        data.setEducation(profile_education.getText().toString());
        data.setOccupation(profile_occupation.getText().toString());
        data.setAuth_token(auth);
        data.setSignature(signature);
        //date
        data.setDOB(fullDate);

        presenter.updateFunction(data);
    }

    public void home()
    {
        Intent loginPage = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(loginPage);
        getActivity().finish();
    }

    //-------------------------------VIEW SAVED PROFILE---------------------------------//

    @Override
    public void onUpdateView(UpdateReceive obj) {
        dismissLoading();
        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status){
            setSummary(obj);
        }
    }

    public void setSummary(UpdateReceive obj){
        String dob = obj.getDOB();
        String mobile = obj.getMobile();
        String height = obj.getHeight();
        String weight = obj.getWeight();
        String smoke = obj.getSmoke();
        String state = obj.getState();
        String town = obj.getTown();
        String education = obj.getEducation();
        String occupation = obj.getOccupation();

        if (mobile!=""){
            profile_mobile.setText(mobile, TextView.BufferType.EDITABLE);
        }
        if (height!=""){
            profile_height.setText(height, TextView.BufferType.EDITABLE);
        }
        if (weight!=""){
            profile_weight.setText(weight, TextView.BufferType.EDITABLE);
        }
        if (smoke!=""){
            profile_smoker.setText(smoke, TextView.BufferType.EDITABLE);
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
        //date picker
        String dobNew = reformatDOB(dob);
        profile_dob.setText(dobNew);
        fullDate = dob;
    }

    //-----------------------------------ADD NEW IMAGE--------------------------------------//

    private void selectImage() {
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
        changeImage();
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
    }

    //-----------------------------------CONVERT IMAGE TO 64BASE--------------------------------------//

    public void changeImage(){
        BitmapDrawable drawable = (BitmapDrawable) profile_picture.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG,100,bos);
        byte[] bb = bos.toByteArray();
        String image = Base64.encodeToString(bb, 0);
        System.out.println("string:"+ image);

        //Log.e("64Base", image);
        //imageInText.setText(img_str);
    }
}