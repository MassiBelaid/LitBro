package com.LilBro.LitBro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.LilBro.LitBro.Fragment.DirecteLocalFragment;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;

import android.content.Intent;
import android.os.Bundle;

public class DirectFragmentActivity extends AppCompatActivity {

    Utilisateur user;
    String nomLocal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_direct_fragment);

        Intent i = getIntent();
        user = (Utilisateur) i.getSerializableExtra("utilisateur");
        nomLocal = i.getStringExtra("nomLocal");

        configureAndShowDirectFragment(new DirecteLocalFragment(nomLocal));


    }


    private void configureAndShowDirectFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_direct,fragment).commit();
    }
}
