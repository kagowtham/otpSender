package com.codebuildrs.otpsender;


import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import static android.content.ContentValues.TAG;

/**
 * Created by manoj on 25-03-2018.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d(TAG, "From: " + remoteMessage.getFrom());


        if (remoteMessage.getData().size() > 0) {
            SharedPreferences preferences=getSharedPreferences("status", Context.MODE_PRIVATE);
            if(preferences.getBoolean("switch",false)) {
                Log.d(TAG, "Message data payload: " + remoteMessage.getData());
                Map m = remoteMessage.getData();
                String to = (String) m.get("to");
                String msg = (String) m.get("msg");
                Log.d(TAG, to + " " + msg);
                SmsManager sms = SmsManager.getDefault();
                sms.sendTextMessage(to, null, msg, null, null);
            }

        }


        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }

    }
}
