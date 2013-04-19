package com.example.fitnesstrackerproto;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DateDuration {
    private String date;
    private String duration;
    
    //getters and setters for date
    public void setDate(String date){
        this.date = date;
    }
    
    public String getDate(){
        return this.date;
    }
    
    //getters and setters for duration
    public void setDuration(String duration){
        this.duration = duration;
    }
    
    public String getDuration(){
        return this.duration;
    }
    
//    public static ArrayList<DateDuration> store() {
//        String dateDur[] = {};
//        Cursor c = null; // first declare and initialise appropriate objects
//        ArrayList<DateDuration> foos = new ArrayList<DateDuration>();
//        DateDuration member = null;
//        try {
//            
//           c = DateDuration.this.getReadableDatabase().rawQuery("SELECT date, username FROM websites", dateDur); // perform query
//           if (c.moveToFirst()) { // move cursor to first row because implicitly
//              do { // cursor is position before first row
//                 member = new DateDuration(); // for each row create new Foo
//                 member.setDuration(c.getString(1));
//                 member.setDate(c.getString(2));
//                 foos.add(member); // add Foo into ArrayList
//              } while (c.moveToNext()); // it moves cursor to next row
//           }
//        }
//        finally { // in finally block release datasource(s), cursor(s)
//           if (c != null) {
//              c.close();
//           }
//           if (WebsitesDataSource.database != null && WebsitesDataSource.database.isOpen()) {
//               WebsitesDataSource.database.close();
//           }
//        }
//        return foos;
//     }
//    
    public String toString() {
        return "Date : " + getDate() + "   Duration : " + getDuration();
    }
}
