package com.example.fitnesstrackerproto;
import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;
public class WorkoutsDataSource {
	protected static SQLiteDatabase database;
	protected static MySQLiteHelper dbHelper;
	private String[] allColumns = { 
				MySQLiteHelper.COLUMN_ID,
				MySQLiteHelper.COLUMN_EXERCISE,
				MySQLiteHelper.COLUMN_DURATION,
				MySQLiteHelper.COLUMN_CALORIES,
				MySQLiteHelper.COLUMN_DATE
			};
	public WorkoutsDataSource(Context context) {
		dbHelper = new MySQLiteHelper(context);
	}
	public void open() throws SQLException {
		database = dbHelper.getWritableDatabase();
	}
	public void close() {
		dbHelper.close();
	}
	
	public void addWorkout(String exercise, String duration, String calories, String date) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_EXERCISE, exercise);
		values.put(MySQLiteHelper.COLUMN_DURATION, duration);
		values.put(MySQLiteHelper.COLUMN_CALORIES, calories);
		values.put(MySQLiteHelper.COLUMN_DATE, date);
		database.insert(MySQLiteHelper.TABLE_WORKOUTS, null,values);
	}
	public void updateWorkout(WorkoutRecords workout){
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_EXERCISE, workout.getExercise());
		values.put(MySQLiteHelper.COLUMN_DURATION, workout.getDuration());
		values.put(MySQLiteHelper.COLUMN_CALORIES, workout.getCalories());
		values.put(MySQLiteHelper.COLUMN_DATE, workout.getDate());
		database.update(MySQLiteHelper.TABLE_WORKOUTS, values, MySQLiteHelper.COLUMN_ID	+ " = " + workout.getId(), null);
	}
	public void deleteWorkout(WorkoutRecords workout) {
		database.delete(MySQLiteHelper.TABLE_WORKOUTS, MySQLiteHelper.COLUMN_ID	+ " = " + workout.getId(), null);
	}
	public List<WorkoutRecords> getAllWorkout() {
		List<WorkoutRecords> workouts = new ArrayList<WorkoutRecords>();
		Cursor cursor = database.query(MySQLiteHelper.TABLE_WORKOUTS,
				allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			WorkoutRecords workout = cursorToWorkout(cursor);
			workouts.add(workout);
			cursor.moveToNext();
		}
		cursor.close();
		return workouts;
	}
	
	public ArrayList<String> getAllDates() {
        ArrayList<String> dates = new ArrayList<String>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_WORKOUTS,
                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            WorkoutRecords workout = cursorToWorkout(cursor);
            dates.add(workout.getDate());
            cursor.moveToNext();
        }
        cursor.close();
        return dates;
    }
	
	   public ArrayList<String> getAllDurations() {
	        ArrayList<String> durations = new ArrayList<String>();
	        Cursor cursor = database.query(MySQLiteHelper.TABLE_WORKOUTS,
	                allColumns, null, null, null, null, MySQLiteHelper.COLUMN_ID + " DESC");
	        cursor.moveToFirst();
	        while (!cursor.isAfterLast()) {
	            WorkoutRecords workout = cursorToWorkout(cursor);
	            durations.add(workout.getDuration());
	            cursor.moveToNext();
	        }
	        cursor.close();
	        return durations;
	    }
	
	private WorkoutRecords cursorToWorkout(Cursor cursor) {
		WorkoutRecords workout = new WorkoutRecords();
		workout.setId(cursor.getInt(0));
		workout.setExercise(cursor.getString(1));
		workout.setDuration(cursor.getString(2));
		workout.setCalories(cursor.getString(3));
		workout.setDate(cursor.getString(4));
		return workout;
	}
	
	public SQLiteDatabase getDatabase() {
	    return database;
	}
	
	
}
