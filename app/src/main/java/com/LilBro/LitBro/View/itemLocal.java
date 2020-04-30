package com.LilBro.LitBro.View;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.R;
import com.bumptech.glide.Glide;


public class itemLocal extends RecyclerView.ViewHolder {

    TextView nomLocal;
    ImageView imageLocal;
    View view;

    public itemLocal(View itemView) {
        super(itemView);
        this.view = itemView;
        nomLocal = itemView.findViewById(R.id.nomLocal);
        imageLocal = itemView.findViewById(R.id.imageLocal);

    }


    public void updateItemWithLocal(Local local){
        this.nomLocal.setText(local.getNomLocal());

        Glide.with(view).
                load(Uri.parse(local.getImage()))
                .into(imageLocal);
    }



}
