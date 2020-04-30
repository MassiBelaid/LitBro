package com.LilBro.LitBro.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Date;
import com.LilBro.LitBro.Activity.ConnextionActivity;
import com.LilBro.LitBro.Activity.MainActivity;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class ModifierLoginFragment extends Fragment implements View.OnClickListener{

    Utilisateur user;
    TextView textError;
    EditText mdp1E, mdp2E, userE;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private SharedPreferences mPreferences;


    public ModifierLoginFragment(Utilisateur u) {
        this.user = u;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_modifier_login, container, false);
        result.findViewById(R.id.buttonConfirmer).setOnClickListener(this);

        userE = (EditText) result.findViewById(R.id.editLoginmodif);
        textError = (TextView) result.findViewById(R.id.textError);
        mdp1E = (EditText) result.findViewById(R.id.editPasswordmodif);
        mdp2E = (EditText) result.findViewById(R.id.editPasswordmodif2);

        mPreferences = getActivity().getSharedPreferences("SESSION", Context.MODE_PRIVATE);

        // Inflate the layout for this fragment
        return result;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonConfirmer :
                String userS = userE.getText().toString();
                String mdp1S = mdp1E.getText().toString();
                String mdp2S = mdp2E.getText().toString();

                if(userS.equals("") || mdp1S.equals("") || mdp2S.equals("")){
                    textError.setText(getResources().getString(R.string.modifCompteChampsIncomplet));
                }else{
                    if(mdp1S.equals(mdp2S)){
                        if(mdp1S.length() > 5){
                            DocumentReference userRef = db.collection(ConnextionActivity.COLLECTION_NAME).document(userS);
                            userRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                @Override
                                public void onSuccess(DocumentSnapshot documentSnapshot) {
                                    if(documentSnapshot.exists()){
                                        textError.setText(getResources().getString(R.string.modifComptePseudExistant));
                                    }else{
                                        Map<String, Object> userColl = new HashMap<>();
                                        userColl.put(Utilisateur.LOGIN, userS);
                                        userColl.put(Utilisateur.MOTDEPASSE, mdp1S);
                                        userColl.put(Utilisateur.MODIFLOGIN, true);
                                        userColl.put(Utilisateur.DATEDERNIERCHANGEMENT, new Date());
                                        userColl.put(Utilisateur.UTILISATEURTYPE, user.getUtilisateurType());
                                        userColl.put(Utilisateur.UTILISATEUR_SUP, user.getUserSup());
                                        db.collection(ConnextionActivity.COLLECTION_NAME).document(user.getLogin()).delete();
                                        db.collection(ConnextionActivity.COLLECTION_NAME).document(userS).set(userColl)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        mPreferences.edit().putString(Utilisateur.LOGIN,userS).apply();
                                                        mPreferences.edit().putString(Utilisateur.MOTDEPASSE,mdp1S).apply();
                                                        mPreferences.edit().putString(Utilisateur.UTILISATEURTYPE,user.getUtilisateurType()).apply();
                                                        mPreferences.edit().putString(Utilisateur.DATEDERNIERCHANGEMENT,new Date().toString()).apply();
                                                        mPreferences.edit().putBoolean(Utilisateur.MODIFLOGIN,true).apply();
                                                        mPreferences.edit().putString(Utilisateur.UTILISATEUR_SUP,user.getUserSup()).apply();

                                                        Toast.makeText(getActivity(),getResources().getString(R.string.GenererCompteSucce),Toast.LENGTH_LONG).show();
                                                        Fragment mainFrag = new MainFragment(new Utilisateur(userS, mdp1S, user.getUtilisateurType(), new Date(), true, user.getUserSup()));
                                                        FragmentTransaction fragTrans = getFragmentManager().beginTransaction();

                                                        fragTrans.replace(R.id.frame_layout_main,mainFrag);
                                                        fragTrans.commit();


                                                    }
                                                }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(getActivity(),getResources().getString(R.string.GenererCompteFail),Toast.LENGTH_LONG).show();
                                            }
                                        });

                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getActivity(),R.string.bddEchec, Toast.LENGTH_LONG).show();
                                }
                            });

                        }else{
                            textError.setText(getResources().getString(R.string.motDePassecourt));
                        }
                    }else{
                        textError.setText(getResources().getString(R.string.motDePasseDifferents));
                    }
                }
                break;
            default:
                break;
        }
    }
}
