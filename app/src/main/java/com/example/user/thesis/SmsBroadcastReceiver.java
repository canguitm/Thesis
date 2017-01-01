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
                timestamp = tokens.nextToken();
                lng = Double.parseDouble(tokens.nextToken());
                lat = Double.parseDouble(tokens.nextToken());
                severity = tokens.nextToken();
                cause = tokens.nextToken();

                Toast.makeText(context, "New Traffic Report Added", Toast.LENGTH_LONG).show();
            }

            DatabaseHelper.putSmsToDatabase(timestamp, lat, lng, severity, cause, context);
            MapsActivity.update_location();
        }
    }

}