package com.LilBro.LitBro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.LilBro.LitBro.Fragment.AlertFragment;
import com.LilBro.LitBro.Fragment.MainFragment;
import com.LilBro.LitBro.Fragment.MapFragment;
import com.LilBro.LitBro.Fragment.ModifierLoginFragment;
import com.LilBro.LitBro.Fragment.ParametreFragment;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.messaging.FirebaseMessaging;


public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    private String description ;
    private Fragment mainFragment;
    private Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseMessaging.getInstance().subscribeToTopic("alertes").addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d("notif","CA MARCHE");
                } else {
                    Toast.makeText(MainActivity.this,"fail",Toast.LENGTH_LONG).show();
                }
            }
        });


        bottomNavigationView = findViewById(R.id.activity_main_bottom_navigation);

        Intent i = getIntent();
        user = (Utilisateur) i.getSerializableExtra("utilisateur");
        description = "User : "+user.getLogin()+" Type de compte "+user.getUtilisateurType();
        if(user.getModifLogin()){
            configureAndShowMainFragment(new MainFragment(this.user));
        }else{
            configureAndShowMainFragment(new ModifierLoginFragment(user));
        }
        configureBottomView();
    }


    private void configureBottomView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer){
            case R.id.itemParametre:
                mainFragment = new ParametreFragment(this.user);
                break;
            case R.id.itemIndex :
                mainFragment = new MainFragment(this.user);
                break;
            case R.id.itemMaps :
                mainFragment = new MapFragment(this.user);
                break;
            case R.id.itemAlert :
                mainFragment = new AlertFragment(this.user);
                break;
            default:
                mainFragment = new MainFragment(this.user);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,mainFragment).addToBackStack("tag").commit();
        return true;
    }

    private void configureAndShowMainFragment(Fragment mainFragment){
        getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_main,mainFragment).commit();
    }

    public Utilisateur getUser(){
        return this.user;
    }
    public void setUtilisateur(Utilisateur u){
        this.user = u;
    }

    @Override
    public void onBackPressed() {
        if(user.getModifLogin()){
            if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_main) instanceof MainFragment ){
                finish();
            } else if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_main) instanceof MapFragment ||
                    (getSupportFragmentManager().findFragmentById(R.id.frame_layout_main) instanceof AlertFragment) ||
                    (getSupportFragmentManager().findFragmentById(R.id.frame_layout_main) instanceof ParametreFragment)){

                bottomNavigationView.setSelectedItemId(R.id.itemIndex);
                updateFragment(new MainFragment(user));

            } else {
                getSupportFragmentManager().popBackStackImmediate();
            }
        } else {
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new ModifierLoginFragment(user)).commit();
            bottomNavigationView.setSelectedItemId(R.id.itemIndex);
        }
    }

    public void updateFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,fragment).addToBackStack("tag").commit();
    }




}
