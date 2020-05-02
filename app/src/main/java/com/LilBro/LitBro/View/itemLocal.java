package com.LilBro.LitBro.View;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Activity.DirectFragmentActivity;
import com.LilBro.LitBro.Activity.MainActivity;
import com.LilBro.LitBro.Fragment.DirecteLocalFragment;
import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.Models.Utilisateur;
import com.LilBro.LitBro.R;
import com.bumptech.glide.Glide;


public class itemLocal extends RecyclerView.ViewHolder {

    TextView nomLocal;
    ImageView imageLocal;
    View view;
    Button bHistAlertes, btLive;

    public itemLocal(View itemView) {
        super(itemView);
        this.view = itemView;
        nomLocal = itemView.findViewById(R.id.nomLocal);
        imageLocal = itemView.findViewById(R.id.imageLocal);
        bHistAlertes = itemView.findViewById(R.id.btHisAlertes);
        bHistAlertes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "Bouton historique cliqué pour le local : "+nomLocal.getText().toString(), Toast.LENGTH_SHORT).show();
            }
        });
        btLive = itemView.findViewById(R.id.btLive);
        btLive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity ma = (MainActivity) itemView.getContext();
                Utilisateur user = ma.getUser();

                Intent i = new Intent(ma, DirectFragmentActivity.class);
                i.putExtra("utilisateur",user);
                i.putExtra("nomLocal",nomLocal.getText().toString());
                ma.startActivity(i);
            }
        });

    }


    public void updateItemWithLocal(Local local){
        this.nomLocal.setText(local.getNomLocal());

        Glide.with(view).
                load(Uri.parse(local.getImage()))
                .into(imageLocal);
    }



}
