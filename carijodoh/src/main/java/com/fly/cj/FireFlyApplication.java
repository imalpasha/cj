package com.fly.cj;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

//import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.Crashlytics;
import com.estimote.sdk.BeaconManager;
import com.estimote.sdk.Region;
import com.fly.cj.api.ApiRequestHandler;
import com.fly.cj.api.ApiService;
import com.fly.cj.utils.SharedPrefManager;
import com.squareup.otto.Bus;

import java.util.UUID;

import javax.inject.Inject;

import dagger.ObjectGraph;
//import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Fabric;
import me.mattlogan.rhymecity.Modules;

public class FireFlyApplication extends AnalyticsApplication {

    private ObjectGraph objectGraph;
    private static Activity instance;
    private BeaconManager beaconManager;
    private Region region;
    private SharedPrefManager pref;
    private Boolean departure_gate = true;
    private Boolean arrive_airport = true;

    @Inject Bus bus;
    @Inject ApiService apiService;

    @Override
    public void onCreate() {
        super.onCreate();

        //Crashlytics crashlytics = new Crashlytics.Builder().disabled(BuildConfig.DEBUG).build();
        //Fabric.with(this, crashlytics);
        //Fabric.with(this, new Crashlytics());
        buildObjectGraphAndInject();
        createApiRequestHandler();
        //instance = FireFlyApplicationthis;
         /* -------------- Beacon ----------------*/
        //beaconManager = new BeaconManager(this);
        pref = new SharedPrefManager(this);
        region = new Region("region", UUID.fromString("B9407F30-F5F8-466E-AFF9-25556B57FE6D"), null, null);
        /* ------------------------------------- */
        Log.e("1", "1");
 /* ----------------------- Beacon Range ------------------------------- */

        /*On enter region - start monitoring*/

        /*beaconManager.connect(new BeaconManager.ServiceReadyCallback() {
            @Override
            public void onServiceReady() {
                //beaconManager.startRanging(region);
            }
        });

        beaconManager.setRangingListener(new BeaconManager.RangingListener() {
            @Override
            public void onBeaconsDiscovered(Region region, List<Beacon> list) {
                if (!list.isEmpty()) {
                    Beacon nearestBeacon = list.get(0);
                    double nearestBeaconDistance = Utils.computeAccuracy(nearestBeacon);
                    Log.e(Integer.toString(nearestBeacon.getMinor()),Double.toString(nearestBeaconDistance));
                    if (nearestBeacon.getMinor() == 2117 && nearestBeaconDistance < 1) {

                        if (arrive_airport) {
                            triggerNotification("ARRIVE_AIRPORT");
                            arrive_airport = false;
                        }

                    } else if (nearestBeacon.getMinor() == 40462 && nearestBeaconDistance < 1) {

                        if (departure_gate) {
                            triggerNotification("KIOSK");
                            departure_gate = false;
                        }

                    }

                }
            }
        });
*/
        /* --------------------------------------------------------------------------*/






    }

    private void buildObjectGraphAndInject() {
        objectGraph = ObjectGraph.create(Modules.list("ASJ3wq8YnBmshFGszZZFHEntCFOUp1xhB2Sjsn4QZMpC3KV6kk"));
        objectGraph.inject(this);
        //getString(R.string.api_key)
    }

    private void createApiRequestHandler() {
        bus.register(new ApiRequestHandler(bus, apiService));
    }



    public ObjectGraph createScopedGraph(Object module) {
        return objectGraph.plus(module);
    }

    public static FireFlyApplication get(Context context) {
        return (FireFlyApplication) context.getApplicationContext();
    }

    public static Activity getContext() {
        return instance;
        //return instance;

    }

    private Intent getLauncherIntent(Context context){
        return context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
    }


}
