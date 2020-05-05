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

import com.LilBro.LitBro.Models.Alerte;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.LilBro.LitBro.View.ItemAlerteAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class AlerteLocalFragment extends Fragment {

    private Utilisateur user;
    private RecyclerView monRecycleur;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ItemAlerteAdapter adapter;
    private String userProp;
    private String nomLocal;


    public AlerteLocalFragment(Utilisateur user, String nomLocal) {
        this.user = user;
        this.nomLocal = nomLocal;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_alerte_local, container, false);
        monRecycleur = result.findViewById(R.id.recycleurAlerteLocal);

        userProp = "";
        if(user.getUtilisateurType().equals("simple")){
            userProp = user.getUserSup();
        }else{
            userProp = user.getLogin();
        }

        List<Alerte> listAlertes = new ArrayList<>();
        Query colalerte = db.collection("alertes").orderBy("date");
        colalerte.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            Alerte aler = document.toObject(Alerte.class);
                            if(aler.getUtilisateurProp().equals(userProp) && aler.getLocal().equals(nomLocal)){
                                listAlertes.add(aler);
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Collections.reverse(listAlertes);
                adapter = new ItemAlerteAdapter(listAlertes);
                monRecycleur.setAdapter(adapter);
                monRecycleur.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),getResources().getString(R.string.bddEchec),Toast.LENGTH_LONG).show();
            }
        });




        return result;
    }
}
