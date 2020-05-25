package com.LilBro.LitBro.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.LilBro.LitBro.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMassagingService extends FirebaseMessagingService {

    public static final String COLLECTION_NAME = "local";
    private static final String CHANNEL_ID = "chn";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //createNotificationChanel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this,getString(R.string.channelID));
                builder.setSmallIcon(R.mipmap.logo_lil_bro);
                builder.setContentTitle("ALERTE !");
                builder.setContentText("une alerte dans le local : ");
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                int mNotificationID = (int) System.currentTimeMillis();

                NotificationManager mNotifManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                mNotifManager.notify(mNotificationID, builder.build());

                /*NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(this);
                notificationManagerCompat.notify(1997,builder.build());*/
    }


    private void createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.notificationName);
            String descritpion = getString(R.string.notificationDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descritpion);
            NotificationManager notmanager = this.getSystemService(NotificationManager.class);
            notmanager.createNotificationChannel(channel);
        }
    }
}
