package com.LilBro.LitBro.View;

import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Activity.MainActivity;
import com.LilBro.LitBro.Fragment.AlerteLocalFragment;
import com.LilBro.LitBro.Fragment.DirecteLocalFragment;
import com.LilBro.LitBro.Fragment.ImageLocalFragment;
import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;


public class ItemLocal extends RecyclerView.ViewHolder {

    TextView nomLocal,descLocal;
    ImageView imageLocal;
    View view;
    Button bHistAlertes, btLive, btImages;

    public ItemLocal(View itemView) {
        super(itemView);
        this.view = itemView;
        nomLocal = itemView.findViewById(R.id.nomLocal);
        descLocal = itemView.findViewById(R.id.descLocal);
        imageLocal = itemView.findViewById(R.id.imageLocal);
        btImages = itemView.findViewById(R.id.btImages);
        bHistAlertes = itemView.findViewById(R.id.btHisAlertes);
        bHistAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) itemView.getContext();
                Utilisateur user = ma.getUser();
                ma.updateFragment(new AlerteLocalFragment(user, nomLocal.getText().toString()));
            }
        });
        btLive = itemView.findViewById(R.id.btLive);
        btLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) itemView.getContext();
                Utilisateur user = ma.getUser();
                ma.updateFragment(new DirecteLocalFragment(nomLocal.getText().toString(), user));
            }
        });

        btImages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) itemView.getContext();
                Utilisateur user = ma.getUser();
                ma.updateFragment(new ImageLocalFragment(nomLocal.getText().toString(), user));
            }
        });

    }


    public void updateItemWithLocal(Local local){
        this.nomLocal.setText(local.getNomLocal());
        this.descLocal.setText(local.getCategorie());

        Glide.with(view).
                load(Uri.parse(local.getImage())).override(400,400).centerCrop()
                .into(imageLocal);
    }



}
