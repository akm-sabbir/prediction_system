package com.android.predictionsystems.data;

import java.util.Arrays;
import java.util.HashSet;

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.text.TextUtils;

public class universalContentProvider extends ContentProvider{

	private static final int TODO = 1;
	private static final int TODO_ID = 2;
	private static final String AUTHORITY = "com.android.predictionsystems.data";
	private static final String BASEPATHFORUSER = "user";
	private static final String BASEPATHFORQUESTION = "question";
	private static final String BASEPATHTECH = "tech";
	private static final  String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/todos";
	private static final String CONTENT_BASE_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/todo";
	public static final Uri CONTENT_URI_USER = Uri.parse("content://" + AUTHORITY + "/" + BASEPATHFORUSER);
	public static final Uri CONTENT_URI_QUESTION = Uri.parse("content://" + AUTHORITY + "/" + BASEPATHFORQUESTION);
	public static final Uri CONTENT_URI_TECHNICALINFO = Uri.parse("content://" + AUTHORITY + "/" + BASEPATHTECH);
	private todoDatabaseHelper dbHelper = null;
	private static final userInfo userinformation = new userInfo();
	private static final questionBank questions = new questionBank();
	private static final technicalInfo technology = new technicalInfo();
	private static final UriMatcher sURIMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	static{		
		sURIMatcher.addURI(AUTHORITY, BASEPATHFORUSER, TODO);
		sURIMatcher.addURI(AUTHORITY, BASEPATHFORUSER + "/#", TODO_ID);
		sURIMatcher.addURI(AUTHORITY, BASEPATHFORQUESTION, TODO);
		sURIMatcher.addURI(AUTHORITY, BASEPATHFORQUESTION + "/#", TODO_ID);
		sURIMatcher.addURI(AUTHORITY, BASEPATHTECH, TODO);
		sURIMatcher.addURI(AUTHORITY, BASEPATHTECH + "/#", TODO_ID);
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int uriType = sURIMatcher.match(uri);
		String pathSeg = uri.getPathSegments().get(0);
		String BASEPATH = pathSeg.equals(BASEPATHFORQUESTION) ? (pathSeg.equals(BASEPATHFORQUESTION) ? BASEPATHFORQUESTION : BASEPATHTECH) : BASEPATHFORUSER;
		String TableName = !pathSeg.equals(BASEPATHFORQUESTION) ? (!pathSeg.equals(BASEPATHTECH) ? userInfo.TABLE_TODO : technicalInfo.TABLE_TODO) : questionBank.TABLE_TODO;
		SQLiteDatabase db = dbHelper.getWritableDatabase();		
	    int rowsDeleted = 0;
	    switch(uriType){
	     
	    case TODO:
	    	rowsDeleted = db.delete(TableName, selection, selectionArgs); 
	    	break;
	    case TODO_ID:
	    	String id = uri.getLastPathSegment();
	    	if(TextUtils.isEmpty(selection)){
	    		if(TableName.equals(userInfo.TABLE_TODO))
	    			rowsDeleted = db.delete(TableName, userInfo._ID + " = "+ id, null);
	    		else
	    			rowsDeleted = db.delete(TableName, questionBank._ID + " = "+ id, null);
	    			
	    	}
	    	else{
	    		if(TableName.equals(userInfo.TABLE_TODO))
	    			rowsDeleted = db.delete(TableName, userInfo._ID + " = " + id + " and " + selection, selectionArgs);
	    		else
	    			rowsDeleted = db.delete(TableName, questionBank._ID + " = " + id + " and " + selection, selectionArgs);
	    			
	    	}
	    	break;
	    default:
	    	throw new IllegalArgumentException("Unknown URI" + uri);
	    
	    }
	    getContext().getContentResolver().notifyChange(uri, null);
		return rowsDeleted;
	}

	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		// TODO Auto-generated method stub
		int uriType = sURIMatcher.match(uri);
		String pathSeg;
		pathSeg = uri.getPathSegments().get(0);	
		String BASEPATH = pathSeg.equals(BASEPATHFORQUESTION) ? (pathSeg.equals(BASEPATHFORQUESTION) ? BASEPATHFORQUESTION : BASEPATHTECH) : BASEPATHFORUSER;
		String TableName = !pathSeg.equals(BASEPATHFORQUESTION) ? (!pathSeg.equals(BASEPATHTECH) ? userInfo.TABLE_TODO : technicalInfo.TABLE_TODO) : questionBank.TABLE_TODO;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		long id = 0;
		switch(uriType){
		 
		case TODO:
			id = db.insert(TableName, null, values);
			break;
		default:
			throw new IllegalArgumentException("Unkown URI" + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return Uri.parse(BASEPATH + "/" + id);
	}

	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context context = getContext();
		TodoTable[] table = {userinformation,questions, technology};
		dbHelper = new todoDatabaseHelper(context,table );
		return false;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {
		// TODO Auto-generated method stub
		SQLiteQueryBuilder sb = new SQLiteQueryBuilder();
		checkColumns(projection);
		String pathSeg = uri.getPathSegments().get(0);
		String BASEPATH = pathSeg.equals(BASEPATHFORQUESTION) ? (pathSeg.equals(BASEPATHFORQUESTION) ? BASEPATHFORQUESTION : BASEPATHTECH) : BASEPATHFORUSER;
		String TableName = !pathSeg.equals(BASEPATHFORQUESTION) ? (!pathSeg.equals(BASEPATHTECH) ? userInfo.TABLE_TODO : technicalInfo.TABLE_TODO) : questionBank.TABLE_TODO;
		sb.setTables(TableName);
		int uriType = sURIMatcher.match(uri);
		switch(uriType){
		case TODO:
			break;
		case TODO_ID:
			if(TableName.equals(userInfo.TABLE_TODO))
				sb.appendWhere(userInfo._ID + " = " + uri.getLastPathSegment());
			else
				sb.appendWhere(questionBank._ID + "=" + uri.getLastPathSegment());
			break;
			default:
				throw new IllegalArgumentException("UNKNOWN URI TYPE");
		
		}
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		Cursor cursor = sb.query(db, projection, selection, selectionArgs, null, null, sortOrder);
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		return cursor;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection,
			String[] selectionArgs) {
		// TODO Auto-generated method stub
		int uriType = sURIMatcher.match(uri);
		String pathSeg = uri.getPathSegments().get(2);
		String BASEPATH = pathSeg.equals(BASEPATHFORQUESTION) ? (pathSeg.equals(BASEPATHFORQUESTION) ? BASEPATHFORQUESTION : BASEPATHTECH) : BASEPATHFORUSER;
		String TableName = !pathSeg.equals(BASEPATHFORQUESTION) ? userInfo.TABLE_TODO : questionBank.TABLE_TODO;
		SQLiteDatabase db = dbHelper.getWritableDatabase();
		int rowsUpdated = 0;
		switch(uriType){
		case TODO:
			rowsUpdated = db.update(TableName, values, selection, selectionArgs);
			break;
		case TODO_ID:
			String id = uri.getLastPathSegment();
			if(TextUtils.isEmpty(selection)){
				if(TableName.equals(userInfo.TABLE_TODO))
					rowsUpdated = db.update(TableName, values, userInfo._ID + " = " + id , null);
				else
					rowsUpdated = db.update(TableName, values, questionBank._ID + " = " + id , null);
					
			}
			else{
				if(TableName.equals(userInfo.TABLE_TODO))
					rowsUpdated = db.update(TableName, values, userInfo._ID + " = " + id + "AND" + selection , selectionArgs);
				else
					rowsUpdated = db.update(TableName, values, questionBank._ID + " = " + id + "AND" + selection, selectionArgs);
			}
			break;
			
		default:
			throw new IllegalArgumentException("unkown uri type" + uri);
		
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return rowsUpdated;
	}
  private void checkColumns(String[] projection){
	  
	  String[] columns = { userInfo._ID,userInfo.FIRST_NAME,userInfo.LAST_NAME,userInfo.PHONE,userInfo.EMAIL};
	  if(projection != null){
		  
		  HashSet<String> requestedColumn = new HashSet<String>(Arrays.asList(projection));
		  HashSet<String> availableColumn = new HashSet<String>(Arrays.asList(columns));
		  if(!availableColumn.containsAll(requestedColumn) )
			  throw new IllegalArgumentException("Unknow columns are requested");
	  }
	  
  }
}
