package health.fitnessfreak;

import com.exaple.fitnessfreak.R;

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
import android.widget.Toast;
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
		
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date date7 = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
		Date date1 = new Date(System.currentTimeMillis() - (1 * DAY_IN_MS));
		Date date30 = new Date(System.currentTimeMillis() - (30 * DAY_IN_MS));
		Date date100 = new Date(System.currentTimeMillis() - (100 * DAY_IN_MS));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date now  = new Date();
		strQuery = "Select * from (select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, 'Yesterday' as walkDate from walk where walkDate = '" + sdf.format(date1) + "' UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '7 Days' as walkDate from walk where walkDate between '" + sdf.format(date7) + "' and '" + sdf.format(now) + "')UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '30 Days' as walkDate from walk where walkDate between '" + sdf.format(date30) + "' and '" + sdf.format(now) + "' UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '100 Days' as walkDate from walk where walkDate between '" + sdf.format(date100) + "' and '" + sdf.format(now) + "' order by walkDate DESC";
		Cursor csr = cf.getData(strQuery);
		Log.i("com.example.FF", "Cursor returned for records"); //
		rcrdAdapter = new RecordsAdapter(this, csr);
		rlistview.setAdapter(rcrdAdapter);
	}

	public void Split(View v) {
		// strQuery = "Select * from Walk";
		strQuery = "Select * from Walk order by walkDate DESC ";
		Cursor csr = cf.getData(strQuery);
		Log.i("com.example.FF", "Cursor returned for records"); //
		rcrdAdapter = new RecordsAdapter(this, csr);
		rlistview.setAdapter(rcrdAdapter);
	}

	public void Combine(View v) {
		long DAY_IN_MS = 1000 * 60 * 60 * 24;
		Date date7 = new Date(System.currentTimeMillis() - (7 * DAY_IN_MS));
		Date date1 = new Date(System.currentTimeMillis() - (1 * DAY_IN_MS));
		Date date30 = new Date(System.currentTimeMillis() - (30 * DAY_IN_MS));
		Date date180 = new Date(System.currentTimeMillis() - (180 * DAY_IN_MS));
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		Date now  = new Date();
		strQuery = "Select * from (select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, 'Yesterday' as walkDate from walk where walkDate = '" + sdf.format(date1) + "' UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '7 Days' as walkDate from walk where walkDate between '" + sdf.format(date7) + "' and '" + sdf.format(now) + "')UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '30 Days' as walkDate from walk where walkDate between '" + sdf.format(date30) + "' and '" + sdf.format(now) + "' UNION select _id, Sum(Steps) as Steps, Sum(distance) as distance, Sum(calories) as calories, '180 Days' as walkDate from walk where walkDate between '" + sdf.format(date180) + "' and '" + sdf.format(now) + "' order by walkDate DESC";
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
