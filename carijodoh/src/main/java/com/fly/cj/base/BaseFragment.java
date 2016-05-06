package com.fly.cj.base;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.fly.cj.Controller;
import com.fly.cj.MainFragmentActivity;
import com.fly.cj.R;
import com.fly.cj.ui.object.Country;
import com.fly.cj.utils.DropDownItem;
import com.fly.cj.utils.DropMenuAdapter;
import com.fly.cj.utils.SharedPrefManager;
import com.fly.cj.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.keyboardsurfer.android.widget.crouton.Configuration;
import de.keyboardsurfer.android.widget.crouton.Crouton;
import de.keyboardsurfer.android.widget.crouton.Style;
import dmax.dialog.SpotsDialog;


public class BaseFragment extends Fragment {

	public com.fly.cj.base.AQuery aq;
	private SharedPreferences pref;
	private int indexForState = -1;
	private String selected;
	private static SharedPrefManager prefManager;
	private static Country obj = new Country();
	private static SpotsDialog mProgressDialog;
	private static SweetAlertDialog pDialog;
	private static Dialog dialog;


	private static Boolean status;
	Boolean manualValidationStatus = true;
	private static int staticIndex = -1;

	public void blinkText(TextView txt){

		//try set blinking textview
		Animation anim = new AlphaAnimation(0.0f, 1.0f);
		anim.setDuration(80); //You can manage the blinking time with this parameter
		anim.setStartOffset(20);
		anim.setRepeatMode(Animation.REVERSE);
		anim.setRepeatCount(Animation.INFINITE);
		txt.startAnimation(anim);

	}

	/*public String getDialingCode(String coutryCode,Activity act){

		String dialingCode = null;

		JSONArray jsonCountry = getCountry(act);
		for(int x = 0 ; x < jsonCountry.length() ; x++) {

			JSONObject row = (JSONObject) jsonCountry.opt(x);
			if(coutryCode.equals(row.optString("country_code"))) {
				dialingCode = row.optString("dialing_code");
			}
		}

		return dialingCode;
	}*/

	public boolean validateDialingCode(String dialingCode, String mobilePhone){

		boolean status = false;

		String twoChar  = mobilePhone.substring(0,2);
		Log.e("two",twoChar);
		if(!dialingCode.equals(twoChar)){
			status = true;
		}
		return status;
	}
	public boolean timeCompare(String arrivalTime,String returnDepartureTime){

		boolean status = false;

		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

		Date arrival = null;
		Date departureReturn = null;

		try {
			arrival = parseFormat.parse(arrivalTime);
			departureReturn = parseFormat.parse(returnDepartureTime);
		}catch (Exception e){
		}
		Log.e("xxxxxxxxxxxxxxx", Long.toString(departureReturn.getTime()));
		Log.e("yyyyyyyyyyyyyy", Long.toString(arrival.getTime()));

		if(arrival.getTime() > departureReturn.getTime() ){
			status = true;
		}

		return status;
	}

	public boolean compare90Minute(String arrivalTime,String returnDepartureTime){

		boolean status = false;

		SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
		SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

		Date arrival = null;
		Date departureReturn = null;

		try {
			arrival = parseFormat.parse(arrivalTime);
			departureReturn = parseFormat.parse(returnDepartureTime);
		}catch (Exception e){
		}

		long count90minute = arrival.getTime() - departureReturn.getTime();

		Log.e("abc", Long.toString(TimeUnit.MILLISECONDS.toMinutes(count90minute)));

		if(Math.abs(TimeUnit.MILLISECONDS.toMinutes(count90minute)) < 90 ){
			status = true;
		}

		return status;
	}

	public boolean travellerAgeValidation(ArrayList<Integer> ageOfTraveller){

		boolean lessThan12 = true;
		//checkAgeOfTraveller
		for(int y = 0 ; y < ageOfTraveller.size() ; y++){
			if(ageOfTraveller.get(y) > 12){
				lessThan12 = false;
			}
		}
		return lessThan12;

	}

	public int travellerAge(String dob){

		int age;

		String[] splitDOB = dob.split("/");
		String dobYear = splitDOB[2];

		boolean status = false;

		Calendar now = Calendar.getInstance();   // Gets the current date and time
		int currentYear = now.get(Calendar.YEAR);

		age = currentYear - Integer.parseInt(dobYear);

		return age;
	}

	/* ------------------ Mobile Check-In ------------------- */
	public static ArrayList<DropDownItem> getTravelDoc(Context context) {

		ArrayList<DropDownItem> travelDocList = new ArrayList<DropDownItem>();

		/*Travel Doc*/
		final String[] doc = context.getResources().getStringArray(R.array.travel_doc);
		for (int i = 0; i < doc.length; i++) {
			String travelDoc = doc[i];
			String[] splitDoc = travelDoc.split("-");
			DropDownItem itemDoc = new DropDownItem();
			itemDoc.setText(splitDoc[0]);
			itemDoc.setCode(splitDoc[1]);
			travelDocList.add(itemDoc);
		}

		return travelDocList;
	}

/*
	public static ArrayList<DropDownItem> getStaticCountry(Activity act){

		ArrayList<DropDownItem> countrys = new ArrayList<DropDownItem>();
		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getCountry();
		String dataCountry = init.get(SharedPrefManager.COUNTRY);
		Log.e("dataCountry",dataCountry);

		try {
			json = new JSONArray(dataCountry);
			Log.e("json",Integer.toString(json.length()));
		}catch (JSONException e){
			e.printStackTrace();
		}

		for (int i = 0; i < json.length(); i++)
		{
			JSONObject row = (JSONObject) json.opt(i);

			DropDownItem itemCountry = new DropDownItem();
			itemCountry.setText(row.optString("country_name"));
			itemCountry.setCode(row.optString("country_code") + "/" + row.optString("dialing_code"));
			itemCountry.setTag("Country");
			countrys.add(itemCountry);
		}

		return countrys;
	}*/

    /* ---------------------------PROFILE----------------------------- */

	public static ArrayList<DropDownItem> getReligion(Activity act) {

		/*Travelling Purpose*/
		ArrayList<DropDownItem> religionList = new ArrayList<DropDownItem>();

		final String[] religion = act.getResources().getStringArray(R.array.religion);
		for (int i = 0; i < religion.length; i++) {
			DropDownItem itemReligion = new DropDownItem();
            itemReligion.setText(religion[i]);
            religionList.add(itemReligion);
		}

		return religionList;
	}

    public static ArrayList<DropDownItem> getSmoker(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> smokerList = new ArrayList<DropDownItem>();

        final String[] smoker = act.getResources().getStringArray(R.array.smoker);
        for (int i = 0; i < smoker.length; i++) {
            DropDownItem itemSmoker = new DropDownItem();
            itemSmoker.setText(smoker[i]);
            smokerList.add(itemSmoker);
        }

        return smokerList;
    }

    public static ArrayList<DropDownItem> getState(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> stateList = new ArrayList<DropDownItem>();

        final String[] state = act.getResources().getStringArray(R.array.state);
        for (int i = 0; i < state.length; i++) {
            DropDownItem itemState = new DropDownItem();
            itemState.setText(state[i]);
            stateList.add(itemState);
        }

        return stateList;
    }

    public static ArrayList<DropDownItem> getMaritial(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> maritialList = new ArrayList<DropDownItem>();

        final String[] maritial = act.getResources().getStringArray(R.array.maritial_status);
        for (int i = 0; i < maritial.length; i++) {
            DropDownItem itemMaritial = new DropDownItem();
            itemMaritial.setText(maritial[i]);
            maritialList.add(itemMaritial);
        }

        return maritialList;
    }

    public static ArrayList<DropDownItem> getChild(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> childList = new ArrayList<DropDownItem>();

        final String[] child = act.getResources().getStringArray(R.array.children);
        for (int i = 0; i < child.length; i++) {
            DropDownItem itemChild = new DropDownItem();
            itemChild.setText(child[i]);
            childList.add(itemChild);
        }

        return childList;
    }

    public static ArrayList<DropDownItem> getRelation(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> relationList = new ArrayList<DropDownItem>();

        final String[] relation = act.getResources().getStringArray(R.array.relationship_status);
        for (int i = 0; i < relation.length; i++) {
            DropDownItem itemRelation = new DropDownItem();
            itemRelation.setText(relation[i]);
            relationList.add(itemRelation);
        }

        return relationList;
    }

    public static ArrayList<DropDownItem> getPolygamy(Activity act) {

		/*Travelling Purpose*/
        ArrayList<DropDownItem> polygamyList = new ArrayList<DropDownItem>();

        final String[] polygamy = act.getResources().getStringArray(R.array.polygamy);
        for (int i = 0; i < polygamy.length; i++) {
            DropDownItem itemPolygamy = new DropDownItem();
            itemPolygamy.setText(polygamy[i]);
            polygamyList.add(itemPolygamy);
        }

        return polygamyList;
    }

	public void setShake(View view) {
		Animation shake = AnimationUtils.loadAnimation(getActivity(), R.anim.shake);
		view.startAnimation(shake);
	}

	public String getFlightPurpose(String travel_purpose){

		String purpose;

		if(travel_purpose.equals("1")){
			purpose = "Leisure";
		}else{
			purpose = "Business";
		}

		return purpose;
	}

	public static String getTravelDocument(Activity act,String docType){

		String documentType = "";

		/*Travel Doc*/
		final String[] doc = act.getResources().getStringArray(R.array.travel_doc);
		for(int i = 0;i<doc.length; i++)
		{
			String travelDoc = doc[i];
			String[] splitDoc = travelDoc.split("-");


			if(docType.equals(splitDoc[1])){
				documentType = splitDoc[0];
			}
		}

		return documentType;
	}

	public static void setSuccessDialog(final Activity act,String msg,final Class<?> cls,String message) {

		if (cls == null) {
			new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
					.setTitleText(message)
					.setContentText(msg)
					.show();
		} else {
			new SweetAlertDialog(act, SweetAlertDialog.SUCCESS_TYPE)
					.setTitleText(message)
					.setContentText(msg)
					.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
						@Override
						public void onClick(SweetAlertDialog sDialog) {
							Intent explicitIntent = new Intent(act, cls);
							explicitIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
							explicitIntent.putExtra("AlertDialog", "Y");
							act.startActivity(explicitIntent);
							act.finish();

						}
					})
					.show();
		}
	}

	public static void setAlertDialog(Activity act,String msg,String title){

		if(act != null){
			new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
					.setTitleText(title)
					.setContentText(msg)
					.show();
		}
	}



	public static void setNormalDialog(Context act,String msg,String title){
		new SweetAlertDialog(act, SweetAlertDialog.NORMAL_TYPE)
				.setTitleText(title)
				.setContentText(msg)
				.show();
	}
	public static boolean setConfirmDialog(final Activity act){

		new SweetAlertDialog(act, SweetAlertDialog.WARNING_TYPE)
				.setTitleText("Are you sure want to update?")
				.setConfirmText("Confirm!")
				.setCancelText("Cancel!")
				.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog sDialog) {
						status = true;
						sDialog.dismiss();
					}
				})
				.showCancelButton(true)
				.setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
					@Override
					public void onClick(SweetAlertDialog sDialog) {
						status = false;
						sDialog.dismiss();
					}
				})
				.show();

		return status;
	}

	public ArrayList<DropDownItem> getListOfYear(Activity act){
		int totalMonth = 12;
		String monthToDisplay;
		ArrayList<DropDownItem> yearList = new ArrayList<DropDownItem>();

		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);


		for(int yearX =  year ; yearX < year+15 ; yearX++){

			DropDownItem itemYear = new DropDownItem();
			itemYear.setText(Integer.toString(yearX));
			itemYear.setCode(Integer.toString(yearX));
			itemYear.setTag("Year");
			yearList.add(itemYear);
		}


		return yearList;

	}

	public ArrayList<DropDownItem> getListOfMonth(Activity act){
		int totalMonth = 12;
		String monthToDisplay;
		ArrayList<DropDownItem> monthList = new ArrayList<DropDownItem>();

		for(int month =  1 ; month < totalMonth+1 ; month++){
			if(month < 10){
				 monthToDisplay = "0"+Integer.toString(month);
			}else{
				monthToDisplay = Integer.toString(month);
			}

			DropDownItem itemTitle = new DropDownItem();
			itemTitle.setText(monthToDisplay);
			itemTitle.setCode(monthToDisplay);
			itemTitle.setTag("Month");
			monthList.add(itemTitle);
		}


		return monthList;

	}

	public static void croutonAlert(Activity act,String msg){
		Crouton.makeText(act, msg, Style.ALERT)
				.setConfiguration(new Configuration.Builder()
						.setDuration(Configuration.DURATION_LONG).build())
				.show();
	}

	public String getMonthInInteger(String monthAlphabet){
		Log.e("MONTH", monthAlphabet);
		int intMonthNo = 0;
		String stringMonthNo = null;
		/*Month*/
		final String[] month = getResources().getStringArray(R.array.month);
		for(int i = 0;i<month.length; i++)
		{
			if(monthAlphabet.equals(month[i])){
				intMonthNo = i+1;
			}
		}

		if(intMonthNo < 10){
			stringMonthNo = "0"+Integer.toString(intMonthNo);
		}else{
			stringMonthNo = Integer.toString(intMonthNo);
		}
		return stringMonthNo;
	}

	public static void initiateLoading(Activity act){

		//if(dialog.isShowing()) {
		//	Log.e("Currenlt","showing");
		//}

		try {
			dialog.dismiss();
		}catch (Exception e){
		}

		dialog = new Dialog(act,R.style.DialogTheme);

		LayoutInflater li = LayoutInflater.from(act);
		final View myView = li.inflate(R.layout.loading_screen, null);


			Log.e("Hello","OK");
			/*pDialog = new SweetAlertDialog(act, SweetAlertDialog.PROGRESS_TYPE);
			pDialog.getProgressHelper().setBarColor(Color.parseColor("#CCff6a4d"));
			pDialog.setTitleText("Loading");
			pDialog.setCustomImage(R.drawable.load);
			pDialog.setCancelable(false);
			pDialog.show();*/

			//ContextThemeWrapper wrapper = new ContextThemeWrapper(act, R.style.DialogTheme);
			//final LayoutInflater inflater = (LayoutInflater) wrapper.getSystemService(act.LAYOUT_INFLATER_SERVICE);


		AlertDialog.Builder builder = new AlertDialog.Builder(act);
		builder.setView(myView);




			dialog.setContentView(myView);
			dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#CCFFFFFF")));
			dialog.setCancelable(false);
			//dialog.show();




			/*Dialog dialog2 = new Dialog(getActivity(),R.style.DialogTheme);
			dialog2.setContentView(myView);
			dialog2.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
			dialog2.show();


				//LayoutInflater li = LayoutInflater.from(act);
				//final View myView = inflater.inflate(R.layout.loading_screen, null);

				AlertDialog.Builder builder = new AlertDialog.Builder(wrapper);
				builder.setView(myView);

				dialog = builder.create();
				//dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
			*/

			WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
			lp.copyFrom(dialog.getWindow().getAttributes());
			lp.width = WindowManager.LayoutParams.MATCH_PARENT;
			//lp.height = 570;
			dialog.getWindow().setAttributes(lp);
			dialog.show();

	}

	public static void dismissLoading(){

		try {
			if(dialog.isShowing()){
				dialog.dismiss();
			}
		}catch (Exception e){
		}
	}

	public void popupAlert(String message){
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setMessage(message)
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						// FIRE ZE MISSILES!
					}
				});

		// Create the AlertDialog object and return it
		builder.create();
		builder.show();
	}

	/*Global PoPup*/
	public void popupSelection(final ArrayList array,Activity act,final TextView txt,final Boolean tagStatus,View v){

		prefManager = new SharedPrefManager(act);
		Utils.hideKeyboard(getActivity(), v);
		Log.e("Popup Alert", "True");
		final ArrayList<DropDownItem> a = array;
		DropMenuAdapter dropState = new DropMenuAdapter(act);
		dropState.setItems(a);

		AlertDialog.Builder alertStateCode = new AlertDialog.Builder(act);

		alertStateCode.setSingleChoiceItems(dropState, indexForState, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				String selected = a.get(which).getText();
				String selectedCode = a.get(which).getCode();

				txt.setText(selected);
				if (tagStatus) {
					txt.setTag(selectedCode);
				}

				indexForState = which;

				dialog.dismiss();
			}
		});


		AlertDialog mDialog = alertStateCode.create();
		mDialog.show();

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(mDialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = 600;
		mDialog.getWindow().setAttributes(lp);
	}

	/*Global PoPup*/
	public void popupSelectionExtra(final ArrayList array,Activity act,final TextView txt,final Boolean tagStatus,final LinearLayout txt2,final String indicate,final LinearLayout country){

		prefManager = new SharedPrefManager(act);

		final ArrayList<DropDownItem> a = array;
		DropMenuAdapter dropState = new DropMenuAdapter(act);
		dropState.setItems(a);

		AlertDialog.Builder alertStateCode = new AlertDialog.Builder(act);

		alertStateCode.setSingleChoiceItems(dropState, indexForState, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {

				String selected = a.get(which).getText();
				String selectedCode = a.get(which).getCode();
				txt.setText(selected);
				if(selectedCode.equals(indicate)){
					txt2.setVisibility(View.VISIBLE);
					if(country != null){
						country.setVisibility(View.GONE);
					}

				}else{
					txt2.setVisibility(View.GONE);
					if(country != null){
						country.setVisibility(View.VISIBLE);
					}
				}
				if(tagStatus){
					txt.setTag(selectedCode);
					Log.e("PURPOSE TAG",selectedCode);
				}else{
					Log.e("PURPOSE TAG", "NOT SET");
				}

				indexForState = which;

				dialog.dismiss();
			}
		});

		//Utils.hideKeyboard(getActivity(), v);
		AlertDialog mDialog = alertStateCode.create();
		mDialog.show();

		WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
		lp.copyFrom(mDialog.getWindow().getAttributes());
		lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
		lp.height = 600;
		mDialog.getWindow().setAttributes(lp);
	}

	public String getSelectedPopupSelection(Activity act){
		HashMap<String, String> init = prefManager.getSelectedPopupSelection();
		String selectedValue = init.get(SharedPrefManager.SELECTED);
		return selectedValue;
	}



	public String getTitleCode(Activity act,String title,String data){

		String titleCode = null;
		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getTitle();
		String dataTitle = init.get(SharedPrefManager.TITLE);

		try {
			json = new JSONArray(dataTitle);
		}catch (JSONException e){
			e.printStackTrace();
		}

		for (int i = 0; i < json.length(); i++)
		{
			if(data.equals("code")){
				JSONObject row = (JSONObject) json.opt(i);
				Log.e(row.optString("title_name"),row.optString("title_code"));
				if(title.equals(row.optString("title_name"))){
					titleCode = row.optString("title_code");
				}
			}else{
				JSONObject row = (JSONObject) json.opt(i);
				if(title.equals(row.optString("title_code"))){
					titleCode = row.optString("title_name");
				}
			}

		}

		return titleCode;
	}


	public String getTravelDocCode(Activity act,String travelDocData){
		/*Travel Doc*/
		String travelDocCode = null;
		final String[] doc = getResources().getStringArray(R.array.travel_doc);
		for(int i = 0;i<doc.length; i++)
		{
			String travelDoc = doc[i];
			String[] splitDoc = travelDoc.split("-");

			if(travelDocData.equals(splitDoc[0])){
				travelDocCode = splitDoc[1];
			}
		}

		return travelDocCode;
	}

	public String getCountryCode(Activity act,String countryData){

		String countryCode = null;
		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getCountry();
		String dataCountry = init.get(SharedPrefManager.COUNTRY);

		try {
			json = new JSONArray(dataCountry);
		}catch (JSONException e){
			e.printStackTrace();
		}

		for (int i = 0; i < json.length(); i++)
		{
			JSONObject row = (JSONObject) json.opt(i);

			if(countryData.equals(row.optString("country_name"))){
				countryCode = row.optString("country_code");
			}
		}

		return countryCode;
	}

	public static String getCountryName(Activity act,String countryCode){

		String countryName = null;
		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getCountry();
		String dataCountry = init.get(SharedPrefManager.COUNTRY);

		try {
			json = new JSONArray(dataCountry);
		}catch (JSONException e){
			e.printStackTrace();
		}

		for (int i = 0; i < json.length(); i++)
		{
			JSONObject row = (JSONObject) json.opt(i);

			if(countryCode.equals(row.optString("country_code"))){
				countryName = row.optString("country_name");
			}
		}

		return countryName;
	}

	/*public JSONArray getState(Activity act){

		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getState();
		String dataState = init.get(SharedPrefManager.STATE);

		try {
			json = new JSONArray(dataState);
		}catch (JSONException e){
			e.printStackTrace();
		}

		return json;
	}*/

	public static String getUserInfoCached(Activity act){

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getUserInfo();
		String userInfo = init.get(SharedPrefManager.USER_INFO);

		return userInfo;
	}

	public static JSONArray getFlight(Activity act){

		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getFlight();
		String dataFlight = init.get(SharedPrefManager.FLIGHT);

		try {
			json = new JSONArray(dataFlight);
			Log.e("Flight Size",Integer.toString(json.length()));

		}catch (JSONException e){
			e.printStackTrace();
		}

		return json;
	}

	/*Return month in alphabet*/
	public static String getMonthAlphabet(int month) {
		return new DateFormatSymbols().getShortMonths()[month];
	}

	/*Get All Country From OS
	public JSONArray getCountry(Activity act)
	{
		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getCountry();
		String dataCountry = init.get(SharedPrefManager.COUNTRY);
		Log.e("dataCountry",dataCountry);

		try {
			json = new JSONArray(dataCountry);
			Log.e("json",Integer.toString(json.length()));
		}catch (JSONException e){
			e.printStackTrace();
		}

		return json;

		/*Locale[] locales = Locale.getAvailableLocales();
		ArrayList<String> countries = new ArrayList<String>();

		for (Locale locale : locales) {
			String country = locale.getDisplayCountry();
			String countryCode = locale.getCountry();


			if (country.trim().length()>0 && !countries.contains(country)) {
				countries.add(country+"-"+countryCode);
			}
		}

		Collections.sort(countries);
		return countries;
	}*/


	/*Get All User Info From OS*/
	public JSONObject getUserInfo(Activity act)
	{
		JSONObject json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getUserInfo();
		String userInfo = init.get(SharedPrefManager.USER_INFO);
		Log.e("UserInfo", userInfo);

		try {
			json = new JSONObject(userInfo);
			Log.e("json",Integer.toString(json.length()));
		}catch (JSONException e){
			e.printStackTrace();
		}

		return json;

	}

	public static String reformatDOB(String dob){
		String date;

		if(dob != ""){
			String string = dob;
			String[] parts = string.split("-");
			String year = parts[0];
			String month = parts[1];
			String day = parts[2];
			date = day+"/"+(month)+"/"+year;
		}else{
			date = "";
		}
		return date;
	}

	public String reformatDOB2(String dob){
		String date;
		String string = dob;
		String[] parts = string.split("/");
		String day = parts[0];
		String month = parts[1];
		String year = parts[2];
		date = year+"-"+month+"-"+day;

		return date;
	}

	/*Get All User Info From OS*/
	public JSONObject getCheckinInfo(Activity act)
	{
		JSONObject json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getCheckinInfo();
		String checkinInfo = init.get(SharedPrefManager.CHECKIN_INFO);


		try {
			json = new JSONObject(checkinInfo);

		}catch (JSONException e){
			e.printStackTrace();
		}

		return json;
	}


	/*public String getStateName(Activity act,String stateCode){

		String stateName = null;

		JSONArray jsonState = getState(act);
		for(int x = 0 ; x < jsonState.length() ; x++) {

			JSONObject row = (JSONObject) jsonState.opt(x);
			if(stateCode.equals(row.optString("state_code"))) {
				stateName = row.optString("state_name");
			}
		}


		return stateName;

	}*/


	public static void setAlertNotification(Activity act){

		if(Controller.connectionAvailable(act)){
			setAlertDialog(MainFragmentActivity.getContext(), "Unable to connect to server","Connection Error");
		}else{
			setAlertDialog(MainFragmentActivity.getContext(), "No Internet Connection","Connection Error");
		}
		dismissLoading();
	}

	/*Get From OS*/
	public JSONArray getTermInfo(Activity act) {
		JSONArray json = null;

		prefManager = new SharedPrefManager(act);
		HashMap<String, String> init = prefManager.getTermInfo();
		String dataTerm = init.get(SharedPrefManager.TERM_INFO);
		//Log.e("dataTerm", dataTerm);

		try {
			json = new JSONArray(dataTerm);
			//Log.e("json", Integer.toString(json.length()));
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return json;
	}
	/*public static void showConnectionError(String test, Activity activity)
	{
        if(activity != null) {
            try {
                TextView txtUTC = (TextView) activity.findViewById(R.id.txtUTC);
                txtUTC.setText(test);

                FrameLayout mainFrame = (FrameLayout) activity.findViewById(R.id.utc_container);
                mainFrame.setVisibility(View.VISIBLE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public static void baseInitiateLoading(Activity activity)
	{
        Log.e("Initiate Loading","TRUE");
		try
		{
			final FrameLayout mainFrame = (FrameLayout) activity.findViewById(R.id.container);
			mainFrame.setVisibility(View.VISIBLE);

			RelativeLayout mainFrameRelative = (RelativeLayout) activity.findViewById(R.id.mainFrameRelative);
			mainFrameRelative.setVisibility(View.VISIBLE);
			mainFrame.bringChildToFront(mainFrameRelative);
			mainFrame.invalidate();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void baseRemoveLoading(Activity activity)
	{
		try
		{
			RelativeLayout mainFrameRelative = (RelativeLayout) activity.findViewById(R.id.mainFrameRelative);
			mainFrameRelative.setVisibility(View.GONE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}*/

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		return super.onCreateView(inflater, container, savedInstanceState);
	}

	@Override
	public void onCreate(Bundle icicle)
	{
		super.onCreate(icicle);
		aq = new com.fly.cj.base.AQuery(getActivity());
		pref = PreferenceManager.getDefaultSharedPreferences(getActivity());
	}

	/*public void showUTCError(String msg)
	{
		Activity activity = getActivity();
		if (activity instanceof MainFragmentActivity)
		{
			MainFragmentActivity myactivity = (MainFragmentActivity) activity;
			myactivity.unableToConnectToServer(msg);
		}
	}*/

	public boolean isNetworkAvailable(Activity activity)
	{
		ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
		return activeNetworkInfo != null && activeNetworkInfo.isConnected();
	}

	public String checkResultCached(Activity act){

		HashMap<String, String> initResult = prefManager.getTempResult();
		String tempResult = initResult.get(SharedPrefManager.TEMP_RESULT);

		try {
			Log.e("tempResult",tempResult);
		}catch (Exception e){

		}

		return tempResult;
	}

	//Cached Result In GSON String
	public static void tempResult(String cachedResult){

		prefManager.setTempResult(cachedResult);

	}

	public void manualValidation(EditText editText,String validationRule){

		if(!editText.getText().toString().equals("")) {

			if(validationRule.equals("bonuslink")){
				if (editText.getText().toString().length() < 16 || editText.getText().toString().length() > 16) {
					editText.setError("Invalid bonuslink card number");
					manualValidationStatus = false;
					setShake(editText);
				}
			}else if(validationRule.equals("phoneNumber")){
				if (editText.getText().toString().length() < 6 || editText.getText().toString().length() > 16) {
					editText.setError("Invalid phone number");
					manualValidationStatus = false;
					setShake(editText);
				}
			}else if(validationRule.equals("faxNumber")){
				if (editText.getText().toString().length() < 6 || editText.getText().toString().length() > 16) {
					editText.setError("Invalid fax number");
					manualValidationStatus = false;
					setShake(editText);
				}
			}

		}
	}


	public Boolean getManualValidationStatus(){
		return manualValidationStatus;
	}

	public void resetManualValidationStatus(){
		manualValidationStatus = true;
	}

	public static void removeLogoHeader(Activity activity)
	{
		try
		{
			RelativeLayout mainFrameRelative = (RelativeLayout) activity.findViewById(R.id.mainLogoLayout);
			mainFrameRelative.setVisibility(View.GONE);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
