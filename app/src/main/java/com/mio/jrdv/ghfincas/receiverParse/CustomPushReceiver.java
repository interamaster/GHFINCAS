package com.mio.jrdv.ghfincas.receiverParse;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.mio.jrdv.ghfincas.ParseActivityListView;
import com.mio.jrdv.ghfincas.helperParse.NotificationUtils;
import com.parse.ParsePushBroadcastReceiver;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by joseramondelgado on 08/12/15.
 */
public class CustomPushReceiver  extends ParsePushBroadcastReceiver{

    /*
    Parse comes with a default broadcast push receiver, but the options are limited.
     If you want to fully utilize the parse notifications, the best option is create a custom broad cast receiver.

    create a class named CustomPushReceiver.java which extends ParsePushBroadcastReceiver.

    onPushReceive() – method will be called whenever push message is received. In this message the json message will be parsed and shown to user.
     */


    private final String TAG = CustomPushReceiver.class.getSimpleName();

    private NotificationUtils notificationUtils;

    private Intent parseIntent;

    public CustomPushReceiver() {
        super();
    }

    @Override
    protected void onPushReceive(Context context, Intent intent) {
        super.onPushReceive(context, intent);

        if (intent == null)
            return;

        try {
            JSONObject json = new JSONObject(intent.getExtras().getString("com.parse.Data"));

            Log.e(TAG, "Push received: " + json);

            parseIntent = intent;

            parsePushJson(context, json);

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }



        //TODO paso del JSON lo quiero normal!!!

    }

    @Override
    protected void onPushDismiss(Context context, Intent intent) {
        super.onPushDismiss(context, intent);
    }

    @Override
    protected void onPushOpen(Context context, Intent intent) {
        super.onPushOpen(context, intent);
    }

    /**
     * Parses the push notification json
     *
     * @param context
     * @param json
     */
    private void parsePushJson(Context context, JSONObject json) {
        try {
            boolean isBackground = json.getBoolean("is_background");
            JSONObject data = json.getJSONObject("data");
            String title = data.getString("title");
            String message = data.getString("message");

            if (!isBackground) {

               // Intent resultIntent = new Intent(context, MainActivity.class);
               Intent resultIntent = new Intent(context, ParseActivityListView.class);//probamos ocn elnuevo
              // Intent resultIntent = new Intent(context, MainActivity.class);
                resultIntent.putExtra("message",message);

                showNotificationMessage(context, title, message, resultIntent);
            }

        } catch (JSONException e) {
            Log.e(TAG, "Push message json exception: " + e.getMessage());
        }
    }


    /**
     * Shows the notification message in the notification bar
     * If the app is in background, launches the app
     *
     * @param context
     * @param title
     * @param message
     * @param intent
     */
    private void showNotificationMessage(Context context, String title, String message, Intent intent) {

        notificationUtils = new NotificationUtils(context);

        intent.putExtras(parseIntent.getExtras());

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        notificationUtils.showNotificationMessage(title, message, intent);
    }


}
