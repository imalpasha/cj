package com.fly.cj;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.activity.Homepage.HomeActivity;
import com.fly.cj.utils.SharedPrefManager;
import com.fly.cj.utils.Utils;

import cn.pedant.SweetAlert.SweetAlertDialog;

/**
 * Created by Dell on 1/5/2016.
 */
public class Controller extends BaseFragment {

    public static void clearAll(Activity act){
        SharedPrefManager  pref = new SharedPrefManager(act);
        pref.clearSignatureFromLocalStorage();
        pref.clearPassword();
        pref.clearUserEmail();
        pref.setLoginStatus("N");
        Log.e("SUCCESS","ok");
    }


    private static SweetAlertDialog pDialog;

    public static boolean connectionAvailable(Activity act){

        Boolean internet;
        if (!Utils.isNetworkAvailable(act)) {
            internet = false;
        }else{
            internet = true;
        }

        return internet;
    }

    public static boolean getRequestStatus(String objStatus,String message,Activity act) {

        SharedPrefManager pref;
        pref = new SharedPrefManager(act);

        Boolean status = false;
        if (objStatus.equals("success") || objStatus.equals("Redirect")) {
            status = true;

        } else if (objStatus.equals("error") || objStatus.equals("error_validation")) {
            status = false;
            setAlertDialog(act, message,"Validation Error");

        } else if (objStatus.equals("401")) {
            status = false;
            pref.clearSignatureFromLocalStorage();

        }else if(objStatus.equals("force_logout")){
            Controller.clearAll(act);
            resetPage(act);
        }else if(objStatus.equals("error")) {
            //croutonAlert(getActivity(),obj.getMessage());
            //setSuccessDialog(getActivity(), obj.getMessage(), getActivity(), SearchFlightActivity.class);
            setAlertDialog(act,message,"Validation Error");
        }
        return status;

    }

    public void retry(){

    }


    //Redirect
    public static void resetPage(Activity act){
        Intent loginPage = new Intent(act, HomeActivity.class);
        act.startActivity(loginPage);
        act.finish();
    }

}
