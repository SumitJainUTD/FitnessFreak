package com.example.fitnessfreak;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
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
	Button start;
	int stepCount;
	int changes;
	public static double EMAvector = 0.0;
	boolean stepDetectorSensor = false;
	public static boolean flag = true;
	public CommonFunctions cf;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.walk);
		cf = new CommonFunctions(this); // intialize Common Function Object
		sensormgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		// mAccelerometer =
		//
		// mGyroscopre = sensormgr.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		// mRotation = sensormgr.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
		mStepDetector = sensormgr.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		if (mStepDetector == null) {
			Toast.makeText(this, "Step sensor not available!",
					Toast.LENGTH_LONG).show();
			mAccelerometer = sensormgr.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		} else {
			stepDetectorSensor = true;
		}
		steps = (TextView) findViewById(R.id.steps);
		start = (Button) findViewById(R.id.start);
		start.setText("Start");
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
			steps.setText(String.valueOf(stepCount));
			Date dte = new Date(); 
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")  ;
			cf.addStep(sdf.format(dte));
		} else {
			double x = event.values[0];
			double y = event.values[1];
			double z = event.values[2];
			changes++;
			if (changes > 10000) {
				changes = 8000;
			}
			double vector = Math.sqrt(((x * x) + (y * y) + (z * z)));
			// if(stepCount<20){
			EMA(vector);
			// }
			if (vector > EMAvector  && flag) {
				stepCount = stepCount + 1;
				// if(stepCount==5 && thresholdFound ==false){
				// thresholdFound=true;
				// stepCount = 0;
				// steps.setText("...");
				// }

				flag = false;
			}
			if (vector < EMAvector && flag == false) {
				// stepCount = stepCount + 1;
				// if (stepCount > 0) {
				// steps.setText(String.valueOf(stepCount));
				// }
				if (stepCount > 0) {
					steps.setText(String.valueOf(stepCount));
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
}
