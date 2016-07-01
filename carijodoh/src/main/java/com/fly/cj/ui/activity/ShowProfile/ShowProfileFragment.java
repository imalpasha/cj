package com.fly.cj.ui.activity.ShowProfile;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fly.cj.Controller;
import com.fly.cj.FireFlyApplication;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.Favourite.FavouriteActivity;
import com.fly.cj.ui.activity.FragmentContainerActivity;
import com.fly.cj.ui.activity.Gallery.GalleryActivity;
import com.fly.cj.ui.activity.PaidPlan.PaidPlanActivity;
import com.fly.cj.ui.module.ShowProfileModule;
import com.fly.cj.ui.object.CachedResult;
import com.fly.cj.ui.object.ShowProfileRequest;
import com.fly.cj.ui.presenter.ShowProfilePresenter;
import com.fly.cj.utils.App;
import com.fly.cj.utils.RealmObjectController;
import com.fly.cj.utils.SharedPrefManager;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.InjectView;
import io.realm.RealmResults;

public class ShowProfileFragment extends BaseFragment implements ShowProfilePresenter.ShowProfileView{

    @Inject
    ShowProfilePresenter presenter;

    @InjectView(R.id.show_age)
    TextView show_age;

    @InjectView(R.id.show_gender)
    TextView show_gender;

    @InjectView(R.id.show_height)
    TextView show_height;

    @InjectView(R.id.show_weight)
    TextView show_weight;

    @InjectView(R.id.show_status)
    TextView show_status;

    @InjectView(R.id.show_title_id)
    TextView show_title_id;

    @InjectView(R.id.show_title_age)
    TextView show_title_age;

    @InjectView(R.id.show_title_online)
    TextView show_title_online;

    @InjectView(R.id.show_fav_icon)
    LinearLayout show_fav_icon;

    @InjectView(R.id.show_gal_icon)
    LinearLayout show_gal_icon;

    @InjectView(R.id.show_chat_icon)
    LinearLayout show_chat_icon;

    @InjectView(R.id.show_profile_pic)
    ImageView show_profile_pic;

    private int fragmentContainerId;
    private SharedPrefManager pref;
    private int user_id;
    private String token;

    View view;

    public static ShowProfileFragment newInstance() {

        ShowProfileFragment fragment = new ShowProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //inject (singleton)
        FireFlyApplication.get(getActivity()).createScopedGraph(new ShowProfileModule(this)).inject(this);
        RealmObjectController.clearCachedResult(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.show_profile, container, false);
        ButterKnife.inject(this, view);
        aq.recycle(view);
        pref = new SharedPrefManager(getActivity());

        HashMap<String, String> initAuth = pref.getAuth();
        String autht = initAuth.get(SharedPrefManager.USER_AUTH);

        HashMap<String, String> initPersonId = pref.getProfileId();
        String user_id = initPersonId.get(SharedPrefManager.PERSON_ID);

        ShowProfileRequest data = new ShowProfileRequest();
        data.setAuth_token(autht);
        Log.e("id from pref", user_id);
        data.setId(user_id);
        presenter.showFunction(data);

        show_fav_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent fav = new Intent(getActivity(), FavouriteActivity.class);
                fav.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(fav);
            }
        });

        show_gal_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent gal = new Intent(getActivity(), GalleryActivity.class);
                gal.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(gal);
            }
        });

        show_chat_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent chat = new Intent(getActivity(), PaidPlanActivity.class);
                chat.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(chat);
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
    public void onResume() {
        super.onResume();
        presenter.onResume();
        Log.e("ONRESUME", "TRUE");

        RealmResults<CachedResult> result = RealmObjectController.getCachedResult(MainFragmentActivity.getContext());
        if (result.size() > 0) {
            Gson gson = new Gson();
            ProfileViewReceive obj = gson.fromJson(result.get(0).getCachedResult(), ProfileViewReceive.class);
            onShowSuccess(obj);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.onPause();
        Log.e("ONPAUSE", "TRUE");
    }

    @Override
    public void onShowSuccess(ProfileViewReceive obj) {
        dismissLoading();
        Boolean status = Controller.getRequestStatus(obj.getStatus(), "", getActivity());
        if (status){
            Log.e("status", obj.getStatus());
            setShow(obj);
        }
    }

    public void setShow(ProfileViewReceive obj){
        String user_id = obj.getUserProfile().getUserId();
        String age = obj.getUserProfile().getDOB();
        String gender = obj.getUserProfile().getSex();
        String height = obj.getUserProfile().getHeight();
        String weight = obj.getUserProfile().getWeight();
        String status = obj.getUserProfile().getMaritial();
        String image = obj.getUserProfile().getImage();

        String dateString = age;
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = dateFormat.parse(dateString);
            System.out.println(date);
            int d = getDay(date);
            System.out.println(d);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);

            int real = year - d;
            show_age.setText(String.valueOf(real) + " tahun");
            show_title_age.setText(String.valueOf(real) + " tahun");

        } catch (ParseException e) {
            e.printStackTrace();
        }

        HashMap<String, String> initUserStatus = pref.getUserStatus();
        String status_online = initUserStatus.get(SharedPrefManager.USER_STATUS);

        show_title_id.setText(user_id);
        show_gender.setText(gender);
        show_height.setText(height);
        show_weight.setText(weight);
        show_status.setText(status);
        show_title_online.setText(status_online);

        String url = App.IMAGE_URL + image;

        if (image.equals("")){
            show_profile_pic.setImageResource(R.drawable.default_profile2);
        }
        else {
            aq.id(R.id.show_profile_pic).image(url);
        }
    }

    public static int getDay(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.YEAR);
    }
}
