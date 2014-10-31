package com.example.fitnessfreak;

import java.text.SimpleDateFormat;
import java.util.Date;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.TabHost.TabSpec;

public class RecordsActivity extends Activity {
	public CommonFunctions cf;
	TextView totalSteps;
	public String strQuery;
	public RecordsAdapter rcrdAdapter;
	public static TabHost tabHostRecord;
	public ListView rlistview;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.records);
		cf = new CommonFunctions(this); // intialize Common Function Object
		rlistview = (ListView) findViewById(R.id.recordslist);
		update();
	}

	public void update() {
		strQuery = "Select * from (select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, 'Yesterday' as walkDate from walk where walkDate = strftime('%m/%d/%Y',date('now','-1 days')) UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '7 Days' as walkDate from walk where walkDate between strftime('%m/%d/%Y',date('now','-6 days')) and strftime('%m/%d/%Y',date('now')))UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '30 Days' as walkDate from walk where walkDate between strftime('%m/%d/%Y',date('now','-30 days')) and strftime('%m/%d/%Y',date('now'))UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '180 Days' as walkDate from walk where walkDate between strftime('%m/%d/%Y',date('now','-180 days')) and strftime('%m/%d/%Y',date('now')) order by walkDate DESC";
		Cursor csr = cf.getData(strQuery);
		Log.i("com.example.FF", "Cursor returned for records"); //
		rcrdAdapter = new RecordsAdapter(this, csr);
		rlistview.setAdapter(rcrdAdapter);
	}

	public void Split(View v) {
		// strQuery = "Select * from Walk";
		strQuery = "Select * from Walk";
		Cursor csr = cf.getData(strQuery);
		Log.i("com.example.FF", "Cursor returned for records"); //
		rcrdAdapter = new RecordsAdapter(this, csr);
		rlistview.setAdapter(rcrdAdapter);
	}

	public void Combine(View v) {
		strQuery = "Select * from (select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, 'Yesterday' as walkDate from walk where walkDate = strftime('%m/%d/%Y',date('now','-1 days')) UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '7 Days' as walkDate from walk where walkDate between strftime('%m/%d/%Y',date('now','-6 days')) and strftime('%m/%d/%Y',date('now')))UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '30 Days' as walkDate from walk where walkDate between strftime('%m/%d/%Y',date('now','-30 days')) and strftime('%m/%d/%Y',date('now'))UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '180 Days' as walkDate from walk where walkDate between strftime('%m/%d/%Y',date('now','-180 days')) and strftime('%m/%d/%Y',date('now')) order by walkDate DESC";
		Cursor csr = cf.getData(strQuery);
		Log.i("com.example.FF", "Cursor returned for records"); //
		rcrdAdapter = new RecordsAdapter(this, csr);
		rlistview.setAdapter(rcrdAdapter);
	}

	@Override
	protected void onResume() {
		super.onResume();
		update();
	}
}
