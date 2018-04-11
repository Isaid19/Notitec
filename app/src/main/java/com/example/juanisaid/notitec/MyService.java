package com.example.juanisaid.notitec;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {
    public MyService() {
    }

    int notificationId = 12345;
    String TAG = "FireBase NotiTec: ";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());

        String from = remoteMessage.getFrom();
        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Data: " + remoteMessage.getData());

        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());

            mostrarNotificacion(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        }

        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
    }

    private void mostrarNotificacion(String title, String body)
    {
        Uri soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        int icono = R.mipmap.ic_launcher;
        NotificationCompat.Builder mBuilder;
        mBuilder = new NotificationCompat.Builder(getApplicationContext())
                //.setContentIntent(pendingIntent)
                .setSmallIcon(icono)
                .setContentTitle(title)//departamento
                .setContentText(body)//descripcion
                .setAutoCancel(true)
                .setSound(soundUri)
                ///.setVibrate(new long[] {100, 250, 100, 500})

        ;

        Intent intent = new Intent(getApplicationContext(), ListaPublicacionActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(pendingIntent);

        NotificationManager mNotifyMgr = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        mNotifyMgr.notify(notificationId, mBuilder.build());
        //mBuilder = new NotificationCompat.Builder(getApplicationContext());
    }
}
