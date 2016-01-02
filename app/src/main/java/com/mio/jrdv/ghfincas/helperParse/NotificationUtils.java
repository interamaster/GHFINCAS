package com.mio.jrdv.ghfincas.helperParse;

import android.app.ActivityManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.mio.jrdv.ghfincas.R;
import com.mio.jrdv.ghfincas.appParse.AppConfig;

import java.util.List;
import java.util.Random;

/**
 * Created by joseramondelgado on 08/12/15.
 */
public class NotificationUtils {

    /*
     NotificationUtils.java under helper package. This class contains below useful methods to handle the notifications.

> showNotificationMessage() – shows the notification in notification area by setting appropriate title, message, icon and intent.

> isAppIsInBackground() – checks whether your app is in background or foreground.
     */


    private String TAG = NotificationUtils.class.getSimpleName();

    private Context mContext;

    public NotificationUtils() {
    }

    public NotificationUtils(Context mContext) {
        this.mContext = mContext;
    }

    public void showNotificationMessage(String title, String message, Intent intent) {

        // Check for empty push message
        if (TextUtils.isEmpty(message))
            return;

        if (isAppIsInBackground(mContext)) {
            // notification icon
            //int icon = R.mipmap.ic_launcher;
            int icon = R.drawable.logo7redondo;

            //
            int smallIcon = R.drawable.logo7redondo;

            int mNotificationId = AppConfig.NOTIFICATION_ID;

            PendingIntent resultPendingIntent =
                    PendingIntent.getActivity(
                            mContext,
                            0,
                            intent,
                            PendingIntent.FLAG_CANCEL_CURRENT
                    );



            //en vez de asi como en:http://stackoverflow.com/questions/27805645/parse-onpushopen-never-called


            //para el delete/dismiss:
            String packageName = mContext.getPackageName();
            Bundle extras = intent.getExtras();
            Intent deleteIntent = new Intent("com.parse.push.intent.DELETE");
            deleteIntent.putExtras(extras);
            deleteIntent.setPackage(packageName);


            Random random = new Random();
            int contentIntentRequestCode = random.nextInt();
            int deleteIntentRequestCode = random.nextInt();

            Intent contentIntent = new Intent("com.parse.push.intent.OPEN");
            contentIntent.putExtras(extras);
            contentIntent.setPackage(packageName);

            /*//NO USO ESTE O NO ABRE OK
            PendingIntent pContentIntent = PendingIntent.getBroadcast(
                    mContext,
                    contentIntentRequestCode,
                    contentIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);

                    */


            PendingIntent pDeleteIntent = PendingIntent.getBroadcast(
                    mContext,
                    deleteIntentRequestCode,
                    deleteIntent,
                    PendingIntent.FLAG_CANCEL_CURRENT);






            NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    mContext);
            Notification notification = mBuilder.setSmallIcon(smallIcon).setTicker(title).setWhen(0)
                    .setAutoCancel(true)
                    .setContentTitle(title)
                    .setStyle(inboxStyle)
                    .setContentIntent( resultPendingIntent)
                    .setDeleteIntent(pDeleteIntent)//sin esto no recibimos el npushdismiss!!
                    .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                    .setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), icon))
                    .setContentText(message)
                    .build();

            NotificationManager notificationManager = (NotificationManager) mContext.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(mNotificationId, notification);
        } else {
            intent.putExtra("title", title);
            intent.putExtra("message", message);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            mContext.startActivity(intent);
        }
    }

    /**
     * Method checks if the app is in background or not
     *
     * @param context
     * @return
     */
    public static boolean isAppIsInBackground(Context context) {
        boolean isInBackground = true;
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
            List<ActivityManager.RunningAppProcessInfo> runningProcesses = am.getRunningAppProcesses();
            for (ActivityManager.RunningAppProcessInfo processInfo : runningProcesses) {
                if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                    for (String activeProcess : processInfo.pkgList) {
                        if (activeProcess.equals(context.getPackageName())) {
                            isInBackground = false;
                        }
                    }
                }
            }
        } else {
            List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
            ComponentName componentInfo = taskInfo.get(0).topActivity;
            if (componentInfo.getPackageName().equals(context.getPackageName())) {
                isInBackground = false;
            }
        }

        return isInBackground;
    }

}
