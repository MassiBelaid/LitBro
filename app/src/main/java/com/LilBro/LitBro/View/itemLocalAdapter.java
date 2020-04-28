package com.LilBro.LitBro.View;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.R;

import java.util.List;

public class itemLocalAdapter extends RecyclerView.Adapter<itemLocal> {
    private List<Local> listeLocaux;

    public itemLocalAdapter(List<Local> listLocaux){
        this.listeLocaux = listLocaux;
    }

    @NonNull
    @Override
    public itemLocal onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        View v1 = inflator.inflate(R.layout.item_local,parent,false);
        return new itemLocal(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull itemLocal holder, int position) {
        holder.updateItemWithLocal(this.listeLocaux.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listeLocaux.size();
    }
}
