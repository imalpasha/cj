package com.fly.cj.utils;

import android.app.Activity;
import android.util.Log;

import com.fly.cj.MainFragmentActivity;
import com.fly.cj.base.BaseFragment;
import com.fly.cj.ui.object.CachedResult;
import com.google.gson.Gson;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by Dell on 2/11/2016.
 */
public class RealmObjectController extends BaseFragment {

    public static void cachedResult(Activity act, String cachedResult) {

        Realm realm = Realm.getInstance(act);

        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

        realm.beginTransaction();
        CachedResult realmObject = realm.createObject(CachedResult.class);
        //realmObject.setCachedAPI(cachedApi);
        realmObject.setCachedResult(cachedResult);
        realm.commitTransaction();

    }

    public static RealmResults<CachedResult> getCachedResult(Activity act) {

        Realm realm = Realm.getInstance(act);
        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();

        return result;
    }

    public static void clearCachedResult(Activity act) {

        Realm realm = Realm.getInstance(act);

        final RealmResults<CachedResult> result = realm.where(CachedResult.class).findAll();
        realm.beginTransaction();
        result.clear();
        realm.commitTransaction();

    }


    public static void deleteRealmFile(Activity act) {
        /*Remove Realm Data*/
        RealmConfiguration config = new RealmConfiguration.Builder(act).deleteRealmIfMigrationNeeded().build();
        Realm realm = Realm.getInstance(config);
        realm.close();
        realm.deleteRealm(config);

    }

}
