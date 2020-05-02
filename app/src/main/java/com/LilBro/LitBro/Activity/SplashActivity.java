package com.LilBro.LitBro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class SplashActivity extends AppCompatActivity {


    private ProgressBar mProgressBar;
    private TextView mTV;
    Utilisateur user;
    public static final String COLLECTION_NAME = "utilisateur";
    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();
    boolean session ;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences mPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = findViewById(R.id.progressBar);
        mTV = findViewById(R.id.helloInv);

        mPreferences = getSharedPreferences("SESSION", MODE_PRIVATE);
        String userPseudo = getSharedPreferences("SESSION", MODE_PRIVATE).getString(Utilisateur.LOGIN, "");
        //new SimpleDateFormat().parse(getPreferences(MODE_PRIVATE).getString(Utilisateur.DATEDERNIERCHANGEMENT,null));
        Date dateDernierChang = new Date();
        if (!userPseudo.equals("")) {
            user = new Utilisateur(getSharedPreferences("SESSION", MODE_PRIVATE).getString(Utilisateur.LOGIN, null),
                    getSharedPreferences("SESSION", MODE_PRIVATE).getString(Utilisateur.MOTDEPASSE, null),
                    getSharedPreferences("SESSION", MODE_PRIVATE).getString(Utilisateur.UTILISATEURTYPE, null),
                    (dateDernierChang),
                    getSharedPreferences("SESSION", MODE_PRIVATE).getBoolean(Utilisateur.MODIFLOGIN, false),
                    getSharedPreferences("SESSION", MODE_PRIVATE).getString(Utilisateur.UTILISATEUR_SUP, null));
            DocumentReference userRef = db.collection(COLLECTION_NAME).document(user.getLogin());
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()) {
                        if (documentSnapshot.getString("motDePasse").equals(user.getMotDePasse())) {
                            session = true;
                        } else {
                            session = false;
                        }
                    } else {
                        session = false;
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SplashActivity.this, R.string.bddEchec, Toast.LENGTH_LONG).show();
                }
            });
        }else{
            session = false;
        }

            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (mProgressStatus < 100) {
                        mProgressStatus++;
                        android.os.SystemClock.sleep(22);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                                mProgressBar.setProgress(mProgressStatus);
                                if (mProgressStatus == 50) {
                                    mTV.setVisibility(View.VISIBLE);
                                }
                            }
                        });
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if(session){
                                Intent i = new Intent(SplashActivity.this, MainActivity.class);
                                i.putExtra("utilisateur", user);
                                startActivity(i);
                            }else{
                            Intent mySuperIntent = new Intent(SplashActivity.this, ConnextionActivity.class);
                            startActivity(mySuperIntent);
                            finish();}
                        }
                    });
                }
            }).start();


    }}