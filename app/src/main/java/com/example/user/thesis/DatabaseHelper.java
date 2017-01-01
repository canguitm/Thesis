package com.example.user.thesis;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by user on 31/12/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Student.db";
    public static final String TABLE_NAME = "report_table";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "LAT";
    public static final String COL_3 = "LNG";
    public static String timestamp;
    public static Double lng;
    public static Double lat;
    public static String severity;
    public static String cause;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        //SQLiteDatabase db = this.getReadableDatabase();


    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table report_table (ID INTEGER PRIMARY KEY AUTOINCREMENT, TIMESTAMP TEXT, LAT REAL, LNG REAL, CAUSE TEXT, SEVERITY TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists report_table");
        onCreate(db);
    }

    public static void putSmsToDatabas(String timestamp, Double lat,Double lng, String severity, String cause, Context context )
    {
        /*
        String smsBody = sms.getMessageBody().toString();
        String address = sms.getOriginatingAddress();

        StringTokenizer tokens = new StringTokenizer(smsBody, "/");
        timestamp = tokens.nextToken();// this will contain "Fruit"
        lng = Double.parseDouble(tokens.nextToken());// this will contain " they
        lat = Double.parseDouble(tokens.nextToken());// this will contain "Fruit"
        severity = tokens.nextToken();// this will contain " they
        cause = tokens.nextToken();// this will contain " they
*/      //Double latt = lat;
        DatabaseHelper dataBaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime(    ));
        // Create SMS row
        ContentValues values = new ContentValues();
        Toast.makeText(context, Double.toString(lat),
                Toast.LENGTH_LONG).show();
        values.put("timestamp", timestamp);
        values.put("lat", lat);
        values.put("lng", lng);
        values.put("severity", severity);
        values.put("cause", cause);

       // INSERT INTO COMPANY VALUES (7, 'James', 24, 'Houston', 10000.00 );
        long result = db.insert("report_table", null, values);
        if(result != -1)
            Toast.makeText(context, "SUCCESS", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();

        db.close();

    }


    public Cursor getAllData(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from report_table", null);
        return res;
    }



}
