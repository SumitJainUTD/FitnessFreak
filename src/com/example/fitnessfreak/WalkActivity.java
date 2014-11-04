package com.example.fitnessfreak;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class WalkActivity extends Activity implements SensorEventListener {
	SensorManager sensormgr;
	Sensor mAccelerometer;
	Sensor mGyroscopre;
	Sensor mStepDetector;
	TextView steps;
	TextView distance;
	TextView Cals;
	Button start;
	int stepCount;
	int changes;
	int EMACount;
	public static double EMAvector = 0.0;
	boolean stepDetectorSensor = false;
	public static boolean flag = true;
	public CommonFunctions cf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walk);
		cf = new CommonFunctions(this); // intialize Common Function Object
		steps = (TextView) findViewById(R.id.steps);
		distance = (TextView) findViewById(R.id.distanceC);
		Cals = (TextView) findViewById(R.id.caloriesC);
//		Date dte = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//		String strCountQuery = "Select Count(*) as Count from walk where walkDate = strftime('%m/%d/%Y',date('now'))";
//		int x = cf.getRecordsCount(strCountQuery);
//		if (x < 1) {
//			steps.setText("0");
//			distance.setText("0 km");
//			Cals.setText("0 kCals");
//		}

		// cf.addStep(sdf.format(dte));
		
		Date dte = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		
		//Toast.makeText(this, sdf.format(dte),Toast.LENGTH_LONG).show();

		sensormgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// mAccelerometer =
		//
		// mGyroscopre = sensormgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		// mRotation = sensormgr.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		mStepDetector = sensormgr.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		if (mStepDetector == null) {
		//	Toast.makeText(this, "Step sensor not available!",Toast.LENGTH_LONG).show();
			mAccelerometer = sensormgr
					.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		} else {
			stepDetectorSensor = true;
		}

		start = (Button) findViewById(R.id.start);
		start.setText("Stop");
		 updateWalkData(); 
	}

	public void startSensor() {
		if (stepDetectorSensor) {
			sensormgr.registerListener(this, mStepDetector,
					SensorManager.SENSOR_DELAY_NORMAL);
		} else {
			sensormgr.registerListener(this, mAccelerometer,
					SensorManager.SENSOR_DELAY_NORMAL);
		}

	}

	public void startCounting(View v) {
		if (start.getText().equals("Start")) {
			startSensor();
			start.setText("Stop");
		} else if (start.getText().equals("Stop")) {
			stopSensor();
			start.setText("Start");
		}
	}

	public void stopSensor() {
		if (stepDetectorSensor) {
			sensormgr.unregisterListener(this, mStepDetector);
		} else {
			sensormgr.unregisterListener(this, mAccelerometer);
		}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub

		if (stepDetectorSensor) {
			stepCount++;
			// steps.setText(String.valueOf(stepCount));
			Date dte = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			cf.addStep(sdf.format(dte));
			updateWalkData();

		} else {
			double x = event.values[0];
			double y = event.values[1];
			double z = event.values[2];
			changes++;
			if (changes > 10000) {
				changes = 8000;
			}
			double vector = Math.sqrt(((x * x) + (y * y) + (z * z)));
			if (EMACount < 1000) {
				EMA(vector);
				Log.i("EMA COUNT", String.valueOf(EMACount));
			}
			if(EMACount>1100){
				EMACount = 1001;
			}
			Log.i("EMAAAAA", String.valueOf(EMACount));
			if (vector > EMAvector +0.07 && flag) {
				stepCount = stepCount + 1;
				EMACount++;
				// if(stepCount==5 && thresholdFound ==false){
				// thresholdFound=true;
				// stepCount = 0;
				// steps.setText("...");
				// }

				flag = false;
			}
			if (vector < EMAvector - 0.07 && flag == false) {
				// stepCount = stepCount + 1;
				// if (stepCount > 0) {
				// steps.setText(String.valueOf(stepCount));
				// }
				Log.i("StepCount" , String.valueOf(stepCount));
				if (stepCount > 0) {
					Date dte = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					cf.addStep(sdf.format(dte));
					// steps.setText(String.valueOf(stepCount));
					updateWalkData();
				}

				flag = true;
			}

		}

	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public void EMA(double vctr) {
		if (EMAvector == 0) {
			EMAvector = vctr;
		} else {
			double k = ((double) 2) / (changes + 1);
			// Log.i("k",String.valueOf(k));
			// steps.setText(String.valueOf(EMAvector));
			EMAvector = vctr * k + (1 - k) * EMAvector;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
//		steps = (TextView) findViewById(R.id.steps);
//		distance = (TextView) findViewById(R.id.distanceC);
//		Cals = (TextView) findViewById(R.id.caloriesC);
//		Date dte = new Date();
//		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//		String strCountQuery = "Select Count(*) as Count from walk where walkDate = strftime('%m/%d/%Y',date('now'))";
//		int x = cf.getRecordsCount(strCountQuery);
//		if (x < 1) {
//			steps.setText("0");
//			distance.setText("0 km");
//			steps.setText("0 kCals");
//		}
//		if (start.getText().equals("Stop")) {
//			startSensor();
//		} else if (start.getText().equals("Start")) {
//			stopSensor();
//		}
		updateWalkData(); 
		startSensor();
		//Toast.makeText(this, "resumed!",Toast.LENGTH_LONG).show();

	}
	@Override
	public void onBackPressed()
	{
	     // code here to show dialog
	     super.onBackPressed();  // optional depending on your needs
	     
	}

	public void updateWalkData() {
		
		String strQuery = "Select * from walk where walkDate = strftime('%m/%d/%Y',date('now'))";
//		Cursor csr = cf.getData(strQuery);
		Date dte = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
		 Cursor csr = cf.getData("Select * from Walk where walkDate = '" + sdf.format(dte) + "'");
		Log.i("Cursor Returned", "XXXXXXXXXXXXXXXXXXXXXXYYYYYYYYYYYY");
		
		int x = cf.getRecordsCount("select Count(*) as Count from walk where walkDate = '" + sdf.format(dte) + "'");
		Log.i("Date Count ", String.valueOf(x));
		
		if (csr.moveToFirst()) {
			do {
				double dis = (csr.getDouble(2));
				double cal = (csr.getDouble(3));
				dis = Math.round(dis * 100);
				dis = dis / 100;
				cal = Math.round(cal * 100);
				cal = cal / 100;
				// double dis = Math.round(csr.getDouble(2));
				// double cal = Math.round(csr.getDouble(3));
				steps.setText(String.valueOf(csr.getInt(1)));
				distance.setText(String.valueOf(dis) + " kms");
				Cals.setText(String.valueOf(cal) + " kCals");
				Log.i("com.example.ID", String.valueOf(csr.getInt(0)));
				Log.i("com.example.Step", String.valueOf(csr.getInt(1)));
				Log.i("com.example.DIs", String.valueOf(csr.getDouble(2)));
				Log.i("com.example.Cal", String.valueOf(csr.getDouble(3)));
				Log.i("com.example.Date", String.valueOf(csr.getString(4)));
			} while (csr.moveToNext());
			csr.close();
		}
	}
}
