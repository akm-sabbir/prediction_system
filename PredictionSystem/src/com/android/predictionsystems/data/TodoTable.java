package com.android.predictionsystems.data;

import android.database.sqlite.SQLiteDatabase;

public interface TodoTable {
   public static final String _ID = "_id";
   public  void onCreate(SQLiteDatabase db);
   public  void onUpgrade(SQLiteDatabase db,int oldVersion,int newVerison);
}
