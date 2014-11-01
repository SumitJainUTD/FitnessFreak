package com.example.fitnessfreak;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class FFOpenHelper extends SQLiteOpenHelper {

	public FFOpenHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table Walk"
				+ "(_id integer primary key, Steps integer, distance REAL, calories REAL, walkDate Date)");
		Log.i("Walk Table Created", "Yes");
		db.execSQL("create table Profile"
				+ "(_id integer primary key,Subject text, Subject_Value text)");
		Log.i("Profile Table Created", "Yes");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS Walk");
		db.execSQL("DROP TABLE IF EXISTS Profile");
		onCreate(db);

	}

	public void addProfileData(String subject, String subjectValue) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("Subject", subject);
		cv.put("Subject_Value", subjectValue);
		db.insert("Profile", null, cv);
		db.close();
	}

	public void updateWalkData(String date) {
		int count = 0;
		SQLiteDatabase db = this.getWritableDatabase();
		
		Cursor csr = executeQuery("Select Count(*)as Count from Walk where walkDate = '"+ date + "'");
		if (csr.moveToFirst()) {
			count = csr.getInt(csr.getColumnIndex("Count"));
		}
		csr.close();
		Log.i("Count", String.valueOf(count));
		if (count > 0) {
			int steps=0;
			double distance=0;
			double calories=0;
			ContentValues cv = new ContentValues();
//			cv.put("Steps", 4);
//			cv.put("distance", 4);
//			cv.put("calories", 4);
//			
//			db.update("Walk", cv, "walkDate" + "='" + date + "'", null);
//			Log.i("Steps", String.valueOf(3));
			csr = executeQuery("Select * from Walk where walkDate = '" + date
					+ "'");
			if (csr.moveToFirst()) {
				Log.i("Steps", String.valueOf("CCCCCCCCCCCC"));
				steps = csr.getInt(1)+1;
				distance = csr.getDouble(2)+0.0008;
				calories = csr.getDouble(3)+0.05;
			}
			csr.close();
			cv.put("Steps", steps);
			cv.put("distance", distance);
			cv.put("calories", calories);		
			db.update("Walk", cv, "walkDate" + "='" + date + "'", null);
			Log.i("Steps", String.valueOf(steps));
			
			cv.clear();
			Log.i("Value", "Increased");			
			db.close();
		} else {
			ContentValues cv = new ContentValues();
			cv.put("Steps", 1);
			cv.put("distance", 1);
			cv.put("calories", 1);
			cv.put("walkDate", date);
			
			db.insert("Walk", null, cv);
			Log.i("First Steps", String.valueOf(1));
			db.close();
			cv.clear();
		}
	}

	public void updateProfileRow(int field, String value) {
		Log.i("updating value", value);
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("Subject_Value", value);
		db.update("Profile", cv, "_id" + "=" + field, null);
		db.close();
		Log.i("updated value", value);
	}

	public void deleteWordFromList(Integer intMainID, Integer intGroupID) {
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("delete from Mapping where mainid = " + intMainID
				+ " and GroupID = " + intGroupID);
		// Toast.makeText("word gonna deleted", "word gonna deleted", 0);
		// String[] whereArgs = {string1,string2,string3};
		db.delete("Mapping", "mainid = ?" + " and GroupID = ?", new String[] {
				String.valueOf(intMainID), String.valueOf(intGroupID) });
		Log.i("Word Deleted", "Word Deleted");
		// Toast.makeText(this, "word gonna deleted", 0).show();
		db.close();
	}

	public void addDataToStats_DB(String ListName, String Date,
			Integer NoOfQuestions, Integer TotalMarks, Integer Correct,
			Double Percentage) {
		Log.i("Add Data to Stats", ListName + "  Going to add");
		Log.i("Total Marks", String.valueOf(TotalMarks));
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues cv = new ContentValues();
		cv.put("ListName", ListName);
		cv.put("ExamDate", Date);
		cv.put("NoOfQuestion", NoOfQuestions);
		cv.put("Percentage", Percentage);
		cv.put("TotalMarks", TotalMarks);
		cv.put("Correct", Correct);
		db.insert("Stats", null, cv);
		db.close();
		Log.i("Add Data to Stats", ListName + "  Yessssss, New Data Added");
	}

	public Cursor executeQuery(String strQuery) {
		// TODO Auto-generated method stub
		String query = strQuery;
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor csr = db.rawQuery(query, null);
		return csr;
	}

}
