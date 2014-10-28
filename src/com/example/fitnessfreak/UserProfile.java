package com.example.fitnessfreak;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class UserProfile extends ActionBarActivity {
	public String [] sex = {"Female", "Male"};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_profile);
		Spinner s1 = (Spinner)findViewById(R.id.sex);
		ArrayAdapter<String> adptr = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,sex);
		adptr.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		s1.setAdapter(adptr);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_profile, menu);
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
