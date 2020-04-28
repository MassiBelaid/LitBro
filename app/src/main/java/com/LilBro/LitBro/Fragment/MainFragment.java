package com.LilBro.LitBro.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.LilBro.LitBro.Models.Local;
import com.LilBro.LitBro.R;
import com.LilBro.LitBro.View.itemLocalAdapter;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {

    private VideoView vidLocal;
    private Uri videoUri;
    private RecyclerView monRecycleur;
    private itemLocalAdapter adapter;

    public MainFragment(String uriVid){
        this.videoUri = Uri.parse(uriVid);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        /*
        vidLocal = view.findViewById(R.id.videoView);
        vidLocal.setVideoURI(videoUri);
        vidLocal.requestFocus();
        vidLocal.start();

         */

        monRecycleur = view.findViewById(R.id.monRecycleur);
        List<Local> ll = new ArrayList<>();
        ll.add(new Local("monLocal1"));
        ll.add(new Local("monLocal2"));
        adapter = new itemLocalAdapter(ll);
        monRecycleur.setAdapter(adapter);
        monRecycleur.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;

    }
}
