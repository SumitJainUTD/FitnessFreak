package com.example.fitnessfreak;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
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

	public void updateUserProfileData(String field, String value) {
		ff.updateProfileRow(field, value);
	}

	public void ShowAlertDialog(String strTitle, final String section, final TextView tv) {
		View AlertView;
		Log.i("dialog displayed" , "displayed");
		LayoutInflater li = LayoutInflater.from(cxt);
		AlertView = li.inflate(R.layout.alert_xml_edit, null);
//		if (section.equalsIgnoreCase("Height")) {
//			AlertView = li.inflate(R.layout.alert_xml_edit, null);
//		}
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
						Settings s = new Settings();
						s.reload();
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
