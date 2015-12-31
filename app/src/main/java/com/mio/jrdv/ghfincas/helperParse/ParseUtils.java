package com.mio.jrdv.ghfincas.helperParse;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.mio.jrdv.ghfincas.appParse.AppConfig;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseInstallation;
import com.parse.ParsePush;
import com.parse.SaveCallback;



/**
 * Created by joseramondelgado on 08/12/15.
 */
public class ParseUtils {
    /*
    Create a class named ParseUtils.java under helper package.
    This class contains utility methods to interact with parse API like initializing the parse,
    subscribing using email to send individual notifications.
     */

    private static String TAG = ParseUtils.class.getSimpleName();

    public static void verifyParseConfiguration(Context context) {
        if (TextUtils.isEmpty(AppConfig.PARSE_APPLICATION_ID) || TextUtils.isEmpty(AppConfig.PARSE_CLIENT_KEY)) {
            Toast.makeText(context, "Please configure your Parse Application ID and Client Key in AppConfig.java", Toast.LENGTH_LONG).show();
            ((Activity) context).finish();
        }
    }

    public static void registerParse(Context context) {
        //para ver el log!!! de l parse:

        Parse.setLogLevel(Parse.LOG_LEVEL_VERBOSE);



        /*
        y en kitkat 4.4 nos da:(y efectivamente el manifest eta OK!!!)

        12-31 07:37:30.067 1795-1795/com.mio.jrdv.ghfincas V/com.parse.ManifestInfo: Cannot use GCM for push on this device because Google Play Services is not available. Install Google Play Services from the Play Store.
12-31 07:37:30.067 1795-1795/com.mio.jrdv.ghfincas V/com.parse.ManifestInfo: Using none for push.
12-31 07:37:30.077 1795-1810/com.mio.jrdv.ghfincas V/com.parse.CachedCurrentInstallationController: Successfully deserialized Installation object
12-31 07:37:30.077 1795-1795/com.mio.jrdv.ghfincas V/com.parse.ParsePushChannelsController: Tried to subscribe or unsubscribe from a channel, but push is not enabled correctly. Push is not configured for this app because the app manifest is missing required declarations. Please add the following declarations to your app manifest to use GCM for push: make sure that these permissions are declared as children of the root <manifest> element:

                                                                                            <uses-permission android:name="android.permission.INTERNET" />
                                                                                            <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
                                                                                            <uses-permission android:name="android.permission.VIBRATE" />
                                                                                            <uses-permission android:name="android.permission.WAKE_LOCK" />
                                                                                            <uses-permission android:name="android.permission.GET_ACCOUNTS" />
                                                                                            <uses-permission android:name="com.google.android.c2dm.permission.RECEIVE" />
                                                                                            <permission android:name="com.mio.jrdv.ghfincas.permission.C2D_MESSAGE" android:protectionLevel="signature" />
                                                                                            <uses-permission android:name="com.mio.jrdv.ghfincas.permission.C2D_MESSAGE" />

                                                                                            Also, please make sure that these services and broadcast receivers are declared as children of the <application> element:

                                                                                            <service android:name="com.parse.PushService" />
                                                                                            <receiver android:name="com.parse.GcmBroadcastReceiver" android:permission="com.google.android.c2dm.permission.SEND">
                                                                                              <intent-filter>
                                                                                                <action android:name="com.google.android.c2dm.intent.RECEIVE" />
                                                                                                <action android:name="com.google.android.c2dm.intent.REGISTRATION" />
                                                                                                <category android:name="com.mio.jrdv.ghfincas" />
                                                                                              </intent-filter>
                                                                                            </receiver>
                                                                                            <receiver android:name="com.parse.ParsePushBroadcastReceiver" android:exported=false>
                                                                                              <intent-filter>
                                                                                                <action android:name="com.parse.push.intent.RECEIVE" />
                                                                                                <action android:name="com.parse.push.intent.OPEN" />
                                                                                                <action android:name="com.parse.push.intent.DELETE" />
                                                                                              </intent-filter>
                                                                                            </receiver>

         */




        /*

        y en lollipop5.1 sin embargo dice:

        com.mio.jrdv.ghfincas V/com.parse.ManifestInfo: Using gcm for push.
12-31 12:45:42.795 2348-2363/com.mio.jrdv.ghfincas V/com.parse.ParseHttpClient: Using net.java.URLConnection library for networking communication.
12-31 12:45:42.812 2348-2366/com.mio.jrdv.ghfincas I/ParseCommandCache: Parse command cache has started processing queued commands.
12-31 12:45:42.813 2348-2365/com.mio.jrdv.ghfincas V/com.parse.CachedCurrentInstallationController: Successfully deserialized Installation object
12-31 12:45:42.822 2348-2364/com.mio.jrdv.ghfincas V/com.parse.GcmRegistrar: Sending GCM registration intent
12-31 12:45:43.049 2348-2384/com.mio.jrdv.ghfincas D/OpenGLRenderer: Use EGL_SWAP_BEHAVIOR_PRESERVED: true

                                                                     [ 12-31 12:45:43.051  2348: 2348 D/         ]
                                                                     HostConnection::get() New Host Connection established 0xa501a1c0, tid 2348
12-31 12:45:43.056 2348-2348/com.mio.jrdv.ghfincas D/Atlas: Validating map...
         */


        //LUEGO HABRA QUE CHEQUEAR SI ESTAN OK LAS GOOGLE PLAY SERVICES!!!!!!
        /*
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if(status == ConnectionResult.SUCCESS) {
            //Success! Do what you want
        }

        */
        // initializing parse library
        Parse.initialize(context, AppConfig.PARSE_APPLICATION_ID, AppConfig.PARSE_CLIENT_KEY);



            ParseInstallation.getCurrentInstallation().saveInBackground();


        ParsePush.subscribeInBackground(AppConfig.PARSE_CHANNEL, new SaveCallback() {
            @Override
            public void done(ParseException e) {
                Log.e(TAG, "Successfully subscribed to Parse!");
            }
        });
    }

    public static void subscribeWithEmail(String email) {
        ParseInstallation installation = ParseInstallation.getCurrentInstallation();

        installation.put("email", email);

        installation.saveInBackground();

        Log.e(TAG, "Subscribed with email: " + email);
    }

}
