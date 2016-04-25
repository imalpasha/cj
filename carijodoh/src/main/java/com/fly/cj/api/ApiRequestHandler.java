package com.fly.cj.api;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import com.estimote.sdk.repackaged.gson_v2_3_1.com.google.gson.Gson;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.api.obj.LoginReceive;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.object.FlightSummary;
import com.fly.cj.ui.object.LoginRequest;
import com.fly.cj.ui.object.PushNotificationObj;
import com.fly.cj.ui.object.RegisterObj;
import com.fly.cj.ui.object.Signature;
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

    // ------------------------------------------------------------------------------ //


    @Subscribe
    public void onLoginRequest(final LoginRequest event) {


        apiService.onRequestToLogin(event, new Callback<LoginReceive>() {

            @Override
            public void success(LoginReceive rhymesResponse, Response response) {

                bus.post(new LoginReceive(rhymesResponse));
                RealmObjectController.cachedResult(MainFragmentActivity.getContext(), (new Gson()).toJson(rhymesResponse));
                resetInc();


            }

            @Override
            public void failure(RetrofitError error) {

                Log.e("inc", Integer.toString(inc));
                if (retry) {
                    onLoginRequest(event);
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
