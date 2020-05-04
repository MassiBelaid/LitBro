package com.LilBro.LitBro.View;

import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.LilBro.LitBro.Models.Image;
import com.LilBro.LitBro.R;
import com.bumptech.glide.Glide;

public class ItemImage extends RecyclerView.ViewHolder {

    private View vue;
    private ImageView itemImgLocal;
    private TextView dateLocal;

    public ItemImage(@NonNull View itemView) {
        super(itemView);
        this.vue = itemView;
        itemImgLocal = vue.findViewById(R.id.itemImgLocal);
        dateLocal = vue.findViewById(R.id.textDate);
    }


    public void updateItemWithImg (Image img){
        dateLocal.setText(img.dateToString());

        Glide.with(vue).
                load(Uri.parse(img.getAddrImage())).override(300,300).centerCrop()
                .into(itemImgLocal);
    }

}
