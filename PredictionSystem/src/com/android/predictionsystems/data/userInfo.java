package com.android.predictionsystems.data;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class userInfo implements TodoTable {
    public static final String TABLE_TODO = "user_table";
	public static final String FIRST_NAME = "first_name";
	public static final String LAST_NAME = "last_name";
	public static final String EMAIL = "email";
	public static final String PHONE  = "phone";
	public static final String PASSWORD = "password";
	private static final String DATABSE_CREATE = "create table " 
								+ TABLE_TODO
								+ " (" 
								+ _ID
								+ " integer primary key autoincrement,"
								+ FIRST_NAME
								+ " text not null,"
								+ LAST_NAME
								+ " text not null,"
								+ EMAIL
								+ " text not null,"
								+ PHONE
								+ " text not null,"
								+ PASSWORD
								+" text not null);";
	public  void onCreate(SQLiteDatabase db){
	  db.execSQL(DATABSE_CREATE);
	  return;
	}
	public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
		
		Log.w(userInfo.class.getName(),"Upgrading the databse from version" + oldVersion + "to" + newVersion + ", which will destroy the old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_TODO );
		onCreate(db);
		return;
	}

}
