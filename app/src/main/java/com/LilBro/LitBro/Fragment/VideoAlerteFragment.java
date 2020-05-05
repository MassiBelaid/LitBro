package com.LilBro.LitBro.Fragment;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.LilBro.LitBro.R;

public class VideoAlerteFragment extends Fragment {

    String UriVideo;
    VideoView videoAlerte;
    ProgressBar pbAlerte;

    public VideoAlerteFragment(String Uri) {
        this.UriVideo = Uri;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View result = inflater.inflate(R.layout.fragment_video_alerte, container, false);
        videoAlerte = result.findViewById(R.id.videoAlerte);
        pbAlerte = result.findViewById(R.id.progressBarAlerterVid);

        Uri videoUri = Uri.parse(UriVideo);
        videoAlerte.setVideoURI(videoUri);
        videoAlerte.requestFocus();
        videoAlerte.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if(what == mp.MEDIA_INFO_BUFFERING_START){
                    pbAlerte.setVisibility(View.VISIBLE);
                }else if(what == mp.MEDIA_INFO_BUFFERING_END){
                    pbAlerte.setVisibility(View.INVISIBLE);
                }
                return false;
            }
        });
        videoAlerte.start();

        return result;
    }


}
