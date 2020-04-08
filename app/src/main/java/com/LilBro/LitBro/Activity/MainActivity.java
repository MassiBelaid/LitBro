package com.LilBro.LitBro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.LilBro.LitBro.Fragment.MainFragment;
import com.LilBro.LitBro.Fragment.ParametreFragment;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String description ;
    private Fragment mainFragment;
    private ParametreFragment parametreFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.activity_main_bottom_navigation);

        Intent i = getIntent();
        Utilisateur user = (Utilisateur) i.getSerializableExtra("utilisateur");
        description = "User : "+user.getLogin()+" Type de compte "+user.getUtilisateurType();
        configureAndShowMainFragment();
        configureBottomView();
    }

    private void configureBottomView(){
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> updateMainFragment(item.getItemId()));
    }

    private Boolean updateMainFragment(Integer integer){
        switch (integer){
            case R.id.itemParametre:
                mainFragment = new ParametreFragment();
                break;
            case R.id.item2 :
                mainFragment = new MainFragment(this.description);
                break;
            case R.id.item3 :
                mainFragment = new MainFragment(this.description);
                break;

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,mainFragment).commit();
        return true;
    }

    private void configureAndShowMainFragment(){
        mainFragment = (MainFragment)getSupportFragmentManager().findFragmentById(R.id.frame_layout_main);
        //if (mainFragment == null){
            mainFragment = new MainFragment(this.description);
            getSupportFragmentManager().beginTransaction().add(R.id.frame_layout_main,mainFragment).commit();
        //}
    }
}
