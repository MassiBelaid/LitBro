package com.LilBro.LitBro.Fragment;

import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.VideoView;

import com.LilBro.LitBro.R;


public class MainFragment extends Fragment {

    private VideoView vidLocal;
    private Uri videoUri;


    public MainFragment(String uriVid){
        this.videoUri = Uri.parse(uriVid);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        vidLocal = view.findViewById(R.id.videoView);
        vidLocal.setVideoURI(videoUri);
        vidLocal.requestFocus();
        vidLocal.start();
        return view;

    }
}
