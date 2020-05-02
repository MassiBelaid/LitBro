package com.LilBro.LitBro.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;
import android.net.Uri;
import com.LilBro.LitBro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class DirecteLocalFragment extends Fragment {

    public static final String COLLECTION_NAME = "local";

    private String nomLocal;
    TextView textChargement;
    Button btAlerter;
    VideoView videoLive;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public DirecteLocalFragment(String nomLocal) {
        this.nomLocal = nomLocal;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_directe_local, container, false);
        btAlerter = result.findViewById(R.id.btAlerter);
        videoLive = result.findViewById(R.id.videoLive);
        textChargement = result.findViewById(R.id.textChargement);

        DocumentReference localRef = db.collection(COLLECTION_NAME).document(this.nomLocal);

        localRef.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    Uri videoUri = Uri.parse(documentSnapshot.getString("live"));
                    videoLive.setVideoURI(videoUri);
                    videoLive.requestFocus();
                    videoLive.start();
                    textChargement.setVisibility(View.INVISIBLE);
                }
            }
        }).addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                textChargement.setVisibility(View.INVISIBLE);
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
