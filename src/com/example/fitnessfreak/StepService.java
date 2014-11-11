package com.example.fitnessfreak;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;
import android.widget.Toast;

public class StepService extends IntentService implements SensorEventListener {
	SensorManager sensormgr;
	Sensor mAccelerometer;
	Sensor mGyroscopre;
	Sensor mStepDetector;
	Sensor mOreintation;
	Sensor mRotation;
	Intent broadcastIntent;
	CommonFunctions cf;
	boolean stepDetectorSensor;
	int stepCount;
	int EMACount;
	public static double EMAvector = 0.0;
	public static int changes = 0;
	public static boolean flag = true;

	public StepService() {
		super("STEP SERVICE");
		// TODO Auto-generated constructor stub
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
//		Toast.makeText(this, "Walk", Toast.LENGTH_LONG).show();
		sensormgr = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mStepDetector = sensormgr.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
		if (mStepDetector == null) {
			 Toast.makeText(this,"Step sensor not available!",Toast.LENGTH_LONG).show();
			mAccelerometer = sensormgr
					.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		} else {
			stepDetectorSensor = true;
		}
		cf = new CommonFunctions(this);
		startSensor();
		broadcastIntent = new Intent();
		broadcastIntent.setAction("STEP_TAKEN");
		return START_STICKY;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		stopSensor();
		Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
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

	public void stopSensor() {
		//if (stepDetectorSensor) {
			sensormgr.unregisterListener(this, mStepDetector);
	//	} else {
			sensormgr.unregisterListener(this, mAccelerometer);
	//	}
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		// TODO Auto-generated method stub

		if (stepDetectorSensor) {
			stepCount++;
			// steps.setText(String.valueOf(stepCount));
			Toast.makeText(this, "Using step detecore", Toast.LENGTH_LONG).show();
			Date dte = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			cf.addStep(sdf.format(dte));
			getBaseContext().sendBroadcast(broadcastIntent);

		} else {
			double x = event.values[0];
			double y = event.values[1];
			double z = event.values[2];
			changes++;
			if (changes > 10000) {
				changes = 8000;
			}
			double vector = Math.sqrt(((x * x) + (y * y) + (z * z)));
			if (EMACount < 100) {
				EMA(vector);
			}
			if (EMACount > 1100) {
				EMACount = 1001;
			}
			if (vector > EMAvector + 0.02  && flag) {
				stepCount = stepCount + 1;
				Toast.makeText(this, "Using Accelerometer", Toast.LENGTH_LONG).show();
				EMACount++;
				flag = false;
			}
			if (vector < EMAvector - 0.02 && flag == false) {
				Log.i("StepCount", String.valueOf(stepCount));
				if (stepCount > 0) {
					Date dte = new Date();
					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
					cf.addStep(sdf.format(dte));
					getBaseContext().sendBroadcast(broadcastIntent);
				}
				flag = true;
			}

		}
	}

	public void EMA(double vctr) {
		if (EMAvector == 0) {
			EMAvector = vctr;
		} else {
			double k = ((double) 2) / (changes + 1);
			EMAvector = vctr * k + (1 - k) * EMAvector;
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onHandleIntent(Intent intent) {
		// TODO Auto-generated method stub

	}

}
