package com.LilBro.LitBro.View;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Historique;
import com.LilBro.LitBro.R;

public class ItemHistorique extends RecyclerView.ViewHolder{

    View view;
    TextView textNomLoc, textUser,textDate;

    public ItemHistorique( View itemView) {
        super(itemView);
        this.view = itemView;

        textNomLoc = view.findViewById(R.id.textNomLoc);
        textDate = view.findViewById(R.id.textDate);
        textUser = view.findViewById(R.id.textUser);
    }


    public void updateItemWithHistorique(Historique historique){
        textNomLoc.setText(historique.getLocal());
        textDate.setText(historique.dateToString());
        textUser.setText(historique.getUtilisateur());
    }
}
