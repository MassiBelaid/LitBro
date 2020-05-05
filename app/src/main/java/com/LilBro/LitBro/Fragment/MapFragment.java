package com.LilBro.LitBro.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.LilBro.LitBro.Activity.MainActivity;
import com.LilBro.LitBro.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MapFragment extends Fragment {


    public MapFragment() {
        // Required empty public constructor
    }

    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View result = inflater.inflate(R.layout.fragment_map, container, false);

        return result;
    }



}
