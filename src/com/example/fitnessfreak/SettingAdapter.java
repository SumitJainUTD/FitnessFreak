package com.example.fitnessfreak;

import java.util.ArrayList;

import android.R.string;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class SettingAdapter extends CursorAdapter implements OnClickListener {
	
	public Context cxt;
	public ArrayList<Boolean> itemChecked = new ArrayList<Boolean>();
	
	public CommonFunctions cf;
	public Settings setting;
	public ListView listV;
	public SettingAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		 cxt = context;
		// TODO Auto-generated constructor stub
		 cf = new CommonFunctions();
		 setting = new Settings();	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		// TODO Auto-generated method stub
		Log.i("Reading Adapter", "Setting Subkect" + cursor.getString(cursor.getColumnIndex("Subject")));
		TextView subject = (TextView) view.findViewById(R.id.subject);
		subject.setText(cursor.getString(cursor.getColumnIndex("Subject")));
		
		Log.i("Reading Adapter", "Setting Subkect_Value" + cursor.getString(cursor.getColumnIndex("Subject_Value")));
		TextView sub_value = (TextView) view.findViewById(R.id.subject_Value);
		sub_value.setText(cursor.getString(cursor.getColumnIndex("Subject_Value")));	
		Log.i("Done Adapter", "Setting Fields Done");
	}	
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		// TODO Auto-generated method stub
		Log.i("Creating View ", "Cretaing View");
		LayoutInflater inflater = LayoutInflater.from(parent.getContext());
		View view = inflater.inflate(R.layout.setting_row, parent,false);				
		return view;
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
}
