package com.LilBro.LitBro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class ConnextionActivity extends AppCompatActivity {

    public static final String UTILISATEUR_AUGMENTE = "augmenté";
    public static final String UTILISATEUR_SIMPLE = "simple";
    public static final String COLLECTION_NAME = "utilisateur";

    EditText editPassword, editLogin;
    Button buttonConnection;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    SharedPreferences mPreferences;
    Utilisateur user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connextion);

        editLogin = (EditText)findViewById(R.id.editLogin);
        editPassword = (EditText)findViewById(R.id.editPassword);
        buttonConnection = (Button) findViewById(R.id.buttonConnexion);

        mPreferences = getSharedPreferences("SESSION",MODE_PRIVATE);
        String userPseudo = getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.LOGIN,"");
        //new SimpleDateFormat().parse(getPreferences(MODE_PRIVATE).getString(Utilisateur.DATEDERNIERCHANGEMENT,null));
        Date dateDernierChang = new Date();
        if(!userPseudo.equals("")){
            user = new Utilisateur(getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.LOGIN,null),
                    getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.MOTDEPASSE,null),
                    getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.UTILISATEURTYPE,null),
                    (dateDernierChang),
                    getSharedPreferences("SESSION",MODE_PRIVATE).getBoolean(Utilisateur.MODIFLOGIN,false),
                    getSharedPreferences("SESSION",MODE_PRIVATE).getString(Utilisateur.UTILISATEUR_LOCAL,null));



            Intent i = new Intent(ConnextionActivity.this, MainActivity.class);
            i.putExtra("utilisateur",user);
            startActivity(i);
        }

        buttonConnection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connexion();
            }
        });
    }

    private void connexion(){
        String login = editLogin.getText().toString();
        final String motDePasse = editPassword.getText().toString();

        if(login.equals("") || motDePasse.equals("")){
            Toast.makeText(ConnextionActivity.this,"Veuillez remplir tout les champs",Toast.LENGTH_LONG).show();
        }else{
            DocumentReference userRef = db.collection(COLLECTION_NAME).document(login);
            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    if (documentSnapshot.exists()){
                        if(documentSnapshot.getString("motDePasse").equals(motDePasse)){
                            user = new Utilisateur(documentSnapshot.getString("login"),documentSnapshot.getString("motDePasse"),
                                    documentSnapshot.getString("utilisateurType"),documentSnapshot.getDate("dateDernierChangement"),
                                    documentSnapshot.getBoolean("modifLogin"),documentSnapshot.getString("StreamLocal"));
                            mPreferences.edit().putString(Utilisateur.LOGIN,documentSnapshot.getString("login")).apply();
                            mPreferences.edit().putString(Utilisateur.MOTDEPASSE,documentSnapshot.getString("motDePasse")).apply();
                            mPreferences.edit().putString(Utilisateur.UTILISATEURTYPE,documentSnapshot.getString("utilisateurType")).apply();
                            mPreferences.edit().putString(Utilisateur.DATEDERNIERCHANGEMENT,documentSnapshot.getDate("dateDernierChangement").toString()).apply();
                            mPreferences.edit().putBoolean(Utilisateur.MODIFLOGIN,documentSnapshot.getBoolean("modifLogin")).apply();
                            mPreferences.edit().putString(Utilisateur.MODIFLOGIN,documentSnapshot.getString("StreamLocal")).apply();

                            Intent i = new Intent(ConnextionActivity.this, MainActivity.class);
                            i.putExtra("utilisateur",user);
                            startActivity(i);
                        }else{
                            Toast.makeText(ConnextionActivity.this,R.string.mdpIncorrect, Toast.LENGTH_LONG).show();
                        }
                    }else{
                        Toast.makeText(ConnextionActivity.this,R.string.userInexistant, Toast.LENGTH_LONG).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(ConnextionActivity.this,R.string.bddEchec, Toast.LENGTH_LONG).show();
                }
            });

        }
    }
}
