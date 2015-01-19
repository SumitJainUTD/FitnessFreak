package health.fitnessfreak;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.TextView;

public class YesterdayRecords extends Activity {
//	public void onCreate(Bundle savedInstanceState) {
//
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.setting_list);
//	}
	 public void onCreate(Bundle savedInstanceState)
     {
         super.onCreate(savedInstanceState);
        
         TextView  tv=new TextView(this);
         tv.setTextSize(25);
         tv.setGravity(Gravity.CENTER_VERTICAL);
         tv.setText("This Is Tab1 Activity");
        
         setContentView(tv);
     }
}
