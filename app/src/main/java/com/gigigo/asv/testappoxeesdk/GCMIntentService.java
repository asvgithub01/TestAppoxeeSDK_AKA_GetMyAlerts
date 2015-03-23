package com.gigigo.asv.testappoxeesdk;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.appoxee.utils.Utils;
import com.gigigo.asv.testappoxeesdk.Models.ChatModel;
import com.gigigo.asv.testappoxeesdk.Models.KEYS;
import com.gigigo.asv.testappoxeesdk.Singleton.ChatArrayStatic;
import com.google.android.gcm.GCMBaseIntentService;

/**
 * Created by Alberto on 19/02/2015.
 */
public class GCMIntentService extends GCMBaseIntentService {

    int mId = 0;

    @Override
    protected void onError(Context context, String errorId) {
        // Error en el registro: tratamiento del error
        Utils.Debug("---DANGER--ONERROR SERVICE");
    }

    @Override
    protected void onMessage(Context context, Intent intent) {
        // Notificación recibida: informo al usuario u otra acción
        Utils.Debug("---ONMESSAGE  SERVICE-->NO HAGO YA NA DE NA Q LO HAGA EL RECEIVER, YO PAAASO");

       //NewNotification(context, intent);
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
    @Override
    protected void onRegistered(Context context, String regId) {
        // Registro correcto: envío el regId a mi servidor
        Utils.Debug("---onRegistered wiiiiiiiiija--ONERROR SERVICE");
    }

    @Override
    protected void onUnregistered(Context context, String regId) {
        // Borrado correcto: informo a mi servidor
        Utils.Debug("---onUnregistered wiiiiiiiiija--ONERROR SERVICE");
    }
}
