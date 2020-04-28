package com.LilBro.LitBro.View;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.R;


public class itemLocal extends RecyclerView.ViewHolder {

    TextView nomLocal;

    public itemLocal(View itemView) {
        super(itemView);
        nomLocal = itemView.findViewById(R.id.nomLocal);

    }


    public void updateItemWithLocal(Local local){
        this.nomLocal.setText(local.getNomLocal());
    }



}
