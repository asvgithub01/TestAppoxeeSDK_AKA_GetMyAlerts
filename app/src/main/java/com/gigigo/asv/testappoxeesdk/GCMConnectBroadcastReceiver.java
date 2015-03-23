package com.gigigo.asv.testappoxeesdk;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.gigigo.asv.testappoxeesdk.Models.ChatModel;
import com.gigigo.asv.testappoxeesdk.Models.KEYS;
import com.gigigo.asv.testappoxeesdk.Singleton.ChatArrayStatic;

/**
 * Created by jsberrocoso on 2/07/14.
 * Creado para poder obtener los datos que llegan del servidor de appoxee.
 * Dado que Appoxe no tiene nada para poder obtenerlos.
 * <p/>
 * public class GCMConnectBroadcastReceiver extends BroadcastReceiver {
 * <p/>
 * private static final String TAG = GCMConnectBroadcastReceiver.class.getSimpleName();
 * private static final String NOTIFICATION_TYPE = "type";
 * private static final String NOTIFICATION_ALERT = "alert";
 * private static final String NOTIFICATION_URL = "url";
 * private static final String NOTIFICATION_ID = "id";
 * private static final String NOTIFICATION_URL_BASE = "urlbase";
 * private static final String NOTIFICATION_URL_TERMS = "urlterms";
 *
 * @Override public void onReceive(Context context, Intent intent) {
 * Log.e(TAG, "onReceive");
 * //Utils.logAllKeysBundle(intent);
 * Bundle bundle = intent.getExtras();
 * if (bundle != null) {
 * <p/>
 * Set<String> keys = bundle.keySet();
 * for (String key : keys) {
 * Object o = bundle.get(key);
 * <p/>
 * String value = o.toString();
 * <p/>
 * Toast.makeText(context, value,Toast.LENGTH_LONG);
 * //                if (NOTIFICATION_TYPE.equalsIgnoreCase(key)) {
 * //                    Preferences.getInstance().setNotificationType(value);
 * //                } else if (NOTIFICATION_URL.equalsIgnoreCase(key)) {
 * //                    Preferences.getInstance().setNotificationURL(value);
 * //                } else if (NOTIFICATION_ALERT.equalsIgnoreCase(key)) {
 * //                    Preferences.getInstance().setNotificationAlert(value);
 * //                } else if (NOTIFICATION_ID.equalsIgnoreCase(key)) {
 * //                    Preferences.getInstance().setNotificationId(value);
 * //                } else if(NOTIFICATION_URL_BASE.equalsIgnoreCase(key)){
 * //                    Preferences.getInstance().setNotificationURLBase(value);
 * //                } else if(NOTIFICATION_URL_TERMS.equalsIgnoreCase(key)){
 * //                    Preferences.getInstance().setNotificationURLTerms(value);
 * //                }
 * Log.d(TAG, "onReceive Key : " + key + " Value: " + value);
 * }
 * <p/>
 * //Preferences.getInstance().setAppoxeeNotification(true);
 * } else {
 * Log.d(TAG, "onReceive bundle null");
 * }
 * }
 * }
 */
public class GCMConnectBroadcastReceiver extends WakefulBroadcastReceiver {
    int mId = 0;

    @Override
    public void onReceive(Context context, Intent intent) {


       NewNotification(context, intent);

//ChatArrayStatic.OnNewChatAdded = new InputMethodSession.EventCallback() {
//    @Override
//    public void finishedEvent(int seq, boolean handled) {
//
//        Utils.Debug("*******************************FUCK YEAH, evento producido***********************************");
//
//
//    }
//};

//asv esto redirige el control del flujo al service, pero desde el service no chuta el tema de mostrar toast

        ComponentName comp = new ComponentName(context.getPackageName(),
                GCMIntentService.class.getName());
        // Start the service, keeping the device awake while it is launching.
        startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);
    }

    private void NewNotification(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        if (bundle != null) {

            String valMsg = "";
            String valAdUrl = "";
            String valEndUrl = "";
            String valTag = "";
            String valMid = "1";

            if (bundle.get(KEYS.TAG_KEY) != null)
                valTag = bundle.get(KEYS.TAG_KEY).toString();
            if (bundle.get(KEYS.AD_URL_KEY) != null)
                valAdUrl = bundle.get(KEYS.AD_URL_KEY).toString();
            if (bundle.get(KEYS.END_URL_KEY) != null)
                valEndUrl = bundle.get(KEYS.END_URL_KEY).toString();
            if (bundle.get(KEYS.MSG_KEY) != null)
                valMsg = bundle.get(KEYS.MSG_KEY).toString();
            if (bundle.get(KEYS.ID_KEY) != null)
                valMid = bundle.get(KEYS.ID_KEY).toString();


            if (valTag != null && valTag != "" && !valTag.equals(KEYS.TAG_CHAT)) {
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(context)
                                .setSmallIcon(R.drawable.ic_launcher)
                                .setContentTitle(valTag)
                                .setContentText(valMsg);

                Intent resultIntent = new Intent(context, NotificationActivity.class);
                resultIntent.putExtra(KEYS.AD_URL_KEY, valAdUrl);
                resultIntent.putExtra(KEYS.END_URL_KEY, valEndUrl);


                android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(context);
// Adds the back stack for the Intent (but not the Intent itself)
                stackBuilder.addParentStack(NotificationActivity.class);
// Adds the Intent that starts the Activity to the top of the stack
                stackBuilder.addNextIntent(resultIntent);
                PendingIntent resultPendingIntent =
                        stackBuilder.getPendingIntent(
                                0,
                                PendingIntent.FLAG_UPDATE_CURRENT
                        );
                mBuilder.setContentIntent(resultPendingIntent);
                NotificationManager mNotificationManager =
                        (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

                mId = Integer.parseInt(valMid);
                mNotificationManager.notify(mId, mBuilder.build());

                Toast.makeText(context, valMsg, Toast.LENGTH_LONG).show();
            } else {
                // ChatArrayStatic.AddChat2Chats(new ChatModel("PerryMaisonEscuareGarden","06/03/2015 13:03"," Kill'em All!!!"));

                String valMensa = "";
                String valNick = "";
                String valTime = "";

                if (bundle.get(KEYS.MSG_KEY) != null)
                    valMensa = bundle.get(KEYS.MSG_KEY).toString();
                if (bundle.get(KEYS.NICK_KEY) != null)
                    valNick = bundle.get(KEYS.NICK_KEY).toString();
                if (bundle.get(KEYS.HORA_KEY) != null)
                    valTime = bundle.get(KEYS.HORA_KEY).toString();

                ChatArrayStatic.AddChat2Chats(new ChatModel(valNick, valTime, valMensa));

            }

        }


    }


//    Bundle bundle = intent.getExtras();
//    if (bundle != null) {
//
//        Set<String> keys = bundle.keySet();
//        for (String key : keys) {
//            Object o = bundle.get(key);
//            String value = "BroadCast" + o.toString();
//            Toast.makeText(context, value, Toast.LENGTH_LONG).show();
//
//            NotificationCompat.Builder mBuilder =
//                    new NotificationCompat.Builder(context)
//                            .setSmallIcon(R.drawable.ic_msg)
//                            .setContentTitle("Titulo" + value)
//                            .setContentText("Mensaje" + value);
//// Creates an explicit intent for an Activity in your app
//            Intent resultIntent = new Intent(context, NotificationActivity.class);
//            resultIntent.putExtra("URL_AD", "http://www.publi.com/");
//            resultIntent.putExtra("URL_END", "http://www.gigigo.com/app/es/home");
//            //resultIntent.putExtra("URL_END","http://www.google.com");
//
//
//// The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//            android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(context);
//// Adds the back stack for the Intent (but not the Intent itself)
//            stackBuilder.addParentStack(NotificationActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//            stackBuilder.addNextIntent(resultIntent);
//            PendingIntent resultPendingIntent =
//                    stackBuilder.getPendingIntent(
//                            0,
//                            PendingIntent.FLAG_UPDATE_CURRENT
//                    );
//            mBuilder.setContentIntent(resultPendingIntent);
//            NotificationManager mNotificationManager =
//                    (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//
//            //asv al lurila maguila este int es el id del mensaje, sirve para modificarla
//            mId = mId + 1;
//
//// mId allows you to update the notification later on.
//            mNotificationManager.notify(mId, mBuilder.build());
//
//
//        }
//    }
}