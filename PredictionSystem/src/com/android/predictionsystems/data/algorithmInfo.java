package com.android.predictionsystems.data;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
public class algorithmInfo implements TodoTable{
	

		public static final String TABLE_TODO = "algorithmInfo";
		public static final String ALGORITHM = "algorithm";
		public static final String APPLIED_EXACT = "exact_application";
		public static final String APPLIED_MODIFIED = "modified_application";
		public static final String EMAIL = "email";
		public static final String USER_ID = "user_id";
		private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_TODO
				+ " ("
				+ _ID 
				+ " integer primary key autoincrement,"
				+ ALGORITHM
				+ " text not  null,"
				+ APPLIED_EXACT 
				+ " text not null,"
				+ APPLIED_MODIFIED
				+ " text not null"
				+ USER_ID
				+" integer);";
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(DATABASE_CREATE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			Log.w(technicalInfo.class.getName(),"upgrading the database from the verison" + oldVersion + "to version" + newVersion + ", which will destroy"
					+"the old data");
			db.execSQL("DROP TABLE IF EXISTS" + TABLE_TODO);
			onCreate(db);
		}

	

}
