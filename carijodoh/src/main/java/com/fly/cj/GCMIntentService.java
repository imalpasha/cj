package com.fly.cj;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.fly.cj.ui.activity.PushNotification.ServerUtilities;
import com.fly.cj.ui.activity.SplashScreen.TokenActivity;
import com.google.android.gcm.GCMBaseIntentService;
import com.google.gson.Gson;

import static com.fly.cj.ui.activity.PushNotification.CommonUtilities.SENDER_ID;
import static com.fly.cj.ui.activity.PushNotification.CommonUtilities.displayMessage;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

    public GCMIntentService() {
        super(SENDER_ID);
    }

    /**
     * Method called on device registered
     **/
    @Override
    protected void onRegistered(Context context, String registrationId) {
        Log.i(TAG, "Device registered: regId = " + registrationId);
        Log.e("Registering", "tRUE");
        //displayMessage(context, "Your device registred with GCM");
        //ServerUtilities.register(context, MainActivity.name, MainActivity.email, registrationId);

        //back to splash screen
        //BaseFragment.splashScreen(context,registrationId);
        TokenActivity.splash(context,registrationId);

    }

    /**
     * Method called on device un registred
     * */
    @Override
    protected void onUnregistered(Context context, String registrationId) {
        Log.i(TAG, "Device unregistered");
      //  displayMessage(context, "Device Unregistered");
      //  ServerUtilities.unregister(context, registrationId);
    }

    /**
     * Method called on Receiving a new message
     * */


    @Override
    protected void onMessage(Context context, Intent intent) {
        Log.i(TAG, "Received message");
        //getIntent().getStringExtra(KEY_EXTRA);

        Log.e("intent", intent.getClass().getCanonicalName());
        String message = intent.getExtras().getString("data");
        
       // displayMessage(context, message);
        // notifies user
        generateNotification(context, message);
        //BaseFragmentActivity.setAppStatus(true);
       // Log.e("CheckAppStatus", Boolean.toString(BaseFragmentActivity.getAppStatus()));

    }

    /**
     * Method called on receiving a deleted message
     * */
    @Override
    protected void onDeletedMessages(Context context, int total) {
        Log.i(TAG, "Received deleted messages notification");
        String message = "Register";
        //displayMessage(context, message);
        // notifies user
       // generateNotification(context, message);
    }

    /**
     * Method called on Error
     * */
    @Override
    public void onError(Context context, String errorId) {
        Log.i(TAG, "Received error: " + errorId);
        Log.e("Registering", "NOT REGISTER");

        //displayMessage(context, "Register");
    }

    @Override
    protected boolean onRecoverableError(Context context, String errorId) {
        // log message
        Log.i(TAG, "Received recoverable error: " + errorId);
        //displayMessage(context, getString(R.string.register,errorId));
        return super.onRecoverableError(context, errorId);
    }

    /**
     * Issues a notification to inform the user that server has sent a message.
     */

    public class GCMClass {

        private String title;
        private String body;

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }

    private void generateNotification(Context context,String notificationMessage) {

        int requestID = (int) System.currentTimeMillis();

        //Gson gson = new Gson();
        //GCMClass obj = gson.fromJson(notificationMessage, GCMClass.class);

        /*Intent notificationIntent = new Intent(context, TokenActivity.class);
        notificationIntent.setAction(Intent.ACTION_MAIN);

        PendingIntent contentIntent = PendingIntent.getActivity(this, requestID,notificationIntent, PendingIntent.FLAG_ONE_SHOT);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        mBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
        mBuilder
                .setContentText("GCM Notification")
                .setContentTitle(String.format(notificationMessage))
                .setSmallIcon(R.drawable.departure_icon)
                .setColor(Color.argb(0x55, 0x00, 0x00, 0xff))
                .setTicker(String.format(notificationMessage));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mBuilder.setContentIntent(contentIntent);
        notificationManager.notify(1, mBuilder.build());
*/

       Log.e("GCM Message",notificationMessage);
    }



    /*private void generateNotification2(Context context, String message) {

        Gson gson = new Gson();
        GCMClass obj = gson.fromJson(message, GCMClass.class);

        //convert string to json
        Intent viewIntent = new Intent(context, Pop2NotificationActivity.class);
        Log.e("Visibile","true");
        viewIntent.setAction("android.intent.action.MAIN");
        viewIntent.addCategory("android.intent.category.LAUNCHER");
        viewIntent.putExtra("MESSAGE", message);


        PendingIntent viewPendingIntent = PendingIntent.getActivity(this, 0, viewIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
        notificationBuilder.setAutoCancel(true).setDefaults(Notification.DEFAULT_ALL);
        notificationBuilder
                .setContentText(obj.getBody())
                .setContentTitle(String.format(obj.getTitle()))
                .setSmallIcon(R.drawable.departure_icon)
                .setColor(Color.argb(0x55, 0x00, 0x00, 0xff))
                .setTicker(String.format(obj.getTitle()));

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationBuilder.setContentIntent(viewPendingIntent);
        notificationManager.notify(1, notificationBuilder.build());

    } */

    /*public void test(){
        BeaconController.startRangeDeparture(this);
    }*/

}
