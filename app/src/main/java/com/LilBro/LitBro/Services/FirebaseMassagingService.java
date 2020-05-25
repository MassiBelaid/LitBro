package com.LilBro.LitBro.Services;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.SharedPreferences;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMassagingService extends FirebaseMessagingService {

    public static final String COLLECTION_NAME = "local";
    private static final String CHANNEL_ID = "chn";


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("receptionNotif","On a reçu une notif");


        FirebaseFirestore db = FirebaseFirestore.getInstance();
        DocumentReference doc = db.collection("alertes").document(remoteMessage.getData().get("alertes"));
        doc.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    SharedPreferences mPref = getSharedPreferences("SESSION",MODE_PRIVATE);
                    String userType= getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.UTILISATEURTYPE, null);
                    String userLogin = "";
                    if(userType.equals("simple")){
                        userLogin = getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.UTILISATEUR_SUP, null);
                    }else {
                        userLogin = getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.LOGIN, null);
                    }
                    if(documentSnapshot.getString("utilisateurProp").equals(userLogin)){
                        createNotificationChanel();
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),getString(R.string.channelID));
                        builder.setSmallIcon(R.mipmap.logo_lil_bro);
                        builder.setContentTitle("ALERTE !");
                        builder.setContentText("une alerte dans le local : " + documentSnapshot.getString("local"));
                        builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
                        notificationManagerCompat.notify(1997,builder.build());
                    }
                }
            }
        });


    }

    @Override
    public void onDeletedMessages() {
        super.onDeletedMessages();
    }


    private void createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.notificationName);
            String descritpion = getString(R.string.notificationDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(getString(R.string.channelID), name, importance);
            channel.setDescription(descritpion);
            NotificationManager notmanager = getApplicationContext().getSystemService(NotificationManager.class);
            notmanager.createNotificationChannel(channel);
        }
    }

}
