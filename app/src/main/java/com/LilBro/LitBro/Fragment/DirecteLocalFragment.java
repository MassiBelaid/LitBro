package com.LilBro.LitBro.Fragment;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;

import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DirecteLocalFragment extends Fragment {

    public static final String COLLECTION_NAME = "local";
    private static final String CHANNEL_ID = "chn";

    private String nomLocal;
    Button btAlerter;
    VideoView videoLive;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar progressBar;
    private Utilisateur user;
    String videoUri;
    ProgressBar pb;

    private boolean isPlaying;

    public DirecteLocalFragment(String nomLocal, Utilisateur user) {
        this.user = user;
        this.nomLocal = nomLocal;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_directe_local, container, false);

        isPlaying = false;
        btAlerter = result.findViewById(R.id.btAlerter);
        videoLive = result.findViewById(R.id.videoLive);
        progressBar = result.findViewById(R.id.progressBarVid);
        pb = result.findViewById(R.id.progressBarAlerter);

        DocumentReference localRef = db.collection(COLLECTION_NAME).document(this.nomLocal);

        localRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    videoUri = documentSnapshot.getString("live");
                    Uri videoUri = Uri.parse(documentSnapshot.getString("live"));
                    videoLive.setVideoURI(videoUri);
                    videoLive.requestFocus();


                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                saveInHistorique();
                isPlaying = true;
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),getResources().getString(R.string.bddEchec),Toast.LENGTH_LONG).show();
            }
        });

        btAlerter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isPlaying){
                    pb.setVisibility(View.VISIBLE);
                    btAlerter.setVisibility(View.INVISIBLE);
                    alerter();
                }
            }
        });

        videoLive.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if(what == mp.MEDIA_INFO_VIDEO_RENDERING_START){
                    progressBar.setVisibility(View.GONE);
                }else if(what == mp.MEDIA_INFO_BUFFERING_END){
                    progressBar.setVisibility(View.VISIBLE);
                }else if(what == mp.MEDIA_INFO_BUFFERING_START){
                    progressBar.setVisibility(View.VISIBLE);
                }
                return false;
            }
        });
        videoLive.start();


        return result;
    }

    private void saveInHistorique(){
        Map<String, Object> historique = new HashMap<>();
        historique.put("utilisateur",user.getLogin());
        historique.put("local",nomLocal);
        historique.put("date",new Date());
        if(user.getUtilisateurType().equals("simple")){
            historique.put("utilisateurProp",user.getUserSup());
        }else{
            historique.put("utilisateurProp",user.getLogin());
        }

        db.collection("historiques").document().set(historique)
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),getResources().getString(R.string.bddEchec),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void alerter(){
        Map<String, Object> alerte = new HashMap<>();
        alerte.put("utilisateur",user.getLogin());
        alerte.put("local",nomLocal);
        alerte.put("date",new Date());
        alerte.put("vidAlerte",this.videoUri);
        if(user.getUtilisateurType().equals("simple")){
            alerte.put("utilisateurProp",user.getUserSup());
        }else{
            alerte.put("utilisateurProp",user.getLogin());
        }

        db.collection("alertes").document().set(alerte)
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getActivity(),getResources().getString(R.string.bddEchec),Toast.LENGTH_LONG).show();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pb.setVisibility(View.INVISIBLE);
                btAlerter.setVisibility(View.VISIBLE);
                btAlerter.setClickable(false);
                Toast.makeText(getActivity(),getResources().getString(R.string.AlerteRéussie), Toast.LENGTH_LONG).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                /*createNotificationChanel();
                NotificationCompat.Builder builder = new NotificationCompat.Builder(getContext(),CHANNEL_ID);
                builder.setSmallIcon(R.mipmap.logo_lil_bro);
                builder.setContentTitle("ALERTE !");
                builder.setContentText("une alerte dans le local : "+nomLocal);
                builder.setPriority(NotificationCompat.PRIORITY_DEFAULT);

                NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(getContext());
                notificationManagerCompat.notify(1997,builder.build());*/
                //add the intent to the alerte
            }
        });
    }

    private void createNotificationChanel(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = getString(R.string.notificationName);
            String descritpion = getString(R.string.notificationDescription);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(descritpion);
            NotificationManager notmanager = getActivity().getSystemService(NotificationManager.class);
            notmanager.createNotificationChannel(channel);
        }
    }
}
