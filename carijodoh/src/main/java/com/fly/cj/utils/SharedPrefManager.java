package com.fly.cj.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import java.util.HashMap;

public class SharedPrefManager {
    private static final String PREF_NAME = "AndroidHivePref";
    public static final String SELECTED = "SELECTED";
    public static final String SIGNATURE = "SIGNATURE";
    public static final String USER_EMAIL = "USER_EMAIL";
    public static final String USER_AUTH = "USER_AUTH";
    //public static final String USER_SIGNATURE = "USER_SIGNATURE";
    public static final String USER_INFO = "USER_INFO";
    public static final String COUNTRY = "COUNTRY";
    public static final String STATE = "STATE";

    public static final String ISLOGIN = "ISLOGIN";
    public static final String ISREGISTER = "ISREGISTER";
    public static final String USERNAME = "USERNAME";

    public static final String PASSWORD = "PASSWORD";
    public static final String DATA_VERSION = "DV";
    public static final String TEMP_RESULT = "TR";

    public static final String USER_ID ="UI";

    public static final String APP_VERSION = "AV";
    public static final String GCMKey = "GCMKey";

    int PRIVATE_MODE = 0;
    Context _context;
    private SharedPreferences _sharedPrefs;
    private Editor _prefsEditor;

    public SharedPrefManager(Context context) {
        this._context = context;
        _sharedPrefs = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        _prefsEditor = _sharedPrefs.edit();
    }

    //-------------------------SIGNATURE------------------------------//
    /*Return Signature Value*/
    public HashMap<String, String> getSignatureFromLocalStorage() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SIGNATURE, _sharedPrefs.getString(SIGNATURE, null));
        return init;
    }

    /*Set Signature Value*/
    public void setSignatureToLocalStorage(String signature) {
        _prefsEditor.putString(SIGNATURE, signature);
        _prefsEditor.apply();
    }

    /*Clear Signature Value*/
    public void clearSignatureFromLocalStorage() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(SIGNATURE).apply();
    }

    //-------------------------SIGNATURE END------------------------------//

    /*user id*/
    public HashMap<String, String> getUserID() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_ID, _sharedPrefs.getString(USER_ID, null));
        return init;
    }

    /*GCM*/
    /*Set GCM Value*/
    public void setGCMKey(String key) {
        _prefsEditor.putString(GCMKey, key);
        _prefsEditor.apply();
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getGCMKey() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(GCMKey, _sharedPrefs.getString(GCMKey, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getAppVersion() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(APP_VERSION, _sharedPrefs.getString(APP_VERSION, null));
        return init;
    }

    /*SOCIAL MEDIA*/
    public HashMap<String, String> getTempResult() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(TEMP_RESULT, _sharedPrefs.getString(TEMP_RESULT, null));
        return init;
    }

    /*DATA VERSION*/
    public HashMap<String, String> getDataVesion() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(DATA_VERSION, _sharedPrefs.getString(DATA_VERSION, null));
        return init;
    }

    /*Return State*/
    public HashMap<String, String> getState() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(STATE, _sharedPrefs.getString(STATE, null));
        return init;
    }

    /*Return Username*/
    public HashMap<String, String> getUsername() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USERNAME, _sharedPrefs.getString(USERNAME, null));
        return init;
    }

    /*Return UserInfo*/
    public HashMap<String, String> getUserInfo() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_INFO, _sharedPrefs.getString(USER_INFO, null));
        return init;
    }

    /*Return Login Status*/
    public HashMap<String, String> getLoginStatus() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(ISLOGIN, _sharedPrefs.getString(ISLOGIN, null));
        return init;
    }

    /*Return Selected Value*/
    public HashMap<String, String> getSelectedPopupSelection() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(SELECTED, _sharedPrefs.getString(SELECTED, null));
        return init;
    }

    /*Return Country Value*/
    public HashMap<String, String> getCountry() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(COUNTRY, _sharedPrefs.getString(COUNTRY, null));
        return init;
    }

    /*Return User_info*/
    public HashMap<String, String> getUserEmail() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_EMAIL, _sharedPrefs.getString(USER_EMAIL, null));
        return init;
    }

    /*Return User_info*/
    public HashMap<String, String> getAuth() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_AUTH, _sharedPrefs.getString(USER_AUTH, null));
        return init;
    }

    /*Return User_info
    public HashMap<String, String> getSignature() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(USER_SIGNATURE, _sharedPrefs.getString(USER_SIGNATURE, null));
        return init;
    }*/

    /*Return User_info*/
    public HashMap<String, String> getUserPassword() {
        HashMap<String, String> init = new HashMap<String, String>();
        init.put(PASSWORD, _sharedPrefs.getString(PASSWORD, null));
        return init;
    }

    /*Set Booking ID*/
    public void setAppVersion(String version) {
        _prefsEditor.putString(APP_VERSION, version);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setUserID(String id) {
        _prefsEditor.putString(USER_ID, id);
        _prefsEditor.apply();
    }

    /*Set SEAT*/
    public void setTempResult(String tempResult) {
        _prefsEditor.putString(TEMP_RESULT, tempResult);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setDataVersion(String version) {
        _prefsEditor.putString(DATA_VERSION, version);
        _prefsEditor.apply();
    }

    /*Set Booking ID*/
    public void setUserPassword(String password) {
        _prefsEditor.putString(PASSWORD, password);
        _prefsEditor.apply();
    }

    /*Set Username Value*/
    public void setUsername(String url) {
        _prefsEditor.putString(USERNAME, url);
        _prefsEditor.apply();
    }

    /*Set STATE value*/
    public void setState(String url) {
        _prefsEditor.putString(STATE, url);
        _prefsEditor.apply();
    }


    /*Set Username Value*/
    public void setUserInfo(String url) {
        _prefsEditor.putString(USER_INFO, url);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setLoginStatus(String status) {
        _prefsEditor.putString(ISLOGIN, status);
        _prefsEditor.apply();
    }

    /*Set Signature Value*/
    public void setRegisterStatus(String status) {
        _prefsEditor.putString(ISREGISTER, status);
        _prefsEditor.apply();
    }

    /*Set Airport Value*/
    public void setSelectedPopupSelection(String signature) {
        _prefsEditor.putString(SELECTED, signature);
        _prefsEditor.apply();
    }

    /*Set Airport Value*/
    public void setCountry(String country) {
        _prefsEditor.putString(COUNTRY, country);
        _prefsEditor.apply();
    }


    /*Set Userinfo Value*/
    public void setUserEmail(String url) {
        _prefsEditor.putString(USER_EMAIL, url);
        _prefsEditor.apply();
    }

    public void setUserAuth(String url) {
        _prefsEditor.putString(USER_AUTH, url);
        _prefsEditor.apply();
    }

    /*public void setUserSignature(String url) {
        _prefsEditor.putString(USER_SIGNATURE, url);
        _prefsEditor.apply();
    }*/

    /*Clear Checkin Value*/
    public void removeUserID() {
        // Clearing All URL
        _sharedPrefs.edit().remove(USER_ID).apply();

    }

    /*Clear Checkin Value*/
    public void clearPassword() {
        // Clearing All URL
        _sharedPrefs.edit().remove(PASSWORD).apply();

    }

    /*Clear Signature Value*/
    public void clearDataVersion() {
        // Clearing All URL
        _sharedPrefs.edit().remove(DATA_VERSION).apply();

    }


    /*Clear Login Status*/
    public void clearLoginStatus() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(ISLOGIN).apply();
    }

    /*Clear Signature Value*/
    public void setUsername() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(USERNAME).apply();
    }

    public void setUserInfo() {
        // Clearing Siganture
        _sharedPrefs.edit().remove(USER_INFO).apply();
    }

    /*Clear Selected Value*/
    public void clearSelectedPopupSelection() {
        // Clearing Selected
        _sharedPrefs.edit().remove(SELECTED).apply();
    }


    /*Clear State Value*/
    public void clearState() {
        // Clearing Selected
        _sharedPrefs.edit().remove(STATE).apply();
    }

    /*Clear State Value*/
    public void clearAppVersion() {
        // Clearing Selected
        _sharedPrefs.edit().remove(APP_VERSION).apply();
    }

    /*Clear Country Value*/
    public void clearCoutnry() {
        // Clearing Selected
        _sharedPrefs.edit().remove(COUNTRY).apply();
    }


    /*Clear UserInfo Value*/
    public void clearUserEmail() {
        // Clearing Selected
        _sharedPrefs.edit().remove(USER_EMAIL).apply();
    }

    /*Clear UserInfo Value*/
    public void clearTempResult() {
        // Clearing Selected
        _sharedPrefs.edit().remove(TEMP_RESULT).apply();
        Log.e("Clear", "True");
    }
}