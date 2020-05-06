package com.LilBro.LitBro.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.LilBro.LitBro.Models.Image;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.LilBro.LitBro.View.ItemImageAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class ImageLocalFragment extends Fragment {
    private String nomLocal;
    private Utilisateur user;
    private RecyclerView rv;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private ItemImageAdapter imgAdapteur;
    private TextView txtPasImg;


    public ImageLocalFragment(String nomLocal, Utilisateur user) {
        this.nomLocal = nomLocal;
        this.user = user;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_image_local, container, false);
        rv = view.findViewById(R.id.imgRecycleur);
        txtPasImg = view.findViewById(R.id.pasImg);
        List<Image> listeImage = new ArrayList<>();
        CollectionReference imgRef = db.collection("refimg");
        imgRef.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot document : queryDocumentSnapshots) {
                            Image img = document.toObject(Image.class);
                            if(img.getNomLocal().equals(nomLocal)){
                                listeImage.add(img);
                            }
                        }
                    }
                }).addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(listeImage.size() > 0){
                    imgAdapteur = new ItemImageAdapter(listeImage);
                    rv.setAdapter(imgAdapteur);
                } else{ txtPasImg.setVisibility(View.VISIBLE); }
            }
        });
        return view;
    }
}
