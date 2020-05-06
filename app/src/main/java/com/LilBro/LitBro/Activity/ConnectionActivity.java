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
import android.widget.Toast;

import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.Date;

public class ConnectionActivity extends AppCompatActivity {

    public static final String UTILISATEUR_AUGMENTE = "augmenté";
    public static final String UTILISATEUR_SIMPLE = "simple";
    public static final String COLLECTION_NAME = "utilisateur";

    EditText editPassword, editLogin;
    Button buttonConnection;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences mPreferences;
    Utilisateur user;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection);

        editLogin = (EditText)findViewById(R.id.editLogin);
        editPassword = (EditText)findViewById(R.id.editPassword);
        buttonConnection = (Button) findViewById(R.id.buttonConnexion);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mPreferences = getSharedPreferences("SESSION",MODE_PRIVATE);

        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonConnection.setVisibility(View.INVISIBLE);
                progressBar.setVisibility(View.VISIBLE);
                connexion();
            }
        });
    }

    private void connexion(){
        String login = editLogin.getText().toString();
        final String motDePasse = editPassword.getText().toString();

        if(login.equals("") || motDePasse.equals("")){
            Toast.makeText(ConnectionActivity.this,R.string.champIncomplets,Toast.LENGTH_LONG).show();
        }else{
            DocumentReference userRef = db.collection(COLLECTION_NAME).document(login);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        if(documentSnapshot.getString("motDePasse").equals(motDePasse)){
                            user = new Utilisateur(documentSnapshot.getString("login"),documentSnapshot.getString("motDePasse"),
                                    documentSnapshot.getString("utilisateurType"),documentSnapshot.getDate("dateDernierChangement"),
                                    documentSnapshot.getBoolean("modifLogin"),documentSnapshot.getString("utilisateurSuperieur"));
                            mPreferences.edit().putString(Utilisateur.LOGIN,documentSnapshot.getString("login")).apply();
                            mPreferences.edit().putString(Utilisateur.MOTDEPASSE,documentSnapshot.getString("motDePasse")).apply();
                            mPreferences.edit().putString(Utilisateur.UTILISATEURTYPE,documentSnapshot.getString("utilisateurType")).apply();
                            mPreferences.edit().putLong(Utilisateur.DATEDERNIERCHANGEMENT,documentSnapshot.getDate("dateDernierChangement").getTime()).apply();
                            mPreferences.edit().putBoolean(Utilisateur.MODIFLOGIN,documentSnapshot.getBoolean("modifLogin")).apply();
                            mPreferences.edit().putString(Utilisateur.UTILISATEUR_SUP,documentSnapshot.getString("utilisateurSuperieur")).apply();

                            Date dateDernierCHangement = user.getDateDernierChangement();
                            Date today = new Date();

                            Calendar c = Calendar.getInstance();
                            c.setTime(dateDernierCHangement);
                            c.add(Calendar.DATE, ModifMDPActivity.N_JOURS-10);
                            dateDernierCHangement = c.getTime();

                            if(today.compareTo(dateDernierCHangement) >= 0){
                                Intent i = new Intent(ConnectionActivity.this, ModifMDPActivity.class);
                                i.putExtra("utilisateur",user);
                                startActivity(i);
                            }else{
                                Intent i = new Intent(ConnectionActivity.this, MainActivity.class);
                                i.putExtra("utilisateur",user);
                                startActivity(i);
                            }

                        }else{
                            Toast.makeText(ConnectionActivity.this,R.string.mdpIncorrect, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(ConnectionActivity.this,R.string.userInexistant, Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ConnectionActivity.this,R.string.bddEchec, Toast.LENGTH_LONG).show();
                }
            }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    buttonConnection.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                }
            });

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
