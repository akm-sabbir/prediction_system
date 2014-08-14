package com.android.predictionsystems.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class todoDatabaseHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "todotable.db";
	private static final int DATABASE_VERSION = 1;
	private TodoTable[] TABLE_TOINTERACT = null;
	public todoDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}
	public todoDatabaseHelper(Context context, TodoTable[] table){
		
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
		TABLE_TOINTERACT = table;
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < TABLE_TOINTERACT.length; i++)
			TABLE_TOINTERACT[i].onCreate(db);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		for(int i = 0 ; i < TABLE_TOINTERACT.length; i++)
			TABLE_TOINTERACT[i].onUpgrade(db, oldVersion, newVersion);
		
	}

}
