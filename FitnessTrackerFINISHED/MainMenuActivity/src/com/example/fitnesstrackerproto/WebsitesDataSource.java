package com.example.fitnesstrackerproto;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
public class WebsitesDataSource {
	protected static SQLiteDatabase database;
	protected static MySQLiteHelper dbHelper;
	//DATE
	private String[] allColumns = { 
				MySQLiteHelper.COLUMN_ID,
				MySQLiteHelper.COLUMN_SITENAME,
				MySQLiteHelper.COLUMN_USERNAME,
				MySQLiteHelper.COLUMN_PASSWORD,
				MySQLiteHelper.COLUMN_DATE
			};
	public WebsitesDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	//DATE
	public void addWebsite(String sitename, String username, String password, String date) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_SITENAME, sitename);
		values.put(MySQLiteHelper.COLUMN_USERNAME, username);
		values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
		values.put(MySQLiteHelper.COLUMN_DATE, date);
		database.insert(MySQLiteHelper.TABLE_WEBSITES, null,values);
	}
	public void updateWebsite(WebsiteRecords website){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_SITENAME, website.getSitename());
		values.put(MySQLiteHelper.COLUMN_USERNAME, website.getUsername());
		values.put(MySQLiteHelper.COLUMN_PASSWORD, website.getPassword());
		values.put(MySQLiteHelper.COLUMN_DATE, website.getDate());
		database.update(MySQLiteHelper.TABLE_WEBSITES, values, MySQLiteHelper.COLUMN_ID	+ " = " + website.getId(), null);
	}
	public void deleteWebsite(WebsiteRecords website) {
		database.delete(MySQLiteHelper.TABLE_WEBSITES, MySQLiteHelper.COLUMN_ID	+ " = " + website.getId(), null);
	}
	public List<WebsiteRecords> getAllWebsite() {
		List<WebsiteRecords> websites = new ArrayList<WebsiteRecords>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_WEBSITES,
				allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			WebsiteRecords website = cursorToWebsite(cursor);
			websites.add(website);
			cursor.moveToNext();
		}
		cursor.close();
		return websites;
	}
	
	public ArrayList<String> getAllDates() {
        ArrayList<String> dates = new ArrayList<String>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_WEBSITES,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WebsiteRecords website = cursorToWebsite(cursor);
            dates.add(website.getDate());
            cursor.moveToNext();
        }
        cursor.close();
        return dates;
    }
	
	   public ArrayList<String> getAllDurations() {
	        ArrayList<String> durations = new ArrayList<String>();
	        Cursor cursor = database.query(MySQLiteHelper.TABLE_WEBSITES,
	                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            WebsiteRecords website = cursorToWebsite(cursor);
	            durations.add(website.getUsername());
	            cursor.moveToNext();
	        }
	        cursor.close();
	        return durations;
	    }
	
	private WebsiteRecords cursorToWebsite(Cursor cursor) {
		WebsiteRecords website = new WebsiteRecords();
		website.setId(cursor.getInt(0));
		website.setSitename(cursor.getString(1));
		website.setUsername(cursor.getString(2));
		website.setPassword(cursor.getString(3));
		website.setDate(cursor.getString(4));
		return website;
	}
	
	public SQLiteDatabase getDatabase() {
	    return database;
	}
	
	
}
