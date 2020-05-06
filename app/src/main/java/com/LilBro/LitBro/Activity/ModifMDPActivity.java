package com.LilBro.LitBro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ModifMDPActivity extends AppCompatActivity {


    public static final int N_JOURS = 30;
    EditText mdp1E, mdp2E;
    TextView textError,textIgnore,textDeconnexion;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    ProgressBar progBar;
    Button bConfirmer;
    private SharedPreferences mPreferences;
    private Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modif_m_d_p);


        textError = (TextView) findViewById(R.id.textErrormdp);
        mdp1E = (EditText) findViewById(R.id.editPasswordmodifmdp);
        mdp2E = (EditText) findViewById(R.id.editPasswordmodif2mdp);
        progBar = (ProgressBar) findViewById(R.id.progressBarModifLoginmdp);
        bConfirmer = (Button) findViewById(R.id.buttonConfirmermdp);
        textIgnore = (TextView) findViewById(R.id.textIgnorermdp);
        textDeconnexion = (TextView) findViewById(R.id.textDeconnexion);

        mPreferences = getSharedPreferences("SESSION",MODE_PRIVATE);

        Intent i = getIntent();
        user = (Utilisateur) i.getSerializableExtra("utilisateur");


        Date dateDernierCHangement = user.getDateDernierChangement();
        Date today = new Date();

        Calendar c = Calendar.getInstance();
        c.setTime(dateDernierCHangement);
        c.add(Calendar.DATE, N_JOURS);
        dateDernierCHangement = c.getTime();

        if(today.compareTo(dateDernierCHangement) >= 0){
            textIgnore.setVisibility(View.INVISIBLE);
        }

        textIgnore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ModifMDPActivity.this, MainActivity.class);
                i.putExtra("utilisateur",user);
                startActivity(i);
            }
        });


        bConfirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //debut
                String mdp1S = mdp1E.getText().toString();
                String mdp2S = mdp2E.getText().toString();
                bConfirmer.setVisibility(View.INVISIBLE);
                progBar.setVisibility(View.VISIBLE);


                if( mdp1S.equals("") || mdp2S.equals("")){
                    textError.setText(getResources().getString(R.string.modifCompteChampsIncomplet));
                }else{
                    if(mdp1S.equals(mdp2S)){
                        if(mdp1S.length() > 5){
                            if (!mdp1S.equals(user.getMotDePasse())) {

                                Map<String, Object> userColl = new HashMap<>();
                                userColl.put(Utilisateur.LOGIN, user.getLogin());
                                userColl.put(Utilisateur.MOTDEPASSE, mdp1S);
                                userColl.put(Utilisateur.MODIFLOGIN, true);
                                userColl.put(Utilisateur.DATEDERNIERCHANGEMENT, new Date());
                                userColl.put(Utilisateur.UTILISATEURTYPE, user.getUtilisateurType());
                                userColl.put(Utilisateur.UTILISATEUR_SUP, user.getUserSup());
                                db.collection(ConnectionActivity.COLLECTION_NAME).document(user.getLogin()).delete();
                                db.collection(ConnectionActivity.COLLECTION_NAME).document(user.getLogin()).set(userColl)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                mPreferences.edit().putString(Utilisateur.LOGIN,user.getLogin()).apply();
                                                mPreferences.edit().putString(Utilisateur.MOTDEPASSE,mdp1S).apply();
                                                mPreferences.edit().putString(Utilisateur.UTILISATEURTYPE,user.getUtilisateurType()).apply();
                                                mPreferences.edit().putLong(Utilisateur.DATEDERNIERCHANGEMENT,new Date().getTime()).apply();
                                                mPreferences.edit().putBoolean(Utilisateur.MODIFLOGIN,true).apply();
                                                mPreferences.edit().putString(Utilisateur.UTILISATEUR_SUP,user.getUserSup()).apply();

                                                //Toast.makeText(getActivity(),getResources().getString(R.string.GenererCompteSucce),Toast.LENGTH_LONG).show();
                                                Utilisateur u = new Utilisateur(user.getLogin(), mdp1S, user.getUtilisateurType(), new Date(), true, user.getUserSup());
                                                Intent i = new Intent(ModifMDPActivity.this, MainActivity.class);
                                                i.putExtra("utilisateur",u);
                                                startActivity(i);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        bConfirmer.setVisibility(View.VISIBLE);
                                        progBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(ModifMDPActivity.this,getResources().getString(R.string.GenererCompteFail),Toast.LENGTH_LONG).show();
                                    }
                                });

                            }else{
                                textError.setText(getResources().getString(R.string.changeMDP));
                                bConfirmer.setVisibility(View.VISIBLE);
                                progBar.setVisibility(View.INVISIBLE);
                            }

                        }else{
                            textError.setText(getResources().getString(R.string.motDePassecourt));
                            bConfirmer.setVisibility(View.VISIBLE);
                            progBar.setVisibility(View.INVISIBLE);
                        }
                    }else{
                        textError.setText(getResources().getString(R.string.motDePasseDifferents));
                        bConfirmer.setVisibility(View.VISIBLE);
                        progBar.setVisibility(View.INVISIBLE);
                    }
                }

                //fin
            }
        });


        textDeconnexion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deconnection();
            }
        });


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void deconnection(){
        mPreferences.edit().putString(Utilisateur.LOGIN,"").apply();
        mPreferences.edit().putString(Utilisateur.MOTDEPASSE,"").apply();
        mPreferences.edit().putString(Utilisateur.UTILISATEURTYPE,"").apply();
        mPreferences.edit().putLong(Utilisateur.DATEDERNIERCHANGEMENT,0).apply();
        mPreferences.edit().putString(Utilisateur.UTILISATEUR_SUP,"").apply();


        Intent i = new Intent(ModifMDPActivity.this, ConnectionActivity.class);
        startActivity(i);
    }
}
