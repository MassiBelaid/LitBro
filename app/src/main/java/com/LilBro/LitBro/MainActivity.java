package com.LilBro.LitBro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.LilBro.LitBro.Models.Utilisateur;

public class MainActivity extends AppCompatActivity {
    TextView textDexcription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textDexcription = (TextView) findViewById(R.id.textDescription);

        Intent i = getIntent();
        Utilisateur user = (Utilisateur) i.getSerializableExtra("utilisateur");
        textDexcription.setText("User : "+user.getLogin()+" Type de compte "+user.getUtilisateurType());
    }
}
