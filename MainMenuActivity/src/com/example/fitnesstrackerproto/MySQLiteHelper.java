//#######################################################
//
//                  MemoPassword
//
//                Mr.Preecha Homjai
//
//              chahalung@hotmail.com
//
//
//#######################################################

package com.example.fitnesstrackerproto;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_WEBSITES = "websites";
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_SITENAME = "sitename";
	public static final String COLUMN_USERNAME = "username";
	public static final String COLUMN_PASSWORD = "password";
	private static final String DATABASE_NAME = "NemoPassword_storage.db";
	private static final int DATABASE_VERSION = 1;
	private static final String DATABASE_CREATE = "create table " + TABLE_WEBSITES 
			+ "( " 
			+ COLUMN_ID	+ " integer primary key autoincrement, " 
			+ COLUMN_SITENAME + " text not null, "
			+ COLUMN_USERNAME + " text not null, "
			+ COLUMN_PASSWORD + " text not null"
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

}
