package com.wic.buildher;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;
import java.util.Random;

/**
 * Custom Parse notification event receiver to change the notification color
 */
public class ParsePushReceiver extends ParsePushBroadcastReceiver {
    private JSONObject getPushData(Intent intent) {
        try {
            return new JSONObject(intent.getStringExtra("com.parse.Data"));
        } catch (JSONException e) {
            Log.e("PushReceiver", "Unexpected JSONException when receiving push data: ", e);
            return null;
        }
    }

    @Override
    protected Notification getNotification(Context context, Intent intent) {
        JSONObject pushData = getPushData(intent);
        if (pushData == null || (!pushData.has("alert") && !pushData.has("title"))) {
            return null;
        }

        String title = pushData.optString("title", "BuildHer");
        String alert = pushData.optString("alert", "Notification received.");
        String tickerText = String.format(Locale.getDefault(), "%s: %s", title, alert);

        Bundle extras = intent.getExtras();

        Random random = new Random();
        int contentIntentRequestCode = random.nextInt();
        int deleteIntentRequestCode = random.nextInt();

        // Security consideration: To protect the app from tampering, we require that intent
        // filters
        // not be exported. To protect the app from information leaks, we restrict the
        // packages which
        // may intercept the push intents.
        String packageName = context.getPackageName();

        Intent contentIntent = new Intent(ParsePushBroadcastReceiver.ACTION_PUSH_OPEN);
        contentIntent.putExtras(extras);
        contentIntent.setPackage(packageName);

        Intent deleteIntent = new Intent(ParsePushBroadcastReceiver.ACTION_PUSH_DELETE);
        deleteIntent.putExtras(extras);
        deleteIntent.setPackage(packageName);

        PendingIntent pContentIntent = PendingIntent
                .getBroadcast(context, contentIntentRequestCode,
                        contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pDeleteIntent = PendingIntent
                .getBroadcast(context, deleteIntentRequestCode,
                        deleteIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        // The purpose of setDefaults(Notification.DEFAULT_ALL) is to inherit notification
        // properties
        // from system defaults
        NotificationCompat.Builder parseBuilder = new NotificationCompat.Builder(context);
        parseBuilder.setContentTitle(title)
                .setContentText(alert)
                .setTicker(tickerText)
                .setSmallIcon(R.drawable.ic_notification)
                .setColor(context.getResources().getColor(R.color.backgroundBlue))
                .setContentIntent(pContentIntent)
                .setDeleteIntent(pDeleteIntent)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL);
        if (alert != null
                && alert
                .length() > ParsePushBroadcastReceiver.SMALL_NOTIFICATION_MAX_CHARACTER_LIMIT) {
            parseBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(alert));
        }
        return parseBuilder.build();
    }
}
