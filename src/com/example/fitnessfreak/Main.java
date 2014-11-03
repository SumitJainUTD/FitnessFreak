package com.example.fitnessfreak;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.app.TabActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



@SuppressWarnings("deprecation")
public class Main extends TabActivity {	
	public CommonFunctions cf;
	public static TabHost tabHost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if(x){
//        	Intent NewINt = new Intent(getApplicationContext(),UserProfile.class);    		
//    		startActivity(NewINt);
//        }
        setContentView(R.layout.activity_main);
        
        cf = new CommonFunctions(this); // intialize Common Function Object
        
     // create the TabHost that will contain the Tabs
        tabHost = (TabHost)findViewById(android.R.id.tabhost);
        

        TabSpec tab1 = tabHost.newTabSpec("First Tab");
        TabSpec tab2 = tabHost.newTabSpec("Second Tab");
        TabSpec tab3 = tabHost.newTabSpec("Third tab");
        TabSpec tab4 = tabHost.newTabSpec("Fourth tab");

       // Set the Tab name and Activity
       // that will be opened when particular Tab will be selected
        tab1.setIndicator("Walk");
        tab1.setContent(new Intent(this,WalkActivity.class));
       
        tab2.setIndicator("BMI");
        tab2.setContent(new Intent(this,BMIActivity.class));

        tab3.setIndicator("Records");
        tab3.setContent(new Intent(this,RecordsActivity.class));
//        tab3.setContent(tabHost.newTabSpec("parent2").setIndicator("Parent2"),YesterdayRecords.class, null);
        
        tab4.setIndicator("User");
        tab4.setContent(new Intent(this,Settings.class));
                /** Add the tabs  to the TabHost to display. */
        tabHost.addTab(tab1);
        tabHost.addTab(tab2);
        tabHost.addTab(tab3);
        tabHost.addTab(tab4);
        
        createDefaultUserProfile();
    }
    public void createDefaultUserProfile() {
		String strCountQuery = "select Count(*) as Count from Profile";

		int x = cf.getRecordsCount(strCountQuery);
		Log.i("Profile Count", String.valueOf(x));
		if (x > 0) {
			// do nothing//
		} else {
			cf.addDefaultUserData("Age", "25");
			cf.addDefaultUserData("Sex", "Male");
			cf.addDefaultUserData("Height", "0");
			cf.addDefaultUserData("Weight", "0");
			Log.i("Profile Created", "Profile Created");
		}
		Date dte = new Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy")  ;
		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));cf.addStep(sdf.format(dte));
//		cf.addStep(sdf.format(dte));
//		
		Cursor csr = cf.getData("Select * from Walk where walkDate = '" + sdf.format(dte)	+ "'");
		int ctr = 0;
		Log.i("Cursor Returned", "yesssssssssssssss");
		if (csr.moveToFirst()){
			do {
				Log.i("com.example.ID", String.valueOf(csr.getInt(0)));
				Log.i("com.example.Step", String.valueOf(csr.getInt(1)));
				Log.i("com.example.DIs", String.valueOf(csr.getDouble(2)));
				Log.i("com.example.Cal", String.valueOf(csr.getDouble(3)));
				Log.i("com.example.Date", String.valueOf(csr.getString(4)));
			} while (csr.moveToNext());
		}
		
		csr.close();
	}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
