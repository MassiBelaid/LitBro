package com.LilBro.LitBro.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.LilBro.LitBro.Activity.ConnextionActivity;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParametreFragment extends Fragment implements View.OnClickListener{

    private Utilisateur user;
    private AlertDialog.Builder alertDialog = null;
    private AlertDialog.Builder alertDialogDeco = null;
    private Utilisateur userGenerated;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Button btGenererCompte,btDeconnextion;
    private SharedPreferences mPreferences;

    public ParametreFragment(Utilisateur u) {
        this.user = u;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

    View result = inflater.inflate(R.layout.fragment_parametre, container, false);
    btGenererCompte = result.findViewById(R.id.bGenererComptes);
    btGenererCompte.setOnClickListener(this);
    btDeconnextion = result.findViewById(R.id.bDeconnection);
    btDeconnextion.setOnClickListener(this);

    mPreferences = getActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE);

    if(!this.user.getUtilisateurType().equals("augmenté")){
        btGenererCompte.setVisibility(View.GONE);
    }

    alertDialog = new AlertDialog.Builder(getActivity());
    alertDialog.setCancelable(true);
    alertDialog.setTitle(getResources().getString(R.string.titeGenererCompte));

    alertDialog.setPositiveButton(getResources().getString(R.string.titeGenererComptePositif), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            saveUtilisateur();
        }
    });
    alertDialog.setNegativeButton(getResources().getString(R.string.titeGenererCompteNegatif), new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            Toast.makeText(getActivity(),getResources().getString(R.string.GenererCompteNegatif),Toast.LENGTH_LONG).show();
        }
    });

        alertDialogDeco = new AlertDialog.Builder(getActivity());
        alertDialogDeco.setCancelable(true);
        alertDialogDeco.setTitle(getResources().getString(R.string.deconnexion));

        alertDialogDeco.setPositiveButton(getResources().getString(R.string.titeGenererComptePositif), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                deconnection();
            }
        });
        alertDialogDeco.setNegativeButton(getResources().getString(R.string.titeGenererCompteNegatif), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Toast.makeText(getActivity(),getResources().getString(R.string.deconnexionAnnulee),Toast.LENGTH_LONG).show();
            }
        });

    return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bGenererComptes :
                genererUser();
                break;
            case R.id.bDeconnection :
                alertDialogDeco.setMessage(R.string.demandeDeco);
                alertDialogDeco.show();
                break;
            default:
                break;
        }
    }


    private void genererUser (){
        String charsMDP = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNPQRSTUVWXYZ123456789";
        String charsPseudo = "abcdefghijklmnopqrstuvwxyz";
        String pseudo = "", mdp = "";
        for (int i=0; i<6; i++)
        {
            int j = (int)Math.floor(Math.random() * charsMDP.length());
            mdp += charsMDP.charAt(j);
            j = (int)Math.floor(Math.random() * charsPseudo.length());
            pseudo += charsPseudo.charAt(j);
        }
        userGenerated = new Utilisateur(pseudo,mdp,"simple", new Date(),false);
        alertDialog.setMessage("pseudo : "+userGenerated.getLogin()+"\nmot de passe : "+userGenerated.getMotDePasse());
        alertDialog.show();
    }

    private void saveUtilisateur(){
        Map<String, Object> userColl = new HashMap<>();
        userColl.put(Utilisateur.LOGIN, userGenerated.getLogin());
        userColl.put(Utilisateur.MOTDEPASSE, userGenerated.getMotDePasse());
        userColl.put(Utilisateur.MODIFLOGIN, userGenerated.getModifLogin());
        userColl.put(Utilisateur.DATEDERNIERCHANGEMENT, userGenerated.getDateDernierChangement());
        userColl.put(Utilisateur.UTILISATEURTYPE, userGenerated.getUtilisateurType());

        db.collection(ConnextionActivity.COLLECTION_NAME).document(userGenerated.getLogin()).set(userColl)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getActivity(),getResources().getString(R.string.GenererCompteSucce),Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),getResources().getString(R.string.GenererCompteFail),Toast.LENGTH_LONG).show();
            }
        });

    }

    private void deconnection(){
            mPreferences.edit().putString(Utilisateur.LOGIN,"").apply();
            mPreferences.edit().putString(Utilisateur.MOTDEPASSE,"").apply();
            mPreferences.edit().putString(Utilisateur.UTILISATEURTYPE,"").apply();
            mPreferences.edit().putString(Utilisateur.DATEDERNIERCHANGEMENT,"").apply();


            Intent i = new Intent(getActivity(),ConnextionActivity.class);
            startActivity(i);
    }
}