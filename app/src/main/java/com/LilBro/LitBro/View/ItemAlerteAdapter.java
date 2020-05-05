package com.LilBro.LitBro.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Alerte;
import com.LilBro.LitBro.R;

import java.util.List;

public class ItemAlerteAdapter extends RecyclerView.Adapter<ItemAlerte> {

    private List<Alerte> listAlerte;

    public ItemAlerteAdapter(List<Alerte> listAlerte) {
        this.listAlerte = listAlerte;
    }

    @NonNull
    @Override
    public ItemAlerte onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflator = LayoutInflater.from(context);
        View v1 = inflator.inflate(R.layout.item_alerte,parent,false);
        return new ItemAlerte(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemAlerte holder, int position) {
        holder.updateItemwithAlerte(this.listAlerte.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listAlerte.size();
    }
}
