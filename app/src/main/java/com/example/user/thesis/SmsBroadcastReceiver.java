package com.example.user.thesis;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

import java.util.StringTokenizer;

/**
 * Created by user on 28/12/2016.
 */
public class SmsBroadcastReceiver extends BroadcastReceiver {

    public static final String SMS_BUNDLE = "pdus";
    public static String timestamp;
    public static Double lng;
    public static Double lat;
    public static String severity;
    public static String cause;

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

            //MainActivity inst = MainActivity.instance();
            //inst.updateInbox(smsMessageStr);
            MapsActivity.update_location();
        }
    }

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