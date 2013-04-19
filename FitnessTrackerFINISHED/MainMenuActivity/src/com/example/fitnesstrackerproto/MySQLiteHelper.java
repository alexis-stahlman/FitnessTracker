package com.example.fitnesstrackerproto;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_WEBSITES = "websites";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SITENAME = "sitename";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	//DATE
	public static final String COLUMN_DATE = "date";
	private static final String DATABASE_NAME = "NemoPassword_storage.db";
	private static final int DATABASE_VERSION = 1;

	
	//DATE
	private static final String DATABASE_CREATE = "create table " + TABLE_WEBSITES 
			+ "( " 
			+ COLUMN_ID	+ " integer primary key autoincrement, " 
			+ COLUMN_SITENAME + " text not null, "
			+ COLUMN_USERNAME + " text not null, "
			+ COLUMN_PASSWORD + " text not null,"
			+ COLUMN_DATE + " text not null"
			+ ");";
	public MySQLiteHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	
	
	@Override
	public void onCreate(SQLiteDatabase database) {
		database.execSQL(DATABASE_CREATE);
	}
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(MySQLiteHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
		db.execSQL("DROP TABLE IF EXISTS" + TABLE_WEBSITES);
		onCreate(db);
	}
	
//	public static ArrayList<String> getAllStringValues() {
//	    ArrayList<String> yourStringValues = new ArrayList<String>();
//	    Cursor result = WebsitesDataSource.database.rawQuery("SELECT * FROM websites", null);
//
//	    
//	    result.moveToFirst();
//	    while (!result.isAfterLast()){
//	        Toast.makeText(MySQLiteHelper.this, result.getString(2)).show();
//	        result.moveToNext();
//	    }
	    
	    
	    
//	    if (result.moveToFirst()) {
//	        do {
////	            yourStringValues.add(result.getString(result
////	                    .getColumnIndex(COLUMN_USERNAME)));
//	        } while (result.moveToNext());
//	    } else {
//	        return null;
//	    }
//	    return yourStringValues;
//	}
	 // Getting All Contacts
	 public List<WebsiteRecords> getAllContacts() {
	    List<WebsiteRecords> dateDurList = new ArrayList<WebsiteRecords>();
	    // Select All Query
	    String selectQuery = "SELECT  * FROM " + TABLE_WEBSITES;
	 
	    SQLiteDatabase db = this.getWritableDatabase();
	    Cursor cursor = db.rawQuery(selectQuery, null);
	 
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	            WebsiteRecords record = new WebsiteRecords();
	            record.setId(Integer.parseInt(cursor.getString(0)));
	            record.setUsername(cursor.getString(1));
	            record.setDate(cursor.getString(2));
	            // Adding contact to list
	            dateDurList.add(record);
	        } while (cursor.moveToNext());
	    }
	 
	    // return contact list
	    return dateDurList;
	}
	
	
}
