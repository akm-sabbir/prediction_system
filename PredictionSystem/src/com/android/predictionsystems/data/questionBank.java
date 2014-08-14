package com.android.predictionsystems.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class questionBank implements TodoTable{
	
	public static final String TABLE_TODO = "questionBank";
	public static final String COLUMN_OPTION1 = "Op1";
	public static final String COLUMN_OPTION2 = "Op2";
	public static final String COLUMN_OPTION3 = "Op3";
	public static final String COLUMN_OPTION4 = "Op4";
	public static final String COLUMN_OPTION5 = "Op5";
	public static final String COLUMN_ANSWER = "Answer";
	public static final String COLUMN_COMPLEXITY = "Complexity";
	private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_TODO
								+ " ("
								+ _ID 
								+ " integer primary key autoincrement,"
								+ COLUMN_OPTION1
								+ " text not  null,"
								+ COLUMN_OPTION2 
								+ " text not null,"
								+ COLUMN_OPTION3
								+ " text not null,"
								+ COLUMN_OPTION4
								+ " text not null,"
								+ COLUMN_OPTION5
								+ " text not null,"
								+ COLUMN_ANSWER
								+ " text not null,"
								+ COLUMN_COMPLEXITY
								+ " text not null);";
	public  void onCreate(SQLiteDatabase db){
		
		db.execSQL(DATABASE_CREATE);
		return;
	}
	public   void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion){
		
		Log.w(questionBank.class.getName(),"upgrading the database from the verison" + oldVersion + "to version" + newVersion + ", which will destroy"
				+"the old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_TODO);
		onCreate(db);
		return;
	}
}
