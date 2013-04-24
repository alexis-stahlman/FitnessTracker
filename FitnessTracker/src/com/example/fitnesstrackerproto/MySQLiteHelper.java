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

    public static final String TABLE_WORKOUTS = "workouts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EXERCISE = "exercise";
    public static final String COLUMN_DURATION = "duration";
    public static final String COLUMN_CALORIES = "calories";
    public static final String COLUMN_DATE = "date";
    private static final String DATABASE_NAME = "FT_storage.db";
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE = "create table "
            + TABLE_WORKOUTS + "( " + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_EXERCISE
            + " text not null, " + COLUMN_DURATION + " text not null, "
            + COLUMN_CALORIES + " text not null," + COLUMN_DATE
            + " text not null" + ");";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database)
    {
        database.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.w(MySQLiteHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_WORKOUTS);
        onCreate(db);
    }

    // Get all workouts
    public List<WorkoutRecords> getAllWorkouts()
    {
        List<WorkoutRecords> dateDurList = new ArrayList<WorkoutRecords>();

        String selectQuery = "SELECT  * FROM " + TABLE_WORKOUTS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                WorkoutRecords record = new WorkoutRecords();
                record.setId(Integer.parseInt(cursor.getString(0)));
                record.setDuration(cursor.getString(1));
                record.setDate(cursor.getString(2));

                dateDurList.add(record);
            } while (cursor.moveToNext());
        }


        return dateDurList;
    }

}
