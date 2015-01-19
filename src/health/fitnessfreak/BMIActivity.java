package health.fitnessfreak;

import com.exaple.fitnessfreak.R;

import java.util.ArrayList;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
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
	@SuppressWarnings("deprecation")
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
		if(vals[2]==""){
			vals[2]="0";
		}
		if(vals[3]==""){
			vals[3]="0";
		}
		double height = Integer.parseInt(vals[2]);
		double weight = Integer.parseInt(vals[3]);
		TextView BMIfield = (TextView)findViewById(R.id.BMI);
		TextView con = (TextView)findViewById(R.id.conclusion);
		if(weight<=0||height<=0){
			BMIfield.setText("BMI: " + String.valueOf(0));
			con.setText("INVALID");
			AlertDialog ad = new AlertDialog.Builder(this).create();  
		    ad.setCancelable(false); // This blocks the 'BACK' button  
		    ad.setMessage("Invalid Height or Weight entries, Please enter valid values");  
		    ad.setTitle("ERROR");
		    ad.setButton("OK", new DialogInterface.OnClickListener() {  
		        @Override  
		        public void onClick(DialogInterface dialog, int which) {  
		            dialog.dismiss(); 
		            TabHost tabHost = Main.tabHost;		            		
		            Log.i("asdasd", "xxxxxxxxxxxxxxxxx");
		            tabHost.setCurrentTab(3);
		        }  
		    });  
		    ad.show();
		}else{
			height  = height/100;			
			double BMI = Math.round((weight/(height*height)));			
			BMIfield.setText("BMI: " + String.valueOf(BMI));
			con.setText(conclusion(BMI, gender));
		}
		
		
	}
	public void EditProfile(View v){
		TabHost tabHost = Main.tabHost;		    
        tabHost.setCurrentTab(3);
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
