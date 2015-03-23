package com.gigigo.asv.testappoxeesdk;

import android.app.Application;

///import android.app.TaskStackBuilder;

/**
 * Created by Alberto on 19/02/2015.
 */
public class TestAppoxee extends Application {
   // int mId = 0;

    @Override
    public void onCreate() {
        super.onCreate();
    }
////region notificaion 1
//        NotificationCompat.Builder mBuilder =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_go)
//                        .setContentTitle("Notificacion Tipo 1.0")
//                        .setContentText("Algo de texto por aqui-> Sonidito");
//
//
//        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();
//
//
//        inboxStyle.setBigContentTitle("Detalles: ");
//        inboxStyle.addLine("1");
//        inboxStyle.addLine("2");
//        inboxStyle.addLine("3");
//        inboxStyle.addLine("4");
//        inboxStyle.setSummaryText("summary text");
//
//        mBuilder.setStyle(inboxStyle);
//        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        mBuilder.setSound(alarmSound);
//
//        NotificationManager NotiFlinger =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        NotiFlinger.notify(10101, mBuilder.build());
//
////endregion
//
//// region notifiacion 2
//        NotificationCompat.Builder mBuilder2 =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_msg)
//                        .setContentTitle("Notification Tipo 2")
//                        .setContentText("En Gigigo somos la pera limonera...");
//// Creates an explicit intent for an Activity in your app
//        Intent resultIntent = new Intent(this, NotificationActivity.class);
//        resultIntent.putExtra("URL_AD", "http://www.publi.com/");
//        resultIntent.putExtra("URL_END", "http://www.gigigo.com/app/es/home");
//        //resultIntent.putExtra("URL_END","http://www.google.com");
//
//
//// The stack builder object will contain an artificial back stack for the
//// started Activity.
//// This ensures that navigating backward from the Activity leads out of
//// your application to the Home screen.
//        android.support.v4.app.TaskStackBuilder stackBuilder = android.support.v4.app.TaskStackBuilder.create(this);
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder.addParentStack(NotificationActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder.addNextIntent(resultIntent);
//        PendingIntent resultPendingIntent =
//                stackBuilder.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder2.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//// mId allows you to update the notification later on.
//        mNotificationManager.notify(mId, mBuilder2.build());
////endregion
//
////region Notificacion 3
//        NotificationCompat.Builder mBuilder3 =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("Notification Tipo 3")
//                        .setContentText("Deberia salir la categoria")
//                        .setCategory("NOTICIAS CATEG")
//                        .setContentInfo("Content Info")
//                        .setGroup("Grupo")
//                        .setGroupSummary(true)
//                        .setNumber(7)
//                        .setPriority(BIND_IMPORTANT)
//                        .setWhen(Calendar.getInstance().getTimeInMillis())
//                        .setShowWhen(true)
//                        .setSubText("subtexto veremos..")
//                        .setTicker("tiiicker text")
//                        .setUsesChronometer(true);
//
//// Creates an explicit intent for an Activity in your app
//        Intent resultIntent3 = new Intent(this, NotificationActivity.class);
//        resultIntent3.putExtra("URL_AD", "http://www.publi.com/");
//        resultIntent3.putExtra("URL_END", "http://www.gigigo.com/app/es/home");
//        //resultIntent.putExtra("URL_END","http://www.google.com");
//
//// your application to the Home screen.
//        android.support.v4.app.TaskStackBuilder stackBuilder3 = android.support.v4.app.TaskStackBuilder.create(this);
//// Adds the back stack for the Intent (but not the Intent itself)
//        stackBuilder3.addParentStack(NotificationActivity.class);
//// Adds the Intent that starts the Activity to the top of the stack
//        stackBuilder3.addNextIntent(resultIntent3);
//        PendingIntent resultPendingIntent3 =
//                stackBuilder3.getPendingIntent(
//                        0,
//                        PendingIntent.FLAG_UPDATE_CURRENT
//                );
//        mBuilder3.setContentIntent(resultPendingIntent);
//        NotificationManager mNotificationManager3 =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        mNotificationManager3.notify(12453, mBuilder3.build());
//
////endregion
//
//        //Region NOtification 4
//
//
//        NotificationCompat.Builder mBuilder4 =
//                new NotificationCompat.Builder(this)
//                        .setSmallIcon(R.drawable.ic_launcher)
//                        .setContentTitle("Notification Tipo 4")
//                        .setContentText("Deberia salir la categoria");/*
//                        .setLargeIcon(bitmap)
//                       ;
//
//
//        NotificationCompat.BigPictureStyle bigPicStyle = new NotificationCompat.BigPictureStyle();
//        bigPicStyle.bigPicture(bitmap);
//        bigPicStyle.setBigContentTitle("Ole ola lalala");
//        mBuilder4.setStyle(bigPicStyle);
//*/
////        Intent resultIntent4 = new Intent(this, NotificationActivity.class);
////        resultIntent4.putExtra("URL_AD", "http://www.publi.com/");
////        resultIntent4.putExtra("URL_END", "http://www.gigigo.com/app/es/home");
////        //resultIntent.putExtra("URL_END","http://www.google.com");
////
////// your application to the Home screen.
////        android.support.v4.app.TaskStackBuilder stackBuilder4 = android.support.v4.app.TaskStackBuilder.create(this);
////// Adds the back stack for the Intent (but not the Intent itself)
////        stackBuilder4.addParentStack(NotificationActivity.class);
////// Adds the Intent that starts the Activity to the top of the stack
////        stackBuilder4.addNextIntent(resultIntent4);
////        PendingIntent resultPendingIntent4 =
////                stackBuilder4.getPendingIntent(
////                        0,
////                        PendingIntent.FLAG_UPDATE_CURRENT
////                );
////        mBuilder4.setContentIntent(resultPendingIntent4);
//        NotificationManager mNotificationManager4 =
//                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//
//        mNotificationManager4.notify(58, mBuilder4.build());
////endregion
//




}
