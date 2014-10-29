package com.example.fitnessfreak;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class CommonFunctions extends Main {
	public String DBName;
	public ListView cfListView;
	public Context cxt;
	public FFOpenHelper ff;
	public Settings setting;

	public CommonFunctions() {

	}

	public CommonFunctions(Context cxt) {
		this.cxt = cxt;
		DBName = "FF02.db";
		ff = new FFOpenHelper(cxt, DBName, null, 1);
	}

	public int getRecordsCount(String strQuery) {
		int intTemp = 0;
		Cursor csr = ff.executeQuery(strQuery);
		if (csr.moveToFirst()) {
			intTemp = csr.getInt(csr.getColumnIndex("Count"));
		}
		csr.close();
		return intTemp;
	}

	public void addDefaultUserData(String subject, String subjectValue) {
		ff.addProfileData(subject, subjectValue);
	}

	public Cursor getData(String strQuery) {
		Cursor csr = ff.executeQuery(strQuery);
		return csr;
	}

	public void updateUserProfileData(String section, String value) {
		int sectionID =1;
		if (section.equalsIgnoreCase("Height")) {
			sectionID = 3;
		}
		if (section.equalsIgnoreCase("Weight")) {			
			sectionID = 4;
		}
		if (section.equalsIgnoreCase("Age")) {
			sectionID = 1;
		}
		if (section.equalsIgnoreCase("Sex")) {
			sectionID = 2;
		}
		ff.updateProfileRow(sectionID, value);
	}

	public void ShowAlertDialog(String strTitle, final String section) {
		View AlertView;
		
		Log.i("dialog displayed" , "displayed");
		LayoutInflater li = LayoutInflater.from(cxt);
		AlertView = li.inflate(R.layout.alert_xml_edit_double, null);
		if (section.equalsIgnoreCase("Height")) {
			AlertView = li.inflate(R.layout.alert_xml_edit_double, null);
		}
		if (section.equalsIgnoreCase("Weight")) {
			AlertView = li.inflate(R.layout.alert_xml_edit_double, null);
		}
		if (section.equalsIgnoreCase("Age")) {
			AlertView = li.inflate(R.layout.alert_xml_edit_number, null);
		}
		if (section.equalsIgnoreCase("Sex")) {
			AlertView = li.inflate(R.layout.alert_xml_edit_double, null);
		}
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cxt);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(AlertView);
		alertDialogBuilder.setTitle(strTitle);

		final EditText userInput = (EditText) AlertView
				.findViewById(R.id.alertSubject);
		
		//Log.i("Entered Value" , inputText);
		// set dialog message
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						final String inputText = userInput.getText().toString();
						Log.i("user input" , inputText);
						updateUserProfileData(section, inputText);
						Log.i("DB Updated", "DB Updated");
						Cursor c = getData("Select * from Profile");
						Settings.listview.setAdapter(new SettingAdapter(cxt, c));
					}
				});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}
	public void ShowAlertDialogSpinner(String strTitle, final String section) {
		View AlertView;
		
		Log.i("dialog displayed" , "displayed");
		LayoutInflater li = LayoutInflater.from(cxt);
		AlertView = li.inflate(R.layout.alert_xml_spinner, null);
//		String [] sex = {"Female", "Male"};
//		Spinner s1 = (Spinner)findViewById(R.id.genderSpinner);
//		ArrayAdapter<String> adptr = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sex);
//		adptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		s1.setAdapter(adptr);
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cxt);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(AlertView);
		alertDialogBuilder.setTitle(strTitle);
		
		final Spinner spn = (Spinner) AlertView
				.findViewById(R.id.genderSpinner);
		
		
		
		//Log.i("Entered Value" , inputText);
		// set dialog message
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						final String inputText = spn.getSelectedItem().toString();
						Log.i("user input" , inputText);
						updateUserProfileData(section, inputText);
						Log.i("DB Updated", "DB Updated");
						Cursor c = getData("Select * from Profile");
						Settings.listview.setAdapter(new SettingAdapter(cxt, c));
					}
				});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
		
	}
	public void ShowAlertDialogNumbers(String strTitle, final String section) {
		View AlertView;
		
		Log.i("dialog displayed" , "displayed");
		LayoutInflater li = LayoutInflater.from(cxt);
		AlertView = li.inflate(R.layout.alert_xml_edit_number, null);		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(cxt);

		// set prompts.xml to alertdialog builder
		alertDialogBuilder.setView(AlertView);
		alertDialogBuilder.setTitle(strTitle);

		final EditText userInput = (EditText) AlertView
				.findViewById(R.id.alertSubjectNumber);
		
		//Log.i("Entered Value" , inputText);
		// set dialog message
		alertDialogBuilder.setCancelable(false);
		alertDialogBuilder.setPositiveButton("OK",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						final String inputText = userInput.getText().toString();
						Log.i("user input" , inputText);
						updateUserProfileData(section, inputText);
						Log.i("DB Updated", "DB Updated");
						Cursor c = getData("Select * from Profile");
						Settings.listview.setAdapter(new SettingAdapter(cxt, c));
					}
				});
		alertDialogBuilder.setNegativeButton("Cancel",
				new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int id) {
						dialog.cancel();
					}
				});

		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();

		// show it
		alertDialog.show();
	}

}
