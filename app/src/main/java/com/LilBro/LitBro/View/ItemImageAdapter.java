package com.LilBro.LitBro.View;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Image;
import com.LilBro.LitBro.R;

import java.util.List;

public class ItemImageAdapter extends RecyclerView.Adapter<ItemImage> {

    private List<Image> listeImg;

    public ItemImageAdapter(List<Image> listeImg) {
        this.listeImg = listeImg;
    }

    @NonNull
    @Override
    public ItemImage onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflateur = LayoutInflater.from(context);
        View v1 = inflateur.inflate(R.layout.item_image,parent,false);
        return new ItemImage(v1);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemImage holder, int position) {
        holder.updateItemWithImg(this.listeImg.get(position));
    }

    @Override
    public int getItemCount() {
        return this.listeImg.size();
    }
}
