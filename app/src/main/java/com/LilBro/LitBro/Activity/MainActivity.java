package com.LilBro.LitBro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;

import com.LilBro.LitBro.Fragment.AlertFragment;
import com.LilBro.LitBro.Fragment.MainFragment;
import com.LilBro.LitBro.Fragment.MapFragment;
import com.LilBro.LitBro.Fragment.ModifierLoginFragment;
import com.LilBro.LitBro.Fragment.ParametreFragment;
import com.LilBro.LitBro.Fragment.VideoAlerteFragment;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    String description ;
    private Fragment mainFragment;
    Utilisateur user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                mainFragment = new MapFragment();
                break;
            case R.id.itemAlert :
                mainFragment = new AlertFragment(this.user);
                break;
            default:
                mainFragment = new MainFragment(this.user);

        }
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,mainFragment).commit();
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
            //if(bottomNavigationView.getSelectedItemId() == R.id.itemIndex){
            if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_main) instanceof MainFragment ){
                finish();
            }else if(getSupportFragmentManager().findFragmentById(R.id.frame_layout_main) instanceof VideoAlerteFragment){
                getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new AlertFragment(user)).commit();
                bottomNavigationView.setSelectedItemId(R.id.itemAlert);
            }
            else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new MainFragment(user)).commit();
            bottomNavigationView.setSelectedItemId(R.id.itemIndex);}
        }else{
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,new ModifierLoginFragment(user)).commit();
            bottomNavigationView.setSelectedItemId(R.id.itemIndex);
        }
    }

    public void updateFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout_main,fragment).commit();
    }
}
