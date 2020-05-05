package com.LilBro.LitBro.View;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Activity.MainActivity;
import com.LilBro.LitBro.Fragment.DirecteLocalFragment;
import com.LilBro.LitBro.Fragment.VideoAlerteFragment;
import com.LilBro.LitBro.Models.Alerte;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;

public class ItemAlerte extends RecyclerView.ViewHolder {

    private View view;
    TextView textNomLoc, textUser,textDate;
    String vidAlerte;
    Button bShowHistAlerte;


    public ItemAlerte(@NonNull View itemView) {
        super(itemView);
        this.view = itemView;

        textNomLoc = view.findViewById(R.id.textNomLocAlerte);
        textDate = view.findViewById(R.id.textDateAlerte);
        textUser = view.findViewById(R.id.textUserAlerte);
        bShowHistAlerte = view.findViewById(R.id.btShowAlerte);


        bShowHistAlerte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) itemView.getContext();
                Utilisateur user = ma.getUser();
                ma.updateFragment(new VideoAlerteFragment(vidAlerte));
            }
        });
    }

    public void updateItemwithAlerte(Alerte alerte){
        textNomLoc.setText(alerte.getLocal());
        textDate.setText(alerte.dateToString());
        textUser.setText(alerte.getUtilisateur());
        this.vidAlerte = (alerte.getVidAlerte());
    }
}
