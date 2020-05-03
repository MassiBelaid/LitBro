package com.LilBro.LitBro.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Historique;
import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.R;

import java.util.List;

public class ItemHitoriqueAdapter extends RecyclerView.Adapter<ItemHistorique> {

    private List<Historique> listeHistoriques;

    public ItemHitoriqueAdapter(List<Historique> listeHistoriques) {
        this.listeHistoriques = listeHistoriques;
    }

    @NonNull
    @Override
    public ItemHistorique onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        View v1 = inflator.inflate(R.layout.item_historique,parent,false);
        return new ItemHistorique(v1);
    }


    @Override
    public void onBindViewHolder(@NonNull ItemHistorique holder, int position) {
        holder.updateItemWithHistorique(this.listeHistoriques.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listeHistoriques.size();
    }
}
