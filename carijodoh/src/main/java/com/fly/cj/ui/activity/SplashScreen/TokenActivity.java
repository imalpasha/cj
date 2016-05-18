package com.fly.cj.ui.activity.SplashScreen;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.fly.cj.R;
import com.fly.cj.ui.activity.PushNotification.AlertDialogManager;
import com.fly.cj.ui.activity.PushNotification.ConnectionDetector;
import com.fly.cj.ui.activity.PushNotification.WakeLocker;
import com.google.android.gcm.GCMRegistrar;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.fly.cj.ui.activity.PushNotification.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static com.fly.cj.ui.activity.PushNotification.CommonUtilities.EXTRA_MESSAGE;
import static com.fly.cj.ui.activity.PushNotification.CommonUtilities.SENDER_ID;

public class TokenActivity extends Activity {
    // label to display gcm messages
    TextView lblMessage;
    EditText textView;
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    // Connection detector
    ConnectionDetector cd;

    public static String name;
    public static String email;
    private SweetAlertDialog pDialog;
    private static Activity activity;
    String regId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_screen);
        pDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        activity = this;
        cd = new ConnectionDetector(getApplicationContext());
        getGCMKey();
    }

    public void getGCMKey(){

        if(pDialog.isShowing()){
            pDialog.dismiss();
        }

        // Make sure the device has the proper dependencies.
        GCMRegistrar.checkDevice(this);

        // Make sure the manifest was properly set - comment out this line
        // while developing the app, then uncomment it when it's ready.
        GCMRegistrar.checkManifest(this);
        //GCMRegistrar.checkManifest(this);

        registerReceiver(mHandleMessageReceiver, new IntentFilter(DISPLAY_MESSAGE_ACTION));

        regId = GCMRegistrar.getRegistrationId(this);

        if (regId.equals("")) {
            // Registration is not present, register now with GCM
            GCMRegistrar.register(this, SENDER_ID);
            this.finish();

            Log.e("regId", "1");

        }else{
            // Check if Internet present
            if (cd.isConnectingToInternet()) {


                Log.e("REG ID",regId);
                Intent home = new Intent(activity, SplashScreenActivity.class);
                home.putExtra("GCM_KEY", regId);
                activity.startActivity(home);
                activity.finish();
                return;

            }
        }

    }

    /**
     * Receiving push messages
     * */
    private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String newMessage = intent.getExtras().getString(EXTRA_MESSAGE);
            // Waking up mobile if it is sleeping
            WakeLocker.acquire(getApplicationContext());
            Log.e("newMessage",newMessage);
            /**
             * Take appropriate action on this message
             * depending upon your app requirement
             * For now i am just displaying it on the screen
             * */

            // Showing received message
           // lblMessage.append(newMessage + "\n");
          //  Toast.makeText(getApplicationContext(), "New Message: " + newMessage, Toast.LENGTH_LONG).show();

            // Releasing wake lock
            WakeLocker.release();
        }
    };

    public static void splash(Context act,String regId){

        Log.e("Token",regId);
        Intent home = new Intent(activity, SplashScreenActivity.class);
        home.putExtra("GCM_KEY", regId);
        home.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(home);
        activity.finish();
    }


    @Override
    protected void onDestroy() {
        if (mRegisterTask != null) {
            mRegisterTask.cancel(true);
        }
        try {
            unregisterReceiver(mHandleMessageReceiver);
            GCMRegistrar.onDestroy(this);
        } catch (Exception e) {
            //Log.e("UnRegister Receiver Error", "> " + e.getMessage());
        }
        super.onDestroy();
    }

}
