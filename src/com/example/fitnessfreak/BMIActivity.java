package com.example.fitnessfreak;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class BMIActivity extends Activity {
	public FFOpenHelper fohelper;
	public String strListName;
	public String strQuery;
	public static ListView listview;
	public CommonFunctions cf;
	public static ArrayList<String> listOfItems;
	public SettingAdapter settingAdptr;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.bmi);

		
	}
	
	@Override
	protected void onResume(){
		super.onResume();
		calculateBMI();
	}
	public void calculateBMI(){
		cf = new CommonFunctions(this);
		strQuery = "Select * from Profile";
		Log.i("Gonna Create List View", "Gonna Create List View");
		Cursor csr = cf.getData(strQuery);
		String[] vals = new String[4];
		int ctr = 0;
		Log.i("Cursor Returned", "yesssssssssssssss");
		if (csr.moveToFirst()){
			do {
				Log.i("Gonna Create List View", csr.getString(2));
				vals[ctr] = csr.getString(2);
				ctr++;
			} while (csr.moveToNext());
		}
		
		csr.close();
		int age = Integer.parseInt(vals[0]);
		String gender = vals[1];
		double height = Integer.parseInt(vals[2]);
		double weight = Integer.parseInt(vals[3]);
		
		height  = height/100;
		
		double BMI = Math.round((weight/(height*height)));
		TextView BMIfield = (TextView)findViewById(R.id.BMI);
		BMIfield.setText("BMI: " + String.valueOf(BMI));
		
		TextView con = (TextView)findViewById(R.id.conclusion);
		con.setText(conclusion(BMI, gender));
	}
	public String conclusion(double BMI, String gender){
		if(gender.equalsIgnoreCase("male")){
			if(BMI<18){
				return "Under Weight";
			}else if (BMI>=18 && BMI<25){
				return "Healthy";
			}else if (BMI>=25 && BMI<30){
				return "Over Weight";
			}else if (BMI>=30 && BMI<40){
				return "Obese";
			}else {
				return "Morbidly Obese";
			}			
		}else{
			if(BMI<17.5){
				return "Under Weight";
			}else if (BMI>=17.5 && BMI<25){
				return "Healthy";
			}else if (BMI>=25 && BMI<30){
				return "Over Weight";
			}else if (BMI>=30 && BMI<40){
				return "Obese";
			}else{
				return "Morbidly Obese";
			}			
		}
	}
}
