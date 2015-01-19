package health.fitnessfreak;

import com.exaple.fitnessfreak.R;

import java.util.ArrayList;


import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;

public class RecordsAdapter extends CursorAdapter implements OnClickListener {
	
	public Context cxt;
	
	public CommonFunctions cf;
	public Settings setting;
	public ListView listV;
	public RecordsAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		 cxt = context;
		// TODO Auto-generated constructor stub
		 cf = new CommonFunctions();
		 setting = new Settings();	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
//		Log.i("Reading Adapter", "Setting Subkect" + cursor.getString(cursor.getColumnIndex("Subject")));
		TextView duration = (TextView) view.findViewById(R.id.rDuration);
		duration.setText(cursor.getString(cursor.getColumnIndex("walkDate")));
		
		
		TextView steps = (TextView) view.findViewById(R.id.rSteps);
		steps.setText(cursor.getString(cursor.getColumnIndex("Steps")));	
		Log.i("Done Adapter", "Setting Fields Done");
		
		TextView distance = (TextView) view.findViewById(R.id.rDistance);
		distance.setText(cursor.getString(cursor.getColumnIndex("distance")));
		
		
		TextView calories = (TextView) view.findViewById(R.id.rCalories);
		calories.setText(cursor.getString(cursor.getColumnIndex("calories")));	
//		Log.i("Done Adapter", "Setting Fields Done");
	}	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("Creating View ", "Cretaing View");
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.row_records, parent,false);				
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}

