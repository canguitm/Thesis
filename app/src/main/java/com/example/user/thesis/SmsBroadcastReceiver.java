package com.example.user.thesis;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Created by user on 28/12/2016.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    DatabaseHelper db;
    public static final String SMS_BUNDLE = "pdus";
    public static String timestamp;
    public static Double lng;
    public static Double lat;
    public static String severity;
    public static String cause;
    public static String trylang= "HEHE";



    public void onReceive(Context context, Intent intent) {

        Bundle intentExtras = intent.getExtras();

        if (intentExtras != null) {
            Object[] sms = (Object[]) intentExtras.get(SMS_BUNDLE);
            String smsMessageStr = "";
            for (int i = 0; i < sms.length; ++i) {
                String format = intentExtras.getString("format");
                SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) sms[i], format);

                String smsBody = smsMessage.getMessageBody().toString();
                String address = smsMessage.getOriginatingAddress();

                StringTokenizer tokens = new StringTokenizer(smsBody, "/");
                timestamp = tokens.nextToken();// this will contain "Fruit"
                lng = Double.parseDouble(tokens.nextToken());// this will contain " they
                lat = Double.parseDouble(tokens.nextToken());// this will contain "Fruit"
                severity = tokens.nextToken();// this will contain " they
                cause = tokens.nextToken();// this will contain " they


                //smsMessageStr += "SMS From: " + address + "\n";
                smsMessageStr = timestamp + lng + lat +severity + cause;
                Toast.makeText(context, "New Traffic Report Added",
                        Toast.LENGTH_LONG).show();
            }
            //SmsMessage smsMessage;
           DatabaseHelper.putSmsToDatabas(timestamp, lat, lng, severity, cause, context);
           // MainActivity inst = MainActivity.instance();
            //inst.putSmsToDatabas(lng, lat, context);

            MapsActivity.update_location();
        }
    }
/*
    private void putSmsToDatabase( SmsMessage sms, Context context )
    {
        String smsBody = sms.getMessageBody().toString();
        String address = sms.getOriginatingAddress();

        StringTokenizer tokens = new StringTokenizer(smsBody, "/");
        timestamp = tokens.nextToken();// this will contain "Fruit"
        lng = Double.parseDouble(tokens.nextToken());// this will contain " they
        lat = Double.parseDouble(tokens.nextToken());// this will contain "Fruit"
        severity = tokens.nextToken();// this will contain " they
        cause = tokens.nextToken();// this will contain " they

        DatabaseHelper dataBaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = dataBaseHelper.getWritableDatabase();

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime(    ));
        // Create SMS row
        ContentValues values = new ContentValues();

        values.put("LAT", lat);
        //values.put("SURNAME", mydate);
        //values.put("LNG", lng);
// values.put( READ, MESSAGE_IS_NOT_READ );
// values.put( STATUS, sms.getStatus() );
// values.put( TYPE, MESSAGE_TYPE_INBOX );
// values.put( SEEN, MESSAGE_IS_NOT_SEEN );

        db.insert("student_table", null, values);

        db.close();

    }
    */

    /*
    public Cursor getData(int id) {
        DatabaseHelper dataBaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = dataBaseHelper.getReadableDatabase();
      //  SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from student_table where id="+id+"", null );
        return res;
    }
    */



    public static String getTimestamp()
    {
        return timestamp;

    }

    public static Double getLng()
    {
        return lng;

    }

    public static Double getLat()
    {
        return lat;

    }

    public static String getSeverity()
    {
        return severity;

    }

    public static String getCause()
    {
        return cause;

    }


}