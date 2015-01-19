package health.fitnessfreak;

import com.exaple.fitnessfreak.R;

import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends Activity {

	public FFOpenHelper fohelper;
	public String strListName;
	public String strQuery;
	public static ListView listview;
	public CommonFunctions cf;
	public static ArrayList<String> listOfItems;
	public SettingAdapter settingAdptr;
	
	

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(R.layout.activity_user_profile);
		setContentView(R.layout.setting_list);
		
		
		listview = (ListView) findViewById(R.id.settingContent_list);
		cf = new CommonFunctions(this);
		listOfItems = new ArrayList<String>();
		strQuery = "Select * from Profile";
		Log.i("Gonna Create List View", "Gonna Create List View");
		Cursor csr = cf.getData(strQuery);
		Log.i("com.example.FF", "Cursor returned");		//
		settingAdptr = new SettingAdapter(this, csr);
		listview.setAdapter(settingAdptr);
		
		listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			//
			String strWord;
			Integer intID;
			String strMeaning;
			String strSentence;
			String strTitle;
			@Override
			public void onItemClick(AdapterView<?> parent, View itemClicked, int position,
					long id) {
				// TODO Auto-generated method stub
				//Toast.makeText(this, "yessss", "LENGTH_SHORT").show();
				//LinearLayout temp = (LinearLayout)itemClicked;
				//TableLayout temp = (TableLayout)itemClicked;
				Log.i("com.example.wordtry.checkPoint","2606_1244");
				TableLayout temp = (TableLayout)itemClicked;
				int x = temp.getChildCount();
				Log.i("com.example.wordtry.ChildCount",String.valueOf(x));
				TableRow tr = (TableRow) temp.getChildAt(0);
				Log.i("com.example.wordtry.RowChildCount",String.valueOf(tr.getChildCount()));
				TextView tvp = (TextView)tr.getChildAt(0);
				TextView fieldVal = (TextView)((TableRow) temp.getChildAt(1)).getChildAt(0);
				Log.i("com.example.wordtry.CheckPoint","2606_1254");
				//TableRow tvp = (TableRow)temp.getChildAt(0);
				//tvp.get
				String strClickedSection = tvp.getText().toString();
				Log.i("com.example.wordtry",fieldVal.getText().toString());
				if(strClickedSection.equalsIgnoreCase("Height")){
					strTitle = "Height (Centimeters)";
					String defaultVal = cf.getSpecificCUserData("Height"); 
					cf.ShowAlertDialogNumbers(strTitle, "Height",defaultVal);
					Log.i("SSSSSSSSSSSSSSSSSSSS",fieldVal.getText().toString());
				}else if(strClickedSection.equalsIgnoreCase("Weight")){
					strTitle = "Weight (Kilograms)";
					String defaultVal = cf.getSpecificCUserData("Weight");
					cf.ShowAlertDialogNumbers(strTitle, "Weight",defaultVal);
				}else if(strClickedSection.equalsIgnoreCase("Age")){
					strTitle = "Age (Number)";
					String defaultVal = cf.getSpecificCUserData("Age");
					cf.ShowAlertDialogNumbers(strTitle, "Age",defaultVal);
				}else if(strClickedSection.equalsIgnoreCase("Sex")){
					strTitle = "Sex";
					String defaultVal = cf.getSpecificCUserData("Sex");
					cf.ShowAlertDialogSpinner(strTitle, "Sex",defaultVal);
				}
				Log.i("resuming page", "resuming pzge");
			}
		});
	}
	public void reload(){
		Cursor csr = cf.getData(strQuery);		
		settingAdptr = new SettingAdapter(this,csr);
		listview.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
		listview.setAdapter(settingAdptr);
	}

	@Override
	protected void onResume(){
		super.onResume();
		reload();
	}	

}
