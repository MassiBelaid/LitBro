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

import com.LilBro.LitBro.Models.Historique;
import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.LilBro.LitBro.View.ItemHistorique;
import com.LilBro.LitBro.View.ItemHitoriqueAdapter;
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


public class HistoriqueVisionnageFragment extends Fragment {

    private Utilisateur user;
    private RecyclerView monRecycleur;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ItemHitoriqueAdapter adapter;

    public HistoriqueVisionnageFragment(Utilisateur user) {
        this.user = user;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View result = inflater.inflate(R.layout.fragment_historique_visionnage, container, false);

        monRecycleur = result.findViewById(R.id.recycleurHistorique);

        List<Historique> historiques = new ArrayList<>();
        CollectionReference colHist = db.collection("historiques");
        colHist.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(QueryDocumentSnapshot document : queryDocumentSnapshots){
                            Historique his = document.toObject(Historique.class);
                            if(his.getUtilisateurProp().equals(user.getLogin())){
                                historiques.add(his);
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                adapter = new ItemHitoriqueAdapter(historiques);
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
