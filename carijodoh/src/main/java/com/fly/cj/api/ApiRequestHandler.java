package com.fly.cj.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.api.obj.ProfileViewReceive;
import com.fly.cj.api.obj.RegisterReceive;
import com.fly.cj.api.obj.UpdateReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.object.LoginRequest;
import com.fly.cj.ui.object.ProfileViewRequest;
import com.fly.cj.ui.object.RegisterRequest;
import com.fly.cj.ui.object.UpdateRequest;
import com.fly.cj.utils.RealmObjectController;
import com.squareup.otto.Bus;
import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiRequestHandler {

    private final Bus bus;
    private final ApiService apiService;
    Context context;
    ProgressDialog mProgressDialog;
    private int inc;
    private boolean retry;

    public ApiRequestHandler(Bus bus, ApiService apiService) {
        this.bus = bus;
        this.apiService = apiService;
        retry = false;
    }

    // -------------------------------------LOGIN----------------------------------------- //

    @Subscribe
    public void onLoginRequest(final LoginRequest event) {

        //LoginReceive -> receive response from api - pass to object
        apiService.onRequestToLogin(event, new Callback<LoginReceive>() {

            @Override
            public void success(LoginReceive rhymesResponse, Response response) {

                //otto send api data to login presenter back
                bus.post(new LoginReceive(rhymesResponse));
                RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
            }
        });
    }

    // -------------------------------------REGISTER----------------------------------------- //

    @Subscribe
    public void onRegisterRequest(final RegisterRequest event) {

        //RegisterReceive -> receive response from api - pass to object
        apiService.onRequestToRegister(event, new Callback<RegisterReceive>() {

            @Override
            public void success(RegisterReceive rhymesResponse, Response response) {
                //otto send api data to register presenter back
                bus.post(new RegisterReceive(rhymesResponse));
                RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                resetInc();
            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("inc", Integer.toString(inc));
                if (retry) {
                    onRegisterRequest(event);
                    loop();
                } else {
                    resetInc();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }
        });
    }

    // -------------------------------------UPDATE----------------------------------------- //

    @Subscribe
    public void onUpdateRequest(final UpdateRequest event) {

        String token = "Bearer " + event.getAuth_token();

        //UpdateReceive -> receive response from api - pass to object
        apiService.onRequestToUpdate(token, event, new Callback<UpdateReceive>() {

            @Override
            public void success(UpdateReceive rhymesResponse, Response response) {

                //otto send api data to update presenter back
                bus.post(new UpdateReceive(rhymesResponse));
                RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                resetInc();
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                Log.e("inc", Integer.toString(inc));
                if (retry) {
                    onUpdateRequest(event);
                    loop();
                } else {
                    resetInc();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }
        });
    }

    // -------------------------------------VIEW PROFILE----------------------------------------- //

    @Subscribe
    public void onProfileViewRequest(final ProfileViewRequest event) {

        String token = "Bearer " + event.getAuth_token();

        //UpdateReceive -> receive response from api - pass to object
        apiService.onRequestToView(token, event, new Callback<ProfileViewReceive>() {

            @Override
            public void success(ProfileViewReceive rhymesResponse, Response response) {

                //otto send api data to view presenter back
                bus.post(new ProfileViewReceive(rhymesResponse));
                RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                resetInc();
            }

            @Override
            public void failure(RetrofitError error) {

                BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                Log.e("inc", Integer.toString(inc));
                if (retry) {
                    onProfileViewRequest(event);
                    loop();
                } else {
                    resetInc();
                    BaseFragment.setAlertNotification(MainFragmentActivity.getContext());
                }
            }
        });
    }

    public void resetInc(){
        inc = 0;
        retry = false;
    }

    public void loop(){
        inc++;
        if(inc > 4){
            retry = false;
        }else{
            retry = false;
        }
    }
}
