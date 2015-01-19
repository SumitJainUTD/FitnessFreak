package health.fitnessfreak;

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
	double maxZ;
	int k;
	double threshold = 0.08;
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
			 //Toast.makeText(this,"Step sensor not available!",Toast.LENGTH_LONG).show();
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
		//Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
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
			//Toast.makeText(this, "Using step detecore", Toast.LENGTH_LONG).show();
			Date dte = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
			cf.addStep(sdf.format(dte));
			getBaseContext().sendBroadcast(broadcastIntent);

		} else {
			double x = event.values[0];
			double y = event.values[1];
			double z = event.values[2];
//			if(k<100){
//				if(z>maxZ){
//					maxZ=z;
//				}
//			//	EMA(z);
//				k++;
//			}
//			
			changes++;
//			if(changes>100){
//				threshold = getThreshold(k, changes);
//				Toast.makeText(this, String.valueOf(threshold), Toast.LENGTH_SHORT).show();
//				if(maxZ-z>=threshold){
//					stepCount++;
//					Date dte = new Date();
//					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//					cf.addStep(sdf.format(dte));
//					getBaseContext().sendBroadcast(broadcastIntent);
//					k=changes;
//					maxZ =z;
//				}else{
//					if(z>maxZ){
//						maxZ=z;
//					}
//				}
//			}
//			if (changes > 10000) {
//				changes = 8000;
//			}
//			double vector = Math.sqrt(((x * x) + (y * y) + (z * z)));
//			if (EMACount < 25) {
//				EMA(z);
//			}
//			if (EMACount > 1100) {
//				EMACount = 1001;
//			}
//			if (EMAvector>0 && z > EMAvector  && flag) {
//				stepCount = stepCount + 1;
//				Toast.makeText(this, String.valueOf(EMAvector), Toast.LENGTH_SHORT).show();
//				EMACount++;
//				flag = false;
//			}
//			if (EMAvector<0 && z < EMAvector && flag == false) {
//				Log.i("StepCount", String.valueOf(stepCount));
//				Toast.makeText(this, String.valueOf(z), Toast.LENGTH_SHORT).show();
//				if (stepCount > 0) {
//					Date dte = new Date();
//					SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
//					cf.addStep(sdf.format(dte));
//					getBaseContext().sendBroadcast(broadcastIntent);
//				}
//				flag = true;
//			}
			if (z > 0.5  && flag) {
				stepCount = stepCount + 1;
				//Toast.makeText(this, String.valueOf(z), Toast.LENGTH_SHORT).show();
				EMACount++;
				flag = false;
			}
			if ( z < -0.5 && flag == false) {
				Log.i("StepCount", String.valueOf(stepCount));
				if (stepCount > 0) {
					//Toast.makeText(this, String.valueOf(z), Toast.LENGTH_SHORT).show();
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
	public double getThreshold(int k, int i){
		double th = (8.571/(i-k))+ 2.371;
		return th;
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
