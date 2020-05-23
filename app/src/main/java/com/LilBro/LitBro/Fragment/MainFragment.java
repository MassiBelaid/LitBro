package com.LilBro.LitBro.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.LilBro.LitBro.Activity.ConnectionActivity;
import com.LilBro.LitBro.Activity.MainActivity;
import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.LilBro.LitBro.View.ItemLocalAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private RecyclerView monRecycleur;
    private ItemLocalAdapter adapter;
    private Utilisateur user;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    String idUserLocal;


    public MainFragment(Utilisateur user){
        //this.videoUri = Uri.parse(uriVid);
        this.user = user;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_main, container, false);


        if(this.user.getUtilisateurType().equals(ConnectionActivity.UTILISATEUR_AUGMENTE)){
            this.idUserLocal = user.getLogin();
        }else{
            this.idUserLocal = user.getUserSup();
        }

        monRecycleur = view.findViewById(R.id.monRecycleur);
        List<Local> ll = new ArrayList<>();
        CollectionReference locaux = db.collection("local");
        locaux.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                    Local loc = document.toObject(Local.class);
                    if(loc.getUtilisateurProp().equals( idUserLocal))
                    {
                        ll.add(loc);
                    }
                }


            }
        }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        adapter = new ItemLocalAdapter(ll);
                        monRecycleur.setAdapter(adapter);
                        monRecycleur.setLayoutManager(new LinearLayoutManager(getActivity()));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),getResources().getString(R.string.bddEchec),Toast.LENGTH_LONG).show();
            }
        });



        return view;

    }



}
