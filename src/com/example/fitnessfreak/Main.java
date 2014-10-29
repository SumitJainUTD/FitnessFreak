package com.example.fitnessfreak;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;



@SuppressWarnings("deprecation")
public class Main extends TabActivity {	
	public CommonFunctions cf;
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
        TabHost tabHost = (TabHost)findViewById(android.R.id.tabhost);


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
        
        tab4.setIndicator("Settings");
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
			cf.addDefaultUserData("Sex", "male");
			cf.addDefaultUserData("Height", "0");
			cf.addDefaultUserData("Weight", "0");
			Log.i("Profile Created", "Profile Created");
		}
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
